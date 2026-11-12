package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue500
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue900
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate900

@Composable
fun EmployerBanner(
  onBookCallClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(
        Brush.linearGradient(
          colors = listOf(
            HibBlue900,
            Color(0xFF1E1B4B),
            HibSlate900
          )
        )
      )
      .border(1.dp, Color(0xFF312E81), RoundedCornerShape(20.dp))
      .padding(20.dp)
      .testTag("employer_banner")
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Small badge
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(HibBlue500.copy(alpha = 0.2f))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = "HIB RECRUITMENT AGENCY SERVICES",
          color = HibBlue100,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Struggling to Vet & Screen 200+ Remote Applicants?",
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 26.sp
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Don't let unqualified applications slow down your hiring. HIB Recruitment Services sources, headhunts, and vets the top 5% of global remote talent for your open positions.",
        color = HibSlate400,
        fontSize = 12.sp,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onBookCallClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = HibBlue500),
        modifier = Modifier.testTag("book_discovery_call_btn")
      ) {
        Icon(
          imageVector = Icons.Default.PhoneInTalk,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Book a Candidate Discovery Call",
          color = Color.White,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Shield,
          contentDescription = null,
          tint = EmeraldLive,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Guaranteed replacement within 90 days",
          color = HibSlate400,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}
