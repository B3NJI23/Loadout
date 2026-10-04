package com.b3nji.loadout.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// The two brand colours, plus a few helpers derived from them.
val Graphite = Color(0xFF3B3B3B)
val Violet = Color(0xFF7060F7)

val Background = Color(0xFF2B2B2B)
val CardBorder = Color(0xFF4E4E4E)
val TextPrimary = Color(0xFFF2F2F2)
val TextSecondary = Color(0xFFB0B0B0)
val Danger = Color(0xFFFF6B6B)

private val LoadoutColors = darkColorScheme(
    primary = Violet,
    onPrimary = Color.White,
    background = Background,
    onBackground = TextPrimary,
    surface = Graphite,
    onSurface = TextPrimary,
    surfaceVariant = Graphite,
    onSurfaceVariant = TextSecondary,
    outline = CardBorder,
    error = Danger,
)

private val LoadoutShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
)

@Composable
fun LoadoutTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LoadoutColors,
        shapes = LoadoutShapes,
        content = content,
    )
}
