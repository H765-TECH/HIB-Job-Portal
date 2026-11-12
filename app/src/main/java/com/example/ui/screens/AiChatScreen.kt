package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
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

val QuickPrompts = listOf(
  "🇵🇰 What are the best WFA jobs for candidates in Pakistan?",
  "💵 How do remote USD payments work in Pakistan (Wise / Payoneer)?",
  "🕒 How to manage PKT timezone overlap with US and EU teams?",
  "📄 Tips to optimize a remote software engineer CV for global roles",
  "🌍 How does 100% WFA differ from WFH Regional?"
)

@Composable
fun AiChatScreen(
  messages: List<ChatMessage>,
  isGenerating: Boolean,
  onSendMessage: (String) -> Unit,
  onClearChat: () -> Unit,
  modifier: Modifier = Modifier
) {
  var inputText by remember { mutableStateOf("") }
  val listState = rememberLazyListState()
  val focusManager = LocalFocusManager.current

  LaunchedEffect(messages.size, messages.lastOrNull()?.text?.length) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(HibBlue50.copy(alpha = 0.2f))
      .testTag("ai_chat_screen")
  ) {
    // Header Bar
    Surface(
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(
                Brush.linearGradient(
                  listOf(
                    Color(0xFF4F46E5),
                    Color(0xFF2563EB)
                  )
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "HIB Career Advisor",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = HibSlate900
              )
              Spacer(modifier = Modifier.width(6.dp))
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(EmeraldLive)
              )
            }
            Text(
              text = "Powered by Gemini 3.8 Flash • Global & Pakistan WFA Expert",
              fontSize = 10.5.sp,
              color = HibBlue700,
              fontWeight = FontWeight.Medium
            )
          }
        }

        IconButton(
          onClick = onClearChat,
          modifier = Modifier.testTag("clear_chat_btn")
        ) {
          Icon(
            imageVector = Icons.Default.DeleteSweep,
            contentDescription = "Clear Chat",
            tint = HibSlate500
          )
        }
      }
    }

    // Scrollable Chat Thread
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(
        items = messages,
        key = { it.id }
      ) { msg ->
        if (msg.sender == "user") {
          UserMessageBubble(message = msg)
        } else {
          ModelMessageBubble(message = msg)
        }
      }

      if (isGenerating && messages.lastOrNull()?.sender != "model") {
        item(key = "typing_indicator") {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(16.dp),
              strokeWidth = 2.dp,
              color = HibBlue600
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Gemini is thinking...",
              fontSize = 11.sp,
              color = HibSlate500,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }

    // Suggested Prompts Carousel
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      QuickPrompts.forEach { prompt ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(HibSlate100)
            .border(1.dp, HibSlate200, RoundedCornerShape(16.dp))
            .clickable(enabled = !isGenerating) {
              onSendMessage(prompt)
            }
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = prompt,
            fontSize = 11.sp,
            color = HibSlate700,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }

    // Bottom Input Bar
    Surface(
      color = Color.White,
      shadowElevation = 4.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = inputText,
          onValueChange = { inputText = it },
          placeholder = {
            Text(
              "Ask about WFA jobs, Pakistan USD contracts...",
              fontSize = 12.5.sp,
              color = HibSlate500
            )
          },
          singleLine = false,
          maxLines = 3,
          modifier = Modifier
            .weight(1f)
            .testTag("chat_input_field"),
          shape = RoundedCornerShape(20.dp),
          colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = HibSlate50,
            focusedContainerColor = HibSlate50,
            unfocusedBorderColor = HibSlate200,
            focusedBorderColor = HibBlue600
          ),
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
          keyboardActions = KeyboardActions(
            onSend = {
              if (inputText.isNotBlank() && !isGenerating) {
                onSendMessage(inputText)
                inputText = ""
                focusManager.clearFocus()
              }
            }
          )
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = {
            if (inputText.isNotBlank() && !isGenerating) {
              onSendMessage(inputText)
              inputText = ""
              focusManager.clearFocus()
            }
          },
          enabled = inputText.isNotBlank() && !isGenerating,
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(if (inputText.isNotBlank() && !isGenerating) HibBlue600 else HibSlate200)
            .testTag("send_chat_btn")
        ) {
          Icon(
            imageVector = Icons.Default.Send,
            contentDescription = "Send",
            tint = if (inputText.isNotBlank() && !isGenerating) Color.White else HibSlate400,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}

@Composable
fun UserMessageBubble(message: ChatMessage) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End
  ) {
    Column(
      horizontalAlignment = Alignment.End,
      modifier = Modifier.widthIn(max = 280.dp)
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 4.dp))
          .background(HibBlue600)
          .padding(horizontal = 14.dp, vertical = 10.dp)
      ) {
        Text(
          text = message.text,
          color = Color.White,
          fontSize = 13.5.sp,
          lineHeight = 19.sp
        )
      }
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = message.timestamp,
        fontSize = 10.sp,
        color = HibSlate400
      )
    }
  }
}

@Composable
fun ModelMessageBubble(message: ChatMessage) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(30.dp)
        .clip(CircleShape)
        .background(Color(0xFF1D4ED8)),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "HIB",
        color = Color.White,
        fontWeight = FontWeight.Black,
        fontSize = 9.sp
      )
    }

    Spacer(modifier = Modifier.width(8.dp))

    Column(modifier = Modifier.widthIn(max = 300.dp)) {
      Card(
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, HibSlate200),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
          if (message.text.isBlank() && message.isStreaming) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              CircularProgressIndicator(
                modifier = Modifier.size(12.dp),
                strokeWidth = 1.5.dp,
                color = HibBlue600
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Analyzing with Gemini...", fontSize = 11.sp, color = HibSlate500)
            }
          } else {
            Text(
              text = message.text,
              color = HibSlate900,
              fontSize = 13.sp,
              lineHeight = 19.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "HIB Advisor • ${message.timestamp}",
        fontSize = 9.5.sp,
        color = HibSlate400,
        modifier = Modifier.padding(start = 4.dp)
      )
    }
  }
}
