package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibSlate100
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate900

@Composable
fun PostJobDialog(
  onDismiss: () -> Unit,
  onSubmit: (
    title: String,
    company: String,
    category: String,
    remoteType: String,
    location: String,
    salary: String,
    type: String,
    description: String,
    requirements: List<String>
  ) -> Unit,
  modifier: Modifier = Modifier
) {
  var title by remember { mutableStateOf("") }
  var company by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Software & Tech") }
  var remoteType by remember { mutableStateOf("100% WFA") }
  var location by remember { mutableStateOf("Global / Worldwide") }
  var salary by remember { mutableStateOf("$100,000 - $130,000") }
  var type by remember { mutableStateOf("Full-Time") }
  var description by remember { mutableStateOf("") }
  var requirementsText by remember { mutableStateOf("") }

  val categories = listOf("Software & Tech", "Marketing & Content", "Customer Support", "Executive & Fractional")
  val remoteTypes = listOf("100% WFA", "WFH Regional", "Hybrid")

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      shadowElevation = 8.dp,
      modifier = modifier
        .fillMaxWidth()
        .testTag("post_job_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Post a Remote Job",
              style = MaterialTheme.typography.titleLarge,
              color = HibSlate900,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Reach 12,000+ verified global remote professionals",
              fontSize = 11.sp,
              color = HibSlate500
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

        Spacer(modifier = Modifier.height(14.dp))

        // Job Title
        Text("Job Title *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          placeholder = { Text("e.g. Senior Frontend Architect", fontSize = 13.sp) },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Company
        Text("Company Name *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = company,
          onValueChange = { company = it },
          placeholder = { Text("e.g. HyperScale Media", fontSize = 13.sp) },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Category selection
        Text("Category", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          categories.forEach { cat ->
            val isSelected = category == cat
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) HibBlue600 else HibSlate100)
                .clickable { category = cat }
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = cat,
                color = if (isSelected) Color.White else HibSlate700,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Remote Type
        Text("Remote Arrangement", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          remoteTypes.forEach { rt ->
            val isSelected = remoteType == rt
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) HibBlue700 else HibSlate100)
                .clickable {
                  remoteType = rt
                  location = when (rt) {
                    "100% WFA" -> "Global / Worldwide"
                    "WFH Regional" -> "Work From Home (US/Canada)"
                    else -> "Hybrid Remote (Flexible)"
                  }
                }
                .padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = rt,
                color = if (isSelected) Color.White else HibSlate700,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Location & Salary
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text("Location Label", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
              value = location,
              onValueChange = { location = it },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true,
              shape = RoundedCornerShape(10.dp),
              colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = HibSlate200,
                focusedBorderColor = HibBlue600
              )
            )
          }

          Column(modifier = Modifier.weight(1f)) {
            Text("Salary Range", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
              value = salary,
              onValueChange = { salary = it },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true,
              shape = RoundedCornerShape(10.dp),
              colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = HibSlate200,
                focusedBorderColor = HibBlue600
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Job Description
        Text("Job Description *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          placeholder = { Text("Overview of role, responsibilities, and team structure...", fontSize = 13.sp) },
          modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Requirements
        Text("Key Requirements (one per line)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = HibSlate700)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = requirementsText,
          onValueChange = { requirementsText = it },
          placeholder = { Text("e.g.\n4+ years experience with tech stack\nStrong remote communication\nPrior startup experience", fontSize = 12.sp) },
          modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(20.dp))

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
              if (title.isNotBlank() && company.isNotBlank()) {
                val reqs = requirementsText.lines().map { it.trim() }.filter { it.isNotEmpty() }
                val finalReqs = if (reqs.isEmpty()) {
                  listOf("3+ years of relevant domain experience", "Proven track record in remote teams", "Strong analytical skills")
                } else reqs

                val desc = description.ifBlank { "Join our high-performing remote team to drive strategic innovation." }
                onSubmit(title, company, category, remoteType, location, salary, type, desc, finalReqs)
              }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HibBlue600),
            modifier = Modifier
              .weight(1.5f)
              .testTag("publish_job_btn")
          ) {
            Text("Publish Job Listing", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
