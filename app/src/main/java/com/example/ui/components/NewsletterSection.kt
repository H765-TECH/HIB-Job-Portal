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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue500
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900

@Composable
fun NewsletterSection(
  onSubscribe: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var email by remember { mutableStateOf("") }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(HibSlate900)
      .border(1.dp, HibSlate800, RoundedCornerShape(20.dp))
      .padding(20.dp)
      .testTag("newsletter_section"),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(HibBlue500.copy(alpha = 0.2f))
          .border(1.dp, HibBlue500.copy(alpha = 0.3f), CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.MarkEmailRead,
          contentDescription = null,
          tint = HibBlue500,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Get Daily Remote Roles in Your Inbox",
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Join 12,000+ professionals. Hand-picked Work From Anywhere and Work From Home positions delivered every Monday morning.",
        color = HibSlate400,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        lineHeight = 17.sp
      )

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          placeholder = { Text("Enter your work email...", fontSize = 12.sp, color = HibSlate500) },
          singleLine = true,
          modifier = Modifier
            .weight(1f)
            .testTag("newsletter_email_input"),
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = HibSlate800,
            focusedContainerColor = HibSlate800,
            unfocusedBorderColor = HibSlate700,
            focusedBorderColor = HibBlue500,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
          )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
          onClick = {
            if (email.isNotBlank()) {
              onSubscribe(email)
              email = ""
            }
          },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = HibBlue600),
          modifier = Modifier.testTag("newsletter_subscribe_btn")
        ) {
          Text(
            text = "Subscribe",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "No spam. Unsubscribe anytime. Curated by HIB Recruitment.",
        color = HibSlate500,
        fontSize = 10.sp
      )
    }
  }
}
