package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate900

@Composable
fun AgencyConsultationDialog(
  onDismiss: () -> Unit,
  onSubmit: (company: String, email: String, role: String, urgency: String, message: String) -> Unit,
  modifier: Modifier = Modifier
) {
  var company by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var role by remember { mutableStateOf("") }
  var urgency by remember { mutableStateOf("Immediate (within 2 weeks)") }
  var message by remember { mutableStateOf("") }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      shadowElevation = 8.dp,
      modifier = modifier
        .fillMaxWidth()
        .testTag("consultation_dialog")
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
              text = "Request Recruitment Support",
              style = MaterialTheme.typography.titleLarge,
              color = HibSlate900,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Let HIB recruiters source and screen remote candidates for your team",
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Company Name *",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = company,
          onValueChange = { company = it },
          placeholder = { Text("e.g. Acme Cloud Corp", fontSize = 13.sp) },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Work Email *",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          placeholder = { Text("hiring@acme.com", fontSize = 13.sp) },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Role You Are Hiring For *",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = role,
          onValueChange = { role = it },
          placeholder = { Text("e.g. Senior Full Stack Engineer", fontSize = 13.sp) },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Hiring Timeline",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = urgency,
          onValueChange = { urgency = it },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Additional Requirements / Notes",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = HibSlate700
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
          value = message,
          onValueChange = { message = it },
          placeholder = { Text("Timezones, tech stack, or budget expectations...", fontSize = 13.sp) },
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
              if (company.isNotBlank() && email.isNotBlank() && role.isNotBlank()) {
                onSubmit(company, email, role, urgency, message)
              }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HibBlue600),
            modifier = Modifier
              .weight(1.5f)
              .testTag("submit_consultation_btn")
          ) {
            Text("Submit Request", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
