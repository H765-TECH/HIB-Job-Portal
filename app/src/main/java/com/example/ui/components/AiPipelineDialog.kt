package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue500
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900

@Composable
fun AiPipelineDialog(
  onDismiss: () -> Unit,
  onSimulateFetch: () -> Unit,
  modifier: Modifier = Modifier
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = HibSlate900,
      shadowElevation = 12.dp,
      modifier = modifier
        .fillMaxWidth()
        .border(1.dp, HibSlate700, RoundedCornerShape(20.dp))
        .testTag("ai_pipeline_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(20.dp)
      ) {
        // Header
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
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(EmeraldLive.copy(alpha = 0.2f))
                .border(1.dp, EmeraldLive.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.SmartToy,
                contentDescription = null,
                tint = EmeraldLive,
                modifier = Modifier.size(24.dp)
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
              Text(
                text = "Automated AI Ingestion",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "How HIB posts verified remote jobs daily",
                fontSize = 11.sp,
                color = HibSlate400
              )
            }
          }

          IconButton(onClick = onDismiss) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = HibSlate400
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Pipeline Console Box
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF070C18))
            .border(1.dp, HibSlate800, RoundedCornerShape(14.dp))
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          PipelineStep(
            tag = "[1. Cron Trigger]",
            tagColor = EmeraldLive,
            icon = Icons.Default.Schedule,
            description = "Runs daily at 08:00 AM UTC via Make.com scenario & cloud worker."
          )

          PipelineStep(
            tag = "[2. RSS Scraper]",
            tagColor = HibBlue500,
            icon = Icons.Default.RssFeed,
            description = "Fetches raw verified feeds from RemoteOK, WeWorkRemotely, and company APIs."
          )

          PipelineStep(
            tag = "[3. AI Ingestion API]",
            tagColor = Color(0xFFA855F7),
            icon = Icons.Default.Psychology,
            description = "Cleans unstructured specs, tags '100% WFA' vs 'WFH Regional', and normalizes salary & skills."
          )

          PipelineStep(
            tag = "[4. Mobile Feed Hook]",
            tagColor = Color(0xFFF59E0B),
            icon = Icons.Default.CloudDownload,
            description = "Instantly deploys formatted job cards into the HIB Portal app feed in real-time."
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = {
              onDismiss()
              onSimulateFetch()
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldLive),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("simulate_ai_fetch_btn")
          ) {
            Icon(
              imageVector = Icons.Default.SmartToy,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Simulate AI Fetch Now",
              color = Color.White,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}

@Composable
private fun PipelineStep(
  tag: String,
  tagColor: Color,
  icon: ImageVector,
  description: String
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .background(tagColor.copy(alpha = 0.15f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = tagColor,
        modifier = Modifier.size(14.dp)
      )
    }

    Spacer(modifier = Modifier.width(10.dp))

    Column {
      Text(
        text = tag,
        color = tagColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = description,
        color = Color(0xFFCBD5E1),
        fontSize = 12.sp,
        lineHeight = 16.sp
      )
    }
  }
}
