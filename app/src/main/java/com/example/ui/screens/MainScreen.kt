package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AgencyConsultationDialog
import com.example.ui.components.AiPipelineDialog
import com.example.ui.components.ApplyJobDialog
import com.example.ui.components.JobDetailsSheet
import com.example.ui.components.PostJobDialog
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue500
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibBlue900
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  viewModel: MainViewModel,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  val filteredJobs by viewModel.filteredJobs.collectAsState()
  val savedJobs by viewModel.savedJobs.collectAsState()
  val applications by viewModel.applications.collectAsState()
  val inquiries by viewModel.agencyInquiries.collectAsState()
  val chatMessages by viewModel.chatMessages.collectAsState()
  val isChatGenerating by viewModel.isChatGenerating.collectAsState()

  BoxWithConstraints(modifier = modifier.fillMaxSize()) {
    val isWideScreen = maxWidth >= 600.dp

    Scaffold(
      modifier = Modifier.fillMaxSize(),
      topBar = {
        HIBTopBar(
          onAiPipelineClick = { viewModel.setAiPipelineDialog(true) },
          onPostJobClick = { viewModel.setPostJobDialog(true) },
          onAiAdvisorClick = { viewModel.setTab(1) }
        )
      },
      bottomBar = {
        if (!isWideScreen) {
          HIBBottomNavBar(
            currentTab = uiState.currentTab,
            onTabSelected = { viewModel.setTab(it) }
          )
        }
      },
      floatingActionButton = {
        if (uiState.currentTab == 0) {
          FloatingActionButton(
            onClick = { viewModel.setPostJobDialog(true) },
            containerColor = HibBlue600,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier.testTag("fab_post_job")
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Post a Remote Job")
          }
        }
      }
    ) { innerPadding ->
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        if (isWideScreen) {
          NavigationRail(
            containerColor = Color.White,
            modifier = Modifier.width(80.dp)
          ) {
            NavigationRailItem(
              selected = uiState.currentTab == 0,
              onClick = { viewModel.setTab(0) },
              icon = {
                Icon(
                  if (uiState.currentTab == 0) Icons.Default.Explore else Icons.Outlined.Explore,
                  contentDescription = "Jobs"
                )
              },
              label = { Text("Jobs") }
            )
            NavigationRailItem(
              selected = uiState.currentTab == 1,
              onClick = { viewModel.setTab(1) },
              icon = {
                Icon(
                  if (uiState.currentTab == 1) Icons.Default.AutoAwesome else Icons.Outlined.AutoAwesome,
                  contentDescription = "AI Advisor"
                )
              },
              label = { Text("AI Advisor") }
            )
            NavigationRailItem(
              selected = uiState.currentTab == 2,
              onClick = { viewModel.setTab(2) },
              icon = {
                Icon(
                  if (uiState.currentTab == 2) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                  contentDescription = "Saved"
                )
              },
              label = { Text("Saved") }
            )
            NavigationRailItem(
              selected = uiState.currentTab == 3,
              onClick = { viewModel.setTab(3) },
              icon = {
                Icon(
                  if (uiState.currentTab == 3) Icons.Default.AssignmentTurnedIn else Icons.Outlined.AssignmentTurnedIn,
                  contentDescription = "Applied"
                )
              },
              label = { Text("Applied") }
            )
            NavigationRailItem(
              selected = uiState.currentTab == 4,
              onClick = { viewModel.setTab(4) },
              icon = {
                Icon(
                  if (uiState.currentTab == 4) Icons.Default.BusinessCenter else Icons.Outlined.BusinessCenter,
                  contentDescription = "Employers"
                )
              },
              label = { Text("Employers") }
            )
          }
        }

        Box(modifier = Modifier.weight(1f)) {
          when (uiState.currentTab) {
            0 -> JobPortalScreen(
              jobs = filteredJobs,
              searchQuery = uiState.searchQuery,
              onSearchQueryChange = { viewModel.updateSearchQuery(it) },
              selectedCategory = uiState.selectedCategory,
              onCategorySelect = { viewModel.selectCategory(it) },
              selectedRemoteFilter = uiState.selectedRemoteFilter,
              onRemoteFilterSelect = { viewModel.selectRemoteFilter(it) },
              isAiImporting = uiState.isAiImporting,
              isAutoIngestRunning = uiState.isAutoIngestRunning,
              onToggleAutoIngest = { viewModel.toggleAutoIngest() },
              onAiRefreshClick = { viewModel.triggerAiImport() },
              onAiPipelineClick = { viewModel.setAiPipelineDialog(true) },
              onJobClick = { viewModel.openJobDetails(it) },
              onBookmarkToggle = { viewModel.toggleBookmark(it) },
              onBookCallClick = { viewModel.setConsultationDialog(true) },
              onSubscribeNewsletter = { viewModel.subscribeNewsletter(it) }
            )
            1 -> AiChatScreen(
              messages = chatMessages,
              isGenerating = isChatGenerating,
              onSendMessage = { viewModel.sendChatMessage(it) },
              onClearChat = { viewModel.clearChat() }
            )
            2 -> SavedJobsScreen(
              savedJobs = savedJobs,
              onJobClick = { viewModel.openJobDetails(it) },
              onBookmarkToggle = { viewModel.toggleBookmark(it) },
              onExploreClick = { viewModel.setTab(0) }
            )
            3 -> ApplicationsScreen(
              applications = applications,
              onExploreClick = { viewModel.setTab(0) }
            )
            4 -> EmployerScreen(
              agencyInquiries = inquiries,
              onPostJobClick = { viewModel.setPostJobDialog(true) },
              onConsultationClick = { viewModel.setConsultationDialog(true) }
            )
          }

          // In-App Toast notification banner
          ToastNotificationBanner(
            message = uiState.toastMessage,
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
          )
        }
      }
    }
  }

  // Modals & Bottom Sheets
  uiState.selectedJobForDetails?.let { job ->
    JobDetailsSheet(
      job = job,
      onDismiss = { viewModel.closeJobDetails() },
      onApplyClick = { viewModel.openApplyDialog(job) },
      onBookmarkToggle = { viewModel.toggleBookmark(job.id) }
    )
  }

  uiState.selectedJobForApplication?.let { job ->
    ApplyJobDialog(
      job = job,
      onDismiss = { viewModel.closeApplyDialog() },
      onSubmit = { name, email, portfolio, resume, note ->
        viewModel.submitApplication(job, name, email, portfolio, resume, note)
      }
    )
  }

  if (uiState.showAiPipelineDialog) {
    AiPipelineDialog(
      onDismiss = { viewModel.setAiPipelineDialog(false) },
      onSimulateFetch = { viewModel.triggerAiImport() }
    )
  }

  if (uiState.showPostJobDialog) {
    PostJobDialog(
      onDismiss = { viewModel.setPostJobDialog(false) },
      onSubmit = { title, comp, cat, rt, loc, sal, typ, desc, reqs ->
        viewModel.postJob(title, comp, cat, rt, loc, sal, typ, desc, reqs)
      }
    )
  }

  if (uiState.showConsultationDialog) {
    AgencyConsultationDialog(
      onDismiss = { viewModel.setConsultationDialog(false) },
      onSubmit = { comp, email, role, urg, msg ->
        viewModel.submitConsultation(comp, email, role, urg, msg)
      }
    )
  }
}

