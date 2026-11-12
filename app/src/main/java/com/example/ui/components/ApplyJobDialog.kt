package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Job
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
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
fun ApplyJobDialog(
  job: Job,
  onDismiss: () -> Unit,
  onSubmit: (name: String, email: String, portfolio: String, resume: String, note: String) -> Unit,
  modifier: Modifier = Modifier
) {
  var name by remember { mutableStateOf("Alex Mercer") }
  var email by remember { mutableStateOf("alex.mercer@gmail.com") }
  var portfolio by remember { mutableStateOf("https://linkedin.com/in/alexmercer-dev") }
  var resumeName by remember { mutableStateOf("Alex_Mercer_CV_2026.pdf") }
  var note by remember {
    mutableStateOf("I have 5+ years of remote experience and deep expertise aligned with your tech stack.")
  }
  var isUploadingResume by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      shadowElevation = 8.dp,
      modifier = modifier
        .fillMaxWidth()
        .testTag("apply_job_dialog")
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
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Quick Job Application",
              style = MaterialTheme.typography.titleLarge,
              color = HibSlate900,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "${job.title} • ${job.company}",
              fontSize = 12.sp,
              color = HibBlue700,
              fontWeight = FontWeight.SemiBold
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = HibSlate500
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Full Name
        Text(
          text = "Full Name *",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Work Email
        Text(
          text = "Email Address *",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Portfolio / GitHub
        Text(
          text = "Portfolio or LinkedIn URL",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = portfolio,
          onValueChange = { portfolio = it },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Resume Upload simulation
        Text(
          text = "Resume Attachment",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(HibSlate100)
            .border(1.dp, HibSlate200, RoundedCornerShape(10.dp))
            .clickable {
              resumeName = if (resumeName.contains("Senior")) {
                "Alex_Mercer_Executive_Profile.pdf"
              } else {
                "Alex_Mercer_Senior_Resume.pdf"
              }
            }
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Icon(
              imageVector = Icons.Default.Description,
              contentDescription = null,
              tint = HibBlue600,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = resumeName,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              color = HibSlate800
            )
          }

          Text(
            text = "Tap to switch",
            fontSize = 11.sp,
            color = HibBlue600,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Cover Note
        Text(
          text = "Cover Note / Introduction",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = note,
          onValueChange = { note = it },
          modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Actions
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("Cancel", color = HibSlate700)
          }

          Button(
            onClick = {
              if (name.isNotBlank() && email.isNotBlank()) {
                onSubmit(name, email, portfolio, resumeName, note)
              }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HibBlue600),
            modifier = Modifier
              .weight(1.5f)
              .testTag("submit_application_btn")
          ) {
            Text("Submit Application", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
