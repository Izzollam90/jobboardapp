package com.jobboard.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun parseHexColor(hex: String): Color {
    val cleaned = hex.removePrefix("#")
    val argb = ("FF" + cleaned).toLong(16)
    return Color(argb)
}

private val Ink = Color(0xFF0C0D0F)
private val Ink2 = Color(0xFF131518)
private val Panel = Color(0xFF16181C)
private val Text = Color(0xFFECEEF0)
private val Muted = Color(0xFF9BA1A9)
private val OnAccent = Color(0xFF17191C)

@Composable
fun JobBoardTheme(accentHex: String, content: @Composable () -> Unit) {
    val accent = parseHexColor(accentHex)
    val scheme = darkColorScheme(
        primary = accent,
        onPrimary = OnAccent,
        primaryContainer = accent.copy(alpha = 0.18f),
        onPrimaryContainer = accent,
        secondary = accent,
        onSecondary = OnAccent,
        background = Ink,
        onBackground = Text,
        surface = Panel,
        onSurface = Text,
        surfaceVariant = Ink2,
        onSurfaceVariant = Muted,
        outline = Color(0x1AFFFFFF),
        outlineVariant = Color(0x2EFFFFFF)
    )
    MaterialTheme(colorScheme = scheme, content = content)
}
