package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.data.model.Job
import com.example.data.model.RemoteBadgeStyle
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue200
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibBlue800
import com.example.ui.theme.HibBlue900
import com.example.ui.theme.HibSlate100
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate600
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900
import com.example.ui.theme.HybridAmberBg
import com.example.ui.theme.HybridAmberBorder
import com.example.ui.theme.HybridAmberText
import com.example.ui.theme.WfaGreenBg
import com.example.ui.theme.WfaGreenBorder
import com.example.ui.theme.WfaGreenText
import com.example.ui.theme.WfhBlueBg
import com.example.ui.theme.WfhBlueBorder
import com.example.ui.theme.WfhBlueText

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JobDetailsSheet(
  job: Job,
  onDismiss: () -> Unit,
  onApplyClick: () -> Unit,
  onBookmarkToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val badge = when (job.remoteType) {
    "100% WFA" -> RemoteBadgeStyle(WfaGreenBg, WfaGreenText, WfaGreenBorder, "🌍")
    "WFH Regional" -> RemoteBadgeStyle(WfhBlueBg, WfhBlueText, WfhBlueBorder, "🏠")
    else -> RemoteBadgeStyle(HybridAmberBg, HybridAmberText, HybridAmberBorder, "🏢")
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    modifier = modifier.testTag("job_details_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 8.dp)
        .padding(bottom = 32.dp)
    ) {
      // Header: Avatar, Company, Title, Close Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(RoundedCornerShape(14.dp))
              .background(
                Brush.linearGradient(
                  listOf(
                    Color(job.brandColorHex),
                    Color(job.brandColorHex).copy(alpha = 0.8f)
                  )
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = job.companyInitials,
              color = Color.White,
              fontWeight = FontWeight.Black,
              fontSize = 18.sp
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = job.company,
                style = MaterialTheme.typography.labelLarge,
                color = HibSlate500
              )
              if (job.isFeatured) {
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                  color = HibBlue600,
                  shape = RoundedCornerShape(4.dp)
                ) {
                  Text(
                    text = "FEATURED",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                  )
                }
              }
            }
            Text(
              text = job.title,
              style = MaterialTheme.typography.titleLarge,
              color = HibSlate900,
              fontWeight = FontWeight.Bold
            )
          }
        }

        IconButton(onClick = onDismiss) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = HibSlate500
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Badges: Remote location & Salary
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(badge.bg)
            .border(1.dp, badge.border, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
          Text(
            text = "${badge.icon} ${job.location}",
            color = badge.text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(HibBlue50)
            .border(1.dp, HibBlue200, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Payments,
              contentDescription = null,
              tint = HibBlue700,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = job.salary,
              color = HibBlue700,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(HibSlate100)
            .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
          Text(
            text = job.type,
            color = HibSlate700,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))
      HorizontalDivider(color = HibSlate200)
      Spacer(modifier = Modifier.height(18.dp))

      // Job Overview Section
      Text(
        text = "Job Overview",
        style = MaterialTheme.typography.titleMedium,
        color = HibSlate900,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = job.description,
        style = MaterialTheme.typography.bodyLarge,
        color = HibSlate700,
        lineHeight = 22.sp
      )

      if (job.isOpenToPakistan) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFECFDF5))
            .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(10.dp))
            .padding(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "🇵🇰 Eligible for Candidates in Pakistan: ${job.timezoneNotes}. USD international contract with payouts supported via Payoneer, Wise, or Direct Wire.",
            fontSize = 11.5.sp,
            color = Color(0xFF047857),
            fontWeight = FontWeight.SemiBold,
            lineHeight = 16.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Requirements Section
      Text(
        text = "Key Requirements",
        style = MaterialTheme.typography.titleMedium,
        color = HibSlate900,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(8.dp))

      job.requirements.forEach { requirement ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = HibBlue600,
            modifier = Modifier
              .size(18.dp)
              .padding(top = 2.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = requirement,
            style = MaterialTheme.typography.bodyMedium,
            color = HibSlate700,
            lineHeight = 20.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(22.dp))

      // Recruitment Verification Callout
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(HibBlue50)
          .border(1.dp, HibBlue200, RoundedCornerShape(14.dp))
          .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Security,
          contentDescription = null,
          tint = HibBlue700,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Recruitment Verification",
            fontWeight = FontWeight.Bold,
            color = HibBlue900,
            fontSize = 13.sp
          )
          Text(
            text = "Verified remote role listed via HIB Recruitment Services portal. Direct talent pipeline with hiring team.",
            color = HibBlue800,
            fontSize = 11.sp,
            lineHeight = 15.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Bottom Actions: Bookmark & Apply Now
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedButton(
          onClick = onBookmarkToggle,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .weight(0.35f)
            .height(50.dp)
            .testTag("details_bookmark_button")
        ) {
          Icon(
            imageVector = if (job.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = null,
            tint = if (job.isBookmarked) HibBlue600 else HibSlate600,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (job.isBookmarked) "Saved" else "Save",
            color = if (job.isBookmarked) HibBlue600 else HibSlate700,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
          )
        }

        Button(
          onClick = onApplyClick,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = HibBlue600),
          modifier = Modifier
            .weight(0.65f)
            .height(50.dp)
            .testTag("details_apply_button")
        ) {
          Text(
            text = "Apply Now",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }
      }
    }
  }
}