@Composable
fun HIBTopBar(
  onAiPipelineClick: () -> Unit,
  onPostJobClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    color = Color.White,
    shadowElevation = 2.dp,
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .windowInsetsPadding(WindowInsets.statusBars)
        .padding(horizontal = 16.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Brand Logo & Title
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
              Brush.linearGradient(
                listOf(
                  Color(0xFF1D4ED8),
                  Color(0xFF4F46E5)
                )
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "HIB",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp,
            letterSpacing = (-0.5).sp
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Text(
            text = "HIB Recruitment",
            style = MaterialTheme.typography.titleMedium,
            color = HibSlate900,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 18.sp
          )
          Text(
            text = "REMOTE JOB PORTAL",
            fontSize = 9.sp,
            color = HibBlue600,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        }
      }

      // Actions
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // AI Demo Button
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFECFDF5))
            .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(8.dp))
            .clickable(onClick = onAiPipelineClick)
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("top_ai_demo_btn")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.SmartToy,
              contentDescription = null,
              tint = Color(0xFF047857),
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "AI Ingest",
              color = Color(0xFF047857),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Post Job Button
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(HibBlue600)
            .clickable(onClick = onPostJobClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("top_post_job_btn")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Post Job",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}

@Composable
fun HIBBottomNavBar(
  currentTab: Int,
  onTabSelected: (Int) -> Unit
) {
  NavigationBar(
    containerColor = Color.White,
    tonalElevation = 6.dp,
    modifier = Modifier
      .fillMaxWidth()
      .windowInsetsPadding(WindowInsets.navigationBars)
  ) {
    NavigationBarItem(
      selected = currentTab == 0,
      onClick = { onTabSelected(0) },
      icon = {
        Icon(
          imageVector = if (currentTab == 0) Icons.Default.Explore else Icons.Outlined.Explore,
          contentDescription = "Explore"
        )
      },
      label = { Text("Jobs", fontSize = 10.5.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = HibBlue600,
        selectedTextColor = HibBlue600,
        indicatorColor = HibBlue100
      )
    )

    NavigationBarItem(
      selected = currentTab == 1,
      onClick = { onTabSelected(1) },
      icon = {
        Icon(
          imageVector = if (currentTab == 1) Icons.Default.AutoAwesome else Icons.Outlined.AutoAwesome,
          contentDescription = "AI Advisor"
        )
      },
      label = { Text("AI Advisor", fontSize = 10.5.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = HibBlue600,
        selectedTextColor = HibBlue600,
        indicatorColor = HibBlue100
      )
    )

    NavigationBarItem(
      selected = currentTab == 2,
      onClick = { onTabSelected(2) },
      icon = {
        Icon(
          imageVector = if (currentTab == 2) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
          contentDescription = "Saved"
        )
      },
      label = { Text("Saved", fontSize = 10.5.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = HibBlue600,
        selectedTextColor = HibBlue600,
        indicatorColor = HibBlue100
      )
    )

    NavigationBarItem(
      selected = currentTab == 3,
      onClick = { onTabSelected(3) },
      icon = {
        Icon(
          imageVector = if (currentTab == 3) Icons.Default.AssignmentTurnedIn else Icons.Outlined.AssignmentTurnedIn,
          contentDescription = "Applied"
        )
      },
      label = { Text("Applied", fontSize = 10.5.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = HibBlue600,
        selectedTextColor = HibBlue600,
        indicatorColor = HibBlue100
      )
    )

    NavigationBarItem(
      selected = currentTab == 4,
      onClick = { onTabSelected(4) },
      icon = {
        Icon(
          imageVector = if (currentTab == 4) Icons.Default.BusinessCenter else Icons.Outlined.BusinessCenter,
          contentDescription = "Employers"
        )
      },
      label = { Text("Employers", fontSize = 10.5.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = HibBlue600,
        selectedTextColor = HibBlue600,
        indicatorColor = HibBlue100
      )
    )
  }
}

@Composable
fun ToastNotificationBanner(
  message: String?,
  modifier: Modifier = Modifier
) {
  AnimatedVisibility(
    visible = message != null,
    enter = slideInVertically(initialOffsetY = { it }),
    exit = slideOutVertically(targetOffsetY = { it }),
    modifier = modifier
  ) {
    message?.let { msg ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = HibSlate900),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(EmeraldLive)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = msg,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }
  }
}

