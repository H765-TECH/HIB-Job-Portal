package com.example.data.model

data class Job(
  val id: Long,
  val title: String,
  val company: String,
  val companyInitials: String,
  val brandColorHex: Long,
  val category: String,
  val remoteType: String, // "100% WFA", "WFH Regional", "Hybrid"
  val location: String,
  val salary: String,
  val type: String, // "Full-Time", "Contract", "Fractional"
  val posted: String,
  val isFeatured: Boolean = false,
  val description: String,
  val requirements: List<String>,
  val isBookmarked: Boolean = false,
  val isAiImported: Boolean = false,
  val isOpenToPakistan: Boolean = true,
  val timezoneNotes: String = "Global asynchronous or flexible PKT/EMEA hours"
)

data class JobApplication(
  val id: String,
  val jobId: Long,
  val jobTitle: String,
  val company: String,
  val applicantName: String,
  val applicantEmail: String,
  val portfolioUrl: String,
  val resumeName: String,
  val coverNote: String,
  val appliedDate: String,
  val status: String = "Submitted" // "Submitted", "Under Review", "Shortlisted"
)

data class AgencyConsultation(
  val id: String,
  val companyName: String,
  val workEmail: String,
  val roleHiringFor: String,
  val urgency: String,
  val message: String,
  val submittedAt: String
)

data class RemoteBadgeStyle(
  val bg: androidx.compose.ui.graphics.Color,
  val text: androidx.compose.ui.graphics.Color,
  val border: androidx.compose.ui.graphics.Color,
  val icon: String
)

data class ChatMessage(
  val id: String,
  val sender: String, // "user" or "model"
  val text: String,
  val timestamp: String,
  val isStreaming: Boolean = false
)
