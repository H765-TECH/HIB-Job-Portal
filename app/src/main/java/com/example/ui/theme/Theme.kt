package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = HibBlue500,
  onPrimary = Color.White,
  primaryContainer = HibBlue800,
  onPrimaryContainer = HibBlue100,
  secondary = HibBlue200,
  onSecondary = HibSlate900,
  tertiary = EmeraldLive,
  background = HibSlate900,
  surface = HibSlate800,
  onBackground = Color.White,
  onSurface = Color.White,
  surfaceVariant = HibSlate700,
  onSurfaceVariant = HibSlate200,
  outline = HibSlate600
)

private val LightColorScheme = lightColorScheme(
  primary = HibBlue600,
  onPrimary = Color.White,
  primaryContainer = HibBlue100,
  onPrimaryContainer = HibBlue800,
  secondary = HibBlue700,
  onSecondary = Color.White,
  tertiary = EmeraldLive,
  background = HibSlate50,
  surface = Color.White,
  onBackground = HibSlate900,
  onSurface = HibSlate900,
  surfaceVariant = HibSlate100,
  onSurfaceVariant = HibSlate600,
  outline = HibSlate200
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent HIB branding
  content: @Composable () -> Unit,
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}
