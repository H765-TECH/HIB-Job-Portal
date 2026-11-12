package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgencyConsultation
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue500
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibBlue900
import com.example.ui.theme.HibSlate100
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900

@Composable
fun EmployerScreen(
  agencyInquiries: List<AgencyConsultation>,
  onPostJobClick: () -> Unit,
  onConsultationClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(HibBlue50.copy(alpha = 0.25f))
      .verticalScroll(rememberScrollState())
      .padding(bottom = 80.dp)
      .testTag("employer_screen")
  ) {
    // Header Hero Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            colors = listOf(
              HibBlue900,
              Color(0xFF1E1B4B),
              HibSlate900
            )
          )
        )
        .padding(20.dp)
    ) {
      Column {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(HibBlue500.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "FOR HIRING MANAGERS & FOUNDERS",
            color = HibBlue100,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Hire the Top 5% of Global Remote Talent",
          style = MaterialTheme.typography.displayMedium,
          color = Color.White,
          fontWeight = FontWeight.ExtraBold,
          lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "HIB Recruitment Services handles outbound headhunting, rigorous technical vetting, and culture alignment so you only interview qualified finalists.",
          color = HibSlate400,
          fontSize = 12.sp,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = onPostJobClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HibBlue600),
            modifier = Modifier
              .weight(1f)
              .testTag("employer_post_job_btn")
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Post a Job", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }

          Button(
            onClick = onConsultationClick,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
            modifier = Modifier
              .weight(1.2f)
              .testTag("employer_consultation_btn")
          ) {
            Icon(
              imageVector = Icons.Default.PhoneInTalk,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Book Call", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Statistics Cards
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      StatCard(
        title = "Top 5% Talent Acceptance",
        description = "Rigorous multi-stage vetting covering communication, asynchronous discipline, and live skill assessments.",
        icon = Icons.Default.Groups,
        iconTint = HibBlue600
      )

      StatCard(
        title = "90-Day Guaranteed Replacement",
        description = "Full peace of mind. If any hired remote candidate departs within 90 days, HIB sources a vetted replacement free.",
        icon = Icons.Default.Shield,
        iconTint = EmeraldLive
      )

      StatCard(
        title = "48-Hour Shortlist Delivery",
        description = "Receive curated, vetted profiles with video screening notes and compensation benchmarks in as little as 48 hours.",
        icon = Icons.Default.Speed,
        iconTint = Color(0xFFF59E0B)
      )
    }

    // Recent Inquiries section if user requested consultation
    if (agencyInquiries.isNotEmpty()) {
      Spacer(modifier = Modifier.height(20.dp))
      Text(
        text = "Your Recent Consultation Requests",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = HibSlate900,
        modifier = Modifier.padding(horizontal = 16.dp)
      )
      Spacer(modifier = Modifier.height(8.dp))

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        agencyInquiries.forEach { inquiry ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, HibSlate200)
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = inquiry.companyName,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                  color = HibSlate900
                )
                Text(
                  text = inquiry.submittedAt,
                  fontSize = 11.sp,
                  color = HibSlate400
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Hiring: ${inquiry.roleHiringFor}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = HibBlue700
              )
              Text(
                text = "Timeline: ${inquiry.urgency}",
                fontSize = 11.sp,
                color = HibSlate500
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun StatCard(
  title: String,
  description: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  iconTint: Color
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    border = androidx.compose.foundation.BorderStroke(1.dp, HibSlate200)
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(iconTint.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = iconTint,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column {
        Text(
          text = title,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          color = HibSlate900
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = description,
          fontSize = 12.sp,
          color = HibSlate500,
          lineHeight = 17.sp
        )
      }
    }
  }
}
