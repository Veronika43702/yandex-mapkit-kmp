package ru.nikfirs.mapkit.compose.utils

import androidx.compose.ui.graphics.toArgb
import ru.nikfirs.mapkit.Color
import ru.nikfirs.mapkit.toArgb
import androidx.compose.ui.graphics.Color as ComposeColor

public fun ComposeColor.toMapkitColor(): Color {
    return Color.fromArgb(toArgb())
}

public fun Color.toComposeColor(): androidx.compose.ui.graphics.Color {
    return ComposeColor(toArgb())
}