package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldLive
import com.example.ui.theme.HibBlue100
import com.example.ui.theme.HibBlue200
import com.example.ui.theme.HibBlue50
import com.example.ui.theme.HibBlue500
import com.example.ui.theme.HibBlue600
import com.example.ui.theme.HibBlue700
import com.example.ui.theme.HibBlue900
import com.example.ui.theme.HibSlate100
import com.example.ui.theme.HibSlate200
import com.example.ui.theme.HibSlate400
import com.example.ui.theme.HibSlate500
import com.example.ui.theme.HibSlate50
import com.example.ui.theme.HibSlate700
import com.example.ui.theme.HibSlate800
import com.example.ui.theme.HibSlate900

val CategoriesList = listOf(
  "All Categories",
  "Software & Tech",
  "Marketing & Content",
  "Customer Support",
  "Executive & Fractional"
)

val RemoteFiltersList = listOf(
  "All Roles",
  "🇵🇰 Pakistan / WFA",
  "100% WFA",
  "WFH Regional",
  "Hybrid"
)

@Composable
fun HeroSection(
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit,
  selectedCategory: String,
  onCategorySelect: (String) -> Unit,
  selectedRemoteFilter: String,
  onRemoteFilterSelect: (String) -> Unit,
  onAiPipelineClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val focusManager = LocalFocusManager.current

  // Pulsing animation for the verified indicator
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            HibSlate900,
            HibSlate800,
            Color(0xFF131E33)
          )
        )
      )
      .padding(horizontal = 16.dp, vertical = 20.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth()
    ) {
      // Verified Opportunity Pill
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(HibBlue500.copy(alpha = 0.15f))
          .border(1.dp, HibBlue500.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
          .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(7.dp)
            .alpha(pulseAlpha)
            .background(EmeraldLive, CircleShape)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
          text = "Verified Work From Anywhere & WFH Opportunities",
          color = HibBlue200,
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Main Headline
      Text(
        text = "Find Your Next Remote Career",
        style = MaterialTheme.typography.displayMedium,
        color = Color.White,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 32.sp
      )
      Text(
        text = "Without Location Boundaries",
        style = MaterialTheme.typography.titleLarge,
        color = HibBlue200,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Curated by HIB Recruitment Services. Connecting global tech, marketing, and operations talent with top-tier remote employers.",
        color = HibSlate400,
        fontSize = 12.sp,
        lineHeight = 17.sp
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Search & Filter Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
        ) {
          // Search Box
          OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("search_input"),
            placeholder = {
              Text(
                "Job title, key skills, or company...",
                fontSize = 13.sp,
                color = HibSlate500
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = HibSlate400
              )
            },
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                  Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                    tint = HibSlate400
                  )
                }
              }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = HibSlate50,
              unfocusedContainerColor = HibSlate50,
              focusedBorderColor = HibBlue600,
              unfocusedBorderColor = HibSlate200
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Category Chips Scrollable
          Text(
            text = "CATEGORY",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = HibSlate500,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            CategoriesList.forEach { category ->
              val isSelected = selectedCategory == category
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) HibBlue600 else HibSlate100)
                  .clickable { onCategorySelect(category) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = category,
                  color = if (isSelected) Color.White else HibSlate700,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Remote Type Filter Pills
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "TYPE:",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = HibSlate500,
              modifier = Modifier.padding(end = 4.dp)
            )

            RemoteFiltersList.forEach { filter ->
              val isSelected = selectedRemoteFilter == filter
              val label = when (filter) {
                "🇵🇰 Pakistan / WFA" -> "🇵🇰 Pakistan / WFA"
                "100% WFA" -> "🌍 100% WFA"
                "WFH Regional" -> "🏠 WFH Regional"
                "Hybrid" -> "🏢 Hybrid"
                else -> "All Roles"
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) HibBlue700 else HibSlate100)
                  .clickable { onRemoteFilterSelect(filter) }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = label,
                  color = if (isSelected) Color.White else HibSlate700,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // AI Ingest Pipeline Banner Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFF064E3B).copy(alpha = 0.5f))
          .border(1.dp, Color(0xFF059669).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
          .clickable(onClick = onAiPipelineClick)
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.SmartToy,
            contentDescription = null,
            tint = Color(0xFF34D399),
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "AI Auto-Importer Live Demo",
            color = Color(0xFFA7F3D0),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Text(
          text = "View Architecture →",
          color = Color(0xFF6EE7B7),
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}
