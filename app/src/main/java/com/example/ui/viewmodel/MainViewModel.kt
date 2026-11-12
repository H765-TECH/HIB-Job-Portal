package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AgencyConsultation
import com.example.data.model.ChatMessage
import com.example.data.model.Job
import com.example.data.model.JobApplication
import com.example.data.repository.AiChatRepository
import com.example.data.repository.JobRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class UiState(
  val searchQuery: String = "",
  val selectedCategory: String = "All Categories",
  val selectedRemoteFilter: String = "All Roles",
  val currentTab: Int = 0, // 0: Explore, 1: AI Advisor, 2: Saved, 3: Applied, 4: Employers
  val selectedJobForDetails: Job? = null,
  val selectedJobForApplication: Job? = null,
  val showAiPipelineDialog: Boolean = false,
  val showPostJobDialog: Boolean = false,
  val showConsultationDialog: Boolean = false,
  val isAiImporting: Boolean = false,
  val isAutoIngestRunning: Boolean = false,
  val toastMessage: String? = null
)

class MainViewModel(
  private val repository: JobRepository = JobRepository(),
  private val chatRepository: AiChatRepository = AiChatRepository()
) : ViewModel() {

  private val _uiState = MutableStateFlow(UiState())
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()

  val allJobs: StateFlow<List<Job>> = repository.jobs
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JobRepository.initialJobs)

  val applications: StateFlow<List<JobApplication>> = repository.applications
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val agencyInquiries: StateFlow<List<AgencyConsultation>> = repository.agencyInquiries
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val chatMessages: StateFlow<List<ChatMessage>> = chatRepository.messages
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AiChatRepository.initialMessages)

  val isChatGenerating: StateFlow<Boolean> = chatRepository.isGenerating
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  private var autoIngestJob: kotlinx.coroutines.Job? = null

  val filteredJobs: StateFlow<List<Job>> = combine(
    allJobs,
    _uiState
  ) { jobs, state ->
    val query = state.searchQuery.trim().lowercase()
    val category = state.selectedCategory
    val remote = state.selectedRemoteFilter

    jobs.filter { job ->
      val matchesSearch = query.isEmpty() ||
        job.title.lowercase().contains(query) ||
        job.company.lowercase().contains(query) ||
        job.category.lowercase().contains(query) ||
        job.location.lowercase().contains(query) ||
        job.description.lowercase().contains(query)

      val matchesCategory = category == "All Categories" || job.category == category

      val matchesRemote = when (remote) {
        "All Roles" -> true
        "🇵🇰 Pakistan / WFA" -> job.isOpenToPakistan && job.remoteType == "100% WFA"
        "100% WFA" -> job.remoteType == "100% WFA"
        "WFH Regional" -> job.remoteType == "WFH Regional"
        "Hybrid" -> job.remoteType == "Hybrid"
        else -> true
      }

      matchesSearch && matchesCategory && matchesRemote
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JobRepository.initialJobs)

  val savedJobs: StateFlow<List<Job>> = combine(allJobs) { (jobs) ->
    jobs.filter { it.isBookmarked }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun updateSearchQuery(query: String) {
    _uiState.value = _uiState.value.copy(searchQuery = query)
  }

  fun selectCategory(category: String) {
    _uiState.value = _uiState.value.copy(selectedCategory = category)
  }

  fun selectRemoteFilter(filter: String) {
    _uiState.value = _uiState.value.copy(selectedRemoteFilter = filter)
  }

  fun setTab(tabIndex: Int) {
    _uiState.value = _uiState.value.copy(currentTab = tabIndex)
  }

  fun openJobDetails(job: Job) {
    _uiState.value = _uiState.value.copy(selectedJobForDetails = job)
  }

  fun closeJobDetails() {
    _uiState.value = _uiState.value.copy(selectedJobForDetails = null)
  }

  fun openApplyDialog(job: Job) {
    _uiState.value = _uiState.value.copy(
      selectedJobForDetails = null,
      selectedJobForApplication = job
    )
  }

  fun closeApplyDialog() {
    _uiState.value = _uiState.value.copy(selectedJobForApplication = null)
  }

  fun toggleBookmark(jobId: Long) {
    repository.toggleBookmark(jobId)
    val currentDetail = _uiState.value.selectedJobForDetails
    if (currentDetail?.id == jobId) {
      _uiState.value = _uiState.value.copy(
        selectedJobForDetails = currentDetail.copy(isBookmarked = !currentDetail.isBookmarked)
      )
    }
  }

  fun submitApplication(
    job: Job,
    name: String,
    email: String,
    portfolio: String,
    resumeName: String,
    coverNote: String
  ) {
    repository.submitApplication(job, name, email, portfolio, resumeName, coverNote)
    closeApplyDialog()
    showToast("Application submitted successfully for ${job.title}!")
  }

  fun postJob(
    title: String,
    company: String,
    category: String,
    remoteType: String,
    location: String,
    salary: String,
    type: String,
    description: String,
    requirements: List<String>
  ) {
    val newJob = repository.postJob(
      title = title,
      company = company,
      category = category,
      remoteType = remoteType,
      location = location,
      salary = salary,
      type = type,
      description = description,
      requirements = requirements
    )
    _uiState.value = _uiState.value.copy(showPostJobDialog = false)
    showToast("Role published! '${newJob.title}' is now live on HIB.")
  }

  fun triggerAiImport() {
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isAiImporting = true)
      showToast("Fetching & parsing remote feeds via AI Pipeline...")
      delay(1100)
      val imported = repository.triggerSimulatedAiImport()
      _uiState.value = _uiState.value.copy(isAiImporting = false)
      showToast("AI Feed Ingested: '${imported.title}'")
    }
  }

  fun toggleAutoIngest() {
    val willBeRunning = !_uiState.value.isAutoIngestRunning
    _uiState.value = _uiState.value.copy(isAutoIngestRunning = willBeRunning)
    if (willBeRunning) {
      showToast("Auto-Job Ingest is ON (Continuous background additions)")
      autoIngestJob?.cancel()
      autoIngestJob = viewModelScope.launch {
        while (isActive) {
          delay(20_000L)
          val job = repository.triggerSimulatedAiImport()
          showToast("🤖 Auto-Ingested: ${job.title}")
        }
      }
    } else {
      autoIngestJob?.cancel()
      autoIngestJob = null
      showToast("Auto-Job Ingest turned OFF")
    }
  }

  fun sendChatMessage(text: String) {
    viewModelScope.launch {
      chatRepository.sendMessage(text)
    }
  }

  fun clearChat() {
    chatRepository.clearChat()
    showToast("Chat reset.")
  }

  fun submitConsultation(
    company: String,
    email: String,
    role: String,
    urgency: String,
    message: String
  ) {
    repository.submitAgencyConsultation(company, email, role, urgency, message)
    _uiState.value = _uiState.value.copy(showConsultationDialog = false)
    showToast("Discovery call requested! HIB Recruitment will reach out within 2 hours.")
  }

  fun subscribeNewsletter(email: String) {
    val success = repository.subscribeNewsletter(email)
    if (success) {
      showToast("Subscribed! You will receive curated WFA & WFH job digests.")
    } else {
      showToast("Please enter a valid email address.")
    }
  }

  fun setAiPipelineDialog(visible: Boolean) {
    _uiState.value = _uiState.value.copy(showAiPipelineDialog = visible)
  }

  fun setPostJobDialog(visible: Boolean) {
    _uiState.value = _uiState.value.copy(showPostJobDialog = visible)
  }

  fun setConsultationDialog(visible: Boolean) {
    _uiState.value = _uiState.value.copy(showConsultationDialog = visible)
  }

  fun showToast(message: String) {
    _uiState.value = _uiState.value.copy(toastMessage = message)
    viewModelScope.launch {
      delay(3500)
      if (_uiState.value.toastMessage == message) {
        _uiState.value = _uiState.value.copy(toastMessage = null)
      }
    }
  }

  fun dismissToast() {
    _uiState.value = _uiState.value.copy(toastMessage = null)
  }

  override fun onCleared() {
    super.onCleared()
    autoIngestJob?.cancel()
  }
}
