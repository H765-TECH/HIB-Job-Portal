package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Job
import com.example.data.model.RemoteBadgeStyle
import com.example.ui.theme.HibBlue200
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibSlate100
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobCard(
  job: Job,
  onClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val badge = when (job.remoteType) {
    "100% WFA" -> RemoteBadgeStyle(WfaGreenBg, WfaGreenText, WfaGreenBorder, "🌍")
    "WFH Regional" -> RemoteBadgeStyle(WfhBlueBg, WfhBlueText, WfhBlueBorder, "🏠")
    else -> RemoteBadgeStyle(HybridAmberBg, HybridAmberText, HybridAmberBorder, "🏢")
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("job_card_${job.id}")
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (job.isFeatured) HibBlue50.copy(alpha = 0.45f) else Color.White
    ),
    border = androidx.compose.foundation.BorderStroke(
      width = if (job.isFeatured) 1.5.dp else 1.dp,
      color = if (job.isFeatured) HibBlue200 else HibSlate200
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Top row: Avatar, Company, Featured badge, Bookmark action
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          // Company Avatar
          Box(
            modifier = Modifier
              .size(46.dp)
              .clip(RoundedCornerShape(12.dp))
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
              fontSize = 15.sp,
              letterSpacing = 0.5.sp
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = job.company,
                style = MaterialTheme.typography.labelMedium,
                color = HibSlate500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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

              if (job.isAiImported) {
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                  color = Color(0xFF10A37F),
                  shape = RoundedCornerShape(4.dp)
                ) {
                  Text(
                    text = "AI FEED",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                  )
                }
              }

              if (job.isOpenToPakistan && job.remoteType == "100% WFA") {
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                  color = Color(0xFF047857),
                  shape = RoundedCornerShape(4.dp)
                ) {
                  Text(
                    text = "🇵🇰 OPEN TO PK",
                    color = Color.White,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
              text = job.title,
              style = MaterialTheme.typography.titleMedium,
              color = HibSlate900,
              fontWeight = FontWeight.Bold,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis
            )
          }
        }

        IconButton(
          onClick = onBookmarkClick,
          modifier = Modifier
            .size(40.dp)
            .testTag("bookmark_button_${job.id}")
        ) {
          Icon(
            imageVector = if (job.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = "Bookmark Job",
            tint = if (job.isBookmarked) HibBlue600 else HibSlate400
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Tags Row: Category, Salary, Posted Time
      FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        JobTagChip(
          icon = Icons.Default.Work,
          text = job.category,
          tint = HibSlate500
        )

        JobTagChip(
          icon = Icons.Default.Payments,
          text = job.salary,
          tint = HibSlate500
        )

        JobTagChip(
          icon = Icons.Default.Schedule,
          text = job.posted,
          tint = HibSlate400
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Bottom Row: Location Badge & "View Role" CTA
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Location Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(badge.bg)
            .border(1.dp, badge.border, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "${badge.icon} ${job.location}",
            color = badge.text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable(onClick = onClick)
        ) {
          Text(
            text = "View Role",
            style = MaterialTheme.typography.labelLarge,
            color = HibBlue600,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(3.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = HibBlue600,
            modifier = Modifier.size(13.dp)
          )
        }
      }
    }
  }
}

@Composable
fun JobTagChip(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  text: String,
  tint: Color
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clip(RoundedCornerShape(6.dp))
      .background(HibSlate100.copy(alpha = 0.8f))
      .padding(horizontal = 7.dp, vertical = 3.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = tint,
      modifier = Modifier.size(13.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = text,
      color = HibSlate800,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium
    )
  }
}
