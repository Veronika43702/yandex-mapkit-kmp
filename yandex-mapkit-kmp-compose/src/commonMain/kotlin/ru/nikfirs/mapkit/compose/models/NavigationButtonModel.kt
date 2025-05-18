package ru.nikfirs.mapkit.compose.models

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public data class NavigationButtonModel(
    val leftButtonCardModifier: Modifier = Modifier.padding(end = 10.dp),
    val leftCardButtonArrangementSpace: Dp = 30.dp,
    val zoomButtonModel: ZoomButtonModel? = null,
    val northButtonCardModifier: Modifier = Modifier,
    val showNorthSetButton: NorthButtonVisibility = NorthButtonVisibility.WHEN_NOT_NORTH,
)

public data class ZoomButtonModel(
    val zoomSpeed: Int = 30,
    val contentColor: Color,
    val backgroundColor: Color,
)

public enum class NorthButtonVisibility {
    ALWAYS, WHEN_NOT_NORTH, NEVER,
}

public data class ZoomButtonAction(
    val onPressStart: (() -> Unit)? = null,
    val onPressEnd: (() -> Unit)? = null,
    val onTap: (() -> Unit)? = null,
)