package ru.nikfirs.mapkit.compose.models

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

public data class NavigationButtonModel(
    val zoomButtonModel: ZoomButtonModel? = null,
    val positionButtonModel: PositionButtonModel? = null,
    val northButtonModel: NorthButtonModel? = null,
    val colors: ButtonColor,
)

public data class ZoomButtonModel(
    val zoomModifier: Modifier = Modifier,
    val zoomSpeed: Int = 30,
    val colors: ButtonColor? = null,
)

public data class PositionButtonModel(
    val positionModifier: Modifier = Modifier,
    val colors: ButtonColor? = null,
)

public data class NorthButtonModel(
    val northButtonModifier: Modifier = Modifier,
    val colors: ButtonColor? = null,
    val showNorthSetButton: NorthButtonVisibility = NorthButtonVisibility.WHEN_NOT_NORTH,
)

public data class ButtonColor(
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