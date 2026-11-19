package io.github.mauriciogamedev.ffcompanion.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object FFPalette {
    val Background = Color(0xFF08080B)
    val Surface = Color(0xFF141419)
    val SurfaceAlt = Color(0xFF1C1C23)
    val Accent = Color(0xFFFF304D)
    val AccentSoft = Color(0xFF6F1828)
    val TextPrimary = Color(0xFFF5F5F7)
    val TextSecondary = Color(0xFF9B9BA5)
    val Border = Color(0xFF34343D)
}

private val FFColorScheme = darkColorScheme(
    primary = FFPalette.Accent,
    onPrimary = Color.White,
    secondary = Color(0xFFFF8192),
    onSecondary = Color(0xFF24070D),
    background = FFPalette.Background,
    onBackground = FFPalette.TextPrimary,
    surface = FFPalette.Surface,
    onSurface = FFPalette.TextPrimary,
    surfaceVariant = FFPalette.SurfaceAlt,
    onSurfaceVariant = FFPalette.TextSecondary,
    outline = FFPalette.Border
)

@Composable
fun FFCompanionTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = FFColorScheme, content = content)
}
