package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Job
import com.example.ui.components.JobCard
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900

@Composable
fun SavedJobsScreen(
  savedJobs: List<Job>,
  onJobClick: (Job) -> Unit,
  onBookmarkToggle: (Long) -> Unit,
  onExploreClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(HibBlue50.copy(alpha = 0.25f))
      .testTag("saved_jobs_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 20.dp, vertical = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Saved Jobs",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = HibSlate900
        )
        Text(
          text = "${savedJobs.size} saved remote position${if (savedJobs.size == 1) "" else "s"}",
          fontSize = 12.sp,
          color = HibSlate500
        )
      }
    }

    if (savedJobs.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp),
        contentAlignment = Alignment.Center
      ) {
        Card(
          modifier = Modifier.fillMaxWidth(),
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
              imageVector = Icons.Default.BookmarkBorder,
              contentDescription = null,
              tint = HibSlate400,
              modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
              text = "No saved jobs yet",
              style = MaterialTheme.typography.titleMedium,
              color = HibSlate800,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Tap the bookmark icon on any job card to save roles you want to review or apply to later.",
              fontSize = 12.sp,
              color = HibSlate500,
              textAlign = TextAlign.Center,
              lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
              onClick = onExploreClick,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = HibBlue600)
            ) {
              Text("Browse Open Positions", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(
          items = savedJobs,
          key = { it.id }
        ) { job ->
          JobCard(
            job = job,
            onClick = { onJobClick(job) },
            onBookmarkClick = { onBookmarkToggle(job.id) }
          )
        }
      }
    }
  }
}
