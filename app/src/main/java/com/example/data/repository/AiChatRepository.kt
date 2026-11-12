package com.example.data.repository

import android.util.Log
import com.example.data.model.ChatMessage
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Content
import com.google.firebase.ai.type.content
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withTimeout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

private const val TAG = "AiChatRepository"

class AiChatRepository {

  private val systemPrompt = """
    You are the HIB AI Remote Career Advisor, the official career intelligence consultant for HIB Recruitment Services.
    Your mission is to help global remote job seekers discover and win high-paying 100% Work From Anywhere (WFA) roles, with dedicated expertise for talent in developing tech hubs including Pakistan, India, EMEA, and Latin America.

    Your core competencies:
    1. 100% Work From Anywhere (WFA): Explain the critical difference between worldwide borderless roles and country-restricted WFH (e.g. US/EU only). Direct candidates to borderless remote roles.
    2. Pakistan & Global Talent Support: Explain how developers, marketers, designers, and managers in Pakistan can secure US/EU remote contracts, invoice legally as foreign independent contractors (PSEB / FBR tax filer perks), and receive payments in USD via Payoneer, Wise, direct wire transfers (Meezan Bank, HBL, Bank Alfalah), or Deel/Remote.com.
    3. Timezone Management: Share proven strategies for managing Pakistan Standard Time (PKT / UTC+5) overlap with US East/West coast (EST/PST) and European (CET/GMT) distributed teams using asynchronous communication (Loom, Slack, Notion).
    4. Resume & ATS Optimization: Practical suggestions to tailor resumes for international recruiters, showcasing asynchronous reliability, clear deliverables, and English fluency.
    5. Compensation Benchmarking: Realistic global remote compensation figures ($40,000 - $180,000+ USD) and negotiation tips.

    Communication style: Professional, structured, encouraging, actionable, and warm. Use bullet points and bold highlights for clarity.
  """.trimIndent()

  private val generativeModel by lazy {
    Firebase.ai.generativeModel(
      modelName = "gemini-3.8-flash",
      systemInstruction = content { text(systemPrompt) }
    )
  }

  private val _messages = MutableStateFlow<List<ChatMessage>>(initialMessages)
  val messages: Flow<List<ChatMessage>> = _messages.asStateFlow()

  private val _isGenerating = MutableStateFlow(false)
  val isGenerating: Flow<Boolean> = _isGenerating.asStateFlow()

  suspend fun sendMessage(userText: String) {
    if (userText.isBlank()) return

    val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    val currentTime = timeFormatter.format(Date())

    val userMessage = ChatMessage(
      id = UUID.randomUUID().toString(),
      sender = "user",
      text = userText.trim(),
      timestamp = currentTime,
      isStreaming = false
    )

    val assistantMessageId = UUID.randomUUID().toString()
    val placeholderAssistant = ChatMessage(
      id = assistantMessageId,
      sender = "model",
      text = "",
      timestamp = currentTime,
      isStreaming = true
    )

    _messages.value = _messages.value + userMessage + placeholderAssistant
    _isGenerating.value = true

    try {
      // Build conversation history for multi-turn chat
      val historyList = mutableListOf<Content>()
      _messages.value.dropLast(1).forEach { msg ->
        if (msg.text.isNotBlank()) {
          historyList.add(content(role = msg.sender) { text(msg.text) })
        }
      }

      val chat = generativeModel.startChat(history = historyList)
      val accumulated = StringBuilder()

      withTimeout(60_000L) {
        chat.sendMessageStream(userText.trim()).collect { chunk ->
          val chunkText = chunk.text.orEmpty()
          accumulated.append(chunkText)
          _messages.value = _messages.value.map { msg ->
            if (msg.id == assistantMessageId) {
              msg.copy(text = accumulated.toString(), isStreaming = true)
            } else msg
          }
        }
      }

      _messages.value = _messages.value.map { msg ->
        if (msg.id == assistantMessageId) {
          msg.copy(
            text = if (accumulated.isNotBlank()) accumulated.toString() else "I'm here to help with your remote career! How can I assist you today?",
            isStreaming = false
          )
        } else msg
      }
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      Log.e(TAG, "Gemini chat generation failed", e)
      val errorText = when {
        e.message?.contains("403") == true -> "App Check authentication connecting. Please verify your internet connection and try again."
        e.message?.contains("timed out") == true -> "The response timed out. Please try sending a shorter question."
        else -> "I encountered an issue processing your request: ${e.localizedMessage ?: "Unknown error"}. Please tap to retry."
      }
      _messages.value = _messages.value.map { msg ->
        if (msg.id == assistantMessageId) {
          msg.copy(text = errorText, isStreaming = false)
        } else msg
      }
    } finally {
      _isGenerating.value = false
    }
  }

  fun clearChat() {
    _messages.value = initialMessages
  }

  companion object {
    val initialMessages = listOf(
      ChatMessage(
        id = "welcome-1",
        sender = "model",
        text = "Hello! I am your **HIB AI Remote Career Advisor** powered by Gemini 3.8.\n\nI specialize in helping professionals worldwide—including talent in **Pakistan**, Southeast Asia, and globally—navigate **100% Work From Anywhere (WFA)** roles, cross-border USD payments (Payoneer, Wise, direct wire), and timezone scheduling (PKT to US/EU).\n\nHow can I help advance your remote career today?",
        timestamp = "Just now",
        isStreaming = false
      )
    )
  }
}
