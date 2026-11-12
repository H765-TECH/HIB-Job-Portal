package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.data.model.JobApplication
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue200
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibSlate100
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900

@Composable
fun ApplicationsScreen(
  applications: List<JobApplication>,
  onExploreClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(HibBlue50.copy(alpha = 0.25f))
      .testTag("applications_screen")
  ) {
    // Top Bar
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
          text = "My Applications",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = HibSlate900
        )
        Text(
          text = "${applications.size} active job application${if (applications.size == 1) "" else "s"}",
          fontSize = 12.sp,
          color = HibSlate500
        )
      }
    }

    if (applications.isEmpty()) {
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
              imageVector = Icons.Default.FolderShared,
              contentDescription = null,
              tint = HibSlate400,
              modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
              text = "No applications yet",
              style = MaterialTheme.typography.titleMedium,
              color = HibSlate800,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Browse active verified remote roles and apply directly with your resume.",
              fontSize = 12.sp,
              color = HibSlate500,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
              onClick = onExploreClick,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = HibBlue600)
            ) {
              Text("Browse Roles", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(
          items = applications,
          key = { it.id }
        ) { app ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, HibSlate200),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              // Status Pill + Applied Date
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  shape = RoundedCornerShape(20.dp),
                  color = if (app.status == "Submitted") HibBlue50 else Color(0xFFECFDF5),
                  border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (app.status == "Submitted") HibBlue200 else Color(0xFFA7F3D0)
                  )
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  ) {
                    Box(
                      modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (app.status == "Submitted") HibBlue600 else EmeraldLive)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = app.status,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (app.status == "Submitted") HibBlue700 else Color(0xFF047857)
                    )
                  }
                }

                Text(
                  text = app.appliedDate,
                  fontSize = 11.sp,
                  color = HibSlate400,
                  fontWeight = FontWeight.Medium
                )
              }

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = app.jobTitle,
                style = MaterialTheme.typography.titleMedium,
                color = HibSlate900,
                fontWeight = FontWeight.Bold
              )

              Text(
                text = app.company,
                style = MaterialTheme.typography.labelLarge,
                color = HibBlue700
              )

              Spacer(modifier = Modifier.height(12.dp))

              // Resume & Candidate Details
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(HibSlate100)
                  .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = HibBlue600,
                    modifier = Modifier.size(15.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Resume: ${app.resumeName}",
                    fontSize = 11.sp,
                    color = HibSlate800,
                    fontWeight = FontWeight.Medium
                  )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = HibSlate500,
                    modifier = Modifier.size(15.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "${app.applicantName} • ${app.applicantEmail}",
                    fontSize = 11.sp,
                    color = HibSlate700
                  )
                }

                if (app.portfolioUrl.isNotBlank() && app.portfolioUrl != "Not provided") {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Link,
                      contentDescription = null,
                      tint = HibSlate500,
                      modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = app.portfolioUrl,
                      fontSize = 11.sp,
                      color = HibBlue600,
                      fontWeight = FontWeight.Medium
                    )
                  }
                }
              }

              if (app.coverNote.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "\"${app.coverNote}\"",
                  fontSize = 11.sp,
                  color = HibSlate500,
                  lineHeight = 16.sp
                )
              }
            }
          }
        }
      }
    }
  }
}
