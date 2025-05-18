package ru.nikfirs.mapkit.sample.ui

import androidx.compose.ui.graphics.Color

data class CustomColors(
    val northTint: Color,
)

val LightCustomColors = CustomColors(
    northTint = Color(0xFFCCCCCC),
)

val DarkCustomColors = CustomColors(
    northTint = Color(0xFFE6E6E6),
)