package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FolderOff
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Job
import com.example.ui.components.EmployerBanner
import com.example.ui.components.HeroSection
import com.example.ui.components.JobCard
import com.example.ui.components.NewsletterSection
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900

@Composable
fun JobPortalScreen(
  jobs: List<Job>,
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  selectedCategory: String,
  onCategorySelect: (String) -> Unit,
  selectedRemoteFilter: String,
  onRemoteFilterSelect: (String) -> Unit,
  isAiImporting: Boolean,
  isAutoIngestRunning: Boolean = false,
  onToggleAutoIngest: () -> Unit = {},
  onAiRefreshClick: () -> Unit,
  onAiPipelineClick: () -> Unit,
  onJobClick: (Job) -> Unit,
  onBookmarkToggle: (Long) -> Unit,
  onBookCallClick: () -> Unit,
  onSubscribeNewsletter: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(HibBlue50.copy(alpha = 0.25f))
      .testTag("job_portal_screen"),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // Hero Header with search & filters
    item(key = "hero_section") {
      HeroSection(
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        selectedCategory = selectedCategory,
        onCategorySelect = onCategorySelect,
        selectedRemoteFilter = selectedRemoteFilter,
        onRemoteFilterSelect = onRemoteFilterSelect,
        onAiPipelineClick = onAiPipelineClick
      )
    }

    // Active Jobs Bar Header
    item(key = "active_jobs_header") {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Active Remote Opportunities",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = HibSlate900
          )
          Text(
            text = "Showing ${jobs.size} active remote role${if (jobs.size == 1) "" else "s"}",
            fontSize = 11.sp,
            color = HibSlate500
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          OutlinedButton(
            onClick = onToggleAutoIngest,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
              containerColor = if (isAutoIngestRunning) Color(0xFFECFDF5) else Color.Transparent
            ),
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isAutoIngestRunning) Color(0xFF047857) else HibSlate200
            ),
            modifier = Modifier.testTag("auto_ingest_toggle_btn")
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(if (isAutoIngestRunning) EmeraldLive else HibSlate400)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
              text = if (isAutoIngestRunning) "Auto: ON" else "Auto: OFF",
              fontSize = 11.sp,
              color = if (isAutoIngestRunning) Color(0xFF047857) else HibSlate700,
              fontWeight = FontWeight.Bold
            )
          }

          OutlinedButton(
            onClick = onAiRefreshClick,
            enabled = !isAiImporting,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("refresh_ai_feed_btn")
          ) {
            if (isAiImporting) {
              CircularProgressIndicator(
                modifier = Modifier.size(14.dp),
                strokeWidth = 2.dp,
                color = HibBlue600
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Ingesting...",
                fontSize = 11.sp,
                color = HibBlue600,
                fontWeight = FontWeight.Bold
              )
            } else {
              Icon(
                imageVector = Icons.Default.Sync,
                contentDescription = null,
                tint = HibBlue600,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "AI Fetch",
                fontSize = 11.sp,
                color = HibBlue600,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // Empty state if no jobs matched
    if (jobs.isEmpty()) {
      item(key = "empty_state") {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          border = androidx.compose.foundation.BorderStroke(1.dp, HibSlate200)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.FolderOff,
              contentDescription = null,
              tint = HibSlate400,
              modifier = Modifier.size(44.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No matching remote jobs found",
              style = MaterialTheme.typography.titleMedium,
              color = HibSlate800,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Try adjusting your search keyword or switching remote filter tabs.",
              fontSize = 12.sp,
              color = HibSlate500,
              textAlign = TextAlign.Center
            )
          }
        }
      }
    } else {
      // Job Cards List
      items(
        items = jobs,
        key = { it.id }
      ) { job ->
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
          JobCard(
            job = job,
            onClick = { onJobClick(job) },
            onBookmarkClick = { onBookmarkToggle(job.id) }
          )
        }
      }
    }

    // Employer Agency Banner Lead Magnet
    item(key = "employer_banner") {
      Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
        EmployerBanner(onBookCallClick = onBookCallClick)
      }
    }

    // Newsletter Section
    item(key = "newsletter_section") {
      Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        NewsletterSection(onSubscribe = onSubscribeNewsletter)
      }
    }

    // Footer Copyright
    item(key = "footer") {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "© 2026 HIB Recruitment Services. All rights reserved.",
          fontSize = 11.sp,
          color = HibSlate500,
          fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "Connecting Work From Anywhere (WFA) & Work From Home (WFH) Talent Worldwide.",
          fontSize = 10.sp,
          color = HibSlate400,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
