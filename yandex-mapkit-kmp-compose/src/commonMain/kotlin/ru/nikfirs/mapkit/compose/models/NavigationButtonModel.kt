package ru.nikfirs.mapkit.compose.models

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Model for navigation control buttons
 * * zooming ([ButtonZoomIn][ru.nikfirs.mapkit.compose.ui.ButtonZoomIn] and [ButtonZoomOut][ru.nikfirs.mapkit.compose.ui.ButtonZoomOut])
 * * setting user location [ButtonCurrentPosition][ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition]
 * * setting map to North azimuth [ButtonOrientNorth][ru.nikfirs.mapkit.compose.ui.ButtonOrientNorth]
 *
 * @param zoomButtonModel sets properties for zoom buttons. When null - buttons not shown.
 * @param positionAndNorthRowModifier is a modifier for row containing
 * [ButtonOrientNorth][ru.nikfirs.mapkit.compose.ui.ButtonOrientNorth] and
 * [ButtonCurrentPosition][ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition] placed in Box{}.
 * Use modifier .align to place it in Box.
 * @param positionButtonModel sets properties for user location. When null - button not shown.
 * @param northButtonModel sets properties for north orientation button. When null - button not shown.
 * @param colors set color for content on buttons and button background.
 * If other parameters ([zoomButtonModel], [positionButtonModel] and [northButtonModel] has its
 * colors parameter set, then it has priority for appropriate button.
 */
public data class NavigationButtonModel(
    val zoomButtonModel: ZoomButtonModel? = null,
    val positionAndNorthRowModifier: Modifier = Modifier,
    val positionButtonModel: PositionButtonModel? = null,
    val northButtonModel: NorthButtonModel? = null,
    val colors: ButtonColor,
)


/**
 * Model for zoom control buttons
 * ([ButtonZoomIn][ru.nikfirs.mapkit.compose.ui.ButtonZoomIn]
 * and [ButtonZoomOut][ru.nikfirs.mapkit.compose.ui.ButtonZoomOut])
 *
 * @param modifier sets properties for [MapControlZoom][ru.nikfirs.mapkit.compose.ui.MapControlZoom],
 * which is a column with both zoom buttons.
 * @param colors set color for content on buttons and button background.
 * When set, it has priority over [NavigationButtonModel.colors]
 * @param zoomSpeed regulates zoom value.
 */
public data class ZoomButtonModel(
    val modifier: Modifier = Modifier,
    val colors: ButtonColor? = null,
    val zoomSpeed: Int = 30,
)

/**
 * Model for zoom button actions.
 *
 * @param onPressStart accomplished when button is pressed.
 * @param onPressEnd accomplished when button is released or action changed
 * (pressing out of button and etc.)
 * @param onTap accomplished when button is shortly tapped.
 */
public data class ZoomButtonAction(
    val onPressStart: (() -> Unit)? = null,
    val onPressEnd: (() -> Unit)? = null,
    val onTap: (() -> Unit)? = null,
)

/**
 * Model for control button for setting user location
 * ([ButtonCurrentPosition][ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition])
 *
 * @param modifier sets properties for button.
 * @param colors set color for content on buttons and button background.
 * When set, it has priority over [NavigationButtonModel.colors]
 */
public data class PositionButtonModel(
    val modifier: Modifier = Modifier,
    val colors: ButtonColor? = null,
)


/**
 * Model for control button which sets the map oriented to North
 * ([ButtonOrientNorth][ru.nikfirs.mapkit.compose.ui.ButtonOrientNorth])
 *
 * @param modifier sets properties for button.
 * @param colors set color for content on buttons and button background.
 * When set, it has priority over [NavigationButtonModel.colors]
 * @param showNorthSetButton regulates conditions for button visibility.
 * Default is [NorthButtonVisibility.WHEN_NOT_NORTH]
 */
public data class NorthButtonModel(
    val modifier: Modifier = Modifier,
    val colors: ButtonColor? = null,
    val showNorthSetButton: NorthButtonVisibility = NorthButtonVisibility.WHEN_NOT_NORTH,
)


/**
 * Model for button color settings.
 *
 * @param contentColor sets color for button content.
 * @param backgroundColor set color for button background.
 */
public data class ButtonColor(
    val contentColor: Color,
    val backgroundColor: Color,
)


/**
 * Enum class for control button which sets the map oriented to North
 * ([ButtonOrientNorth][ru.nikfirs.mapkit.compose.ui.ButtonOrientNorth]), containing
 * visible conditions.
 *
 * [ALWAYS] - always visible.
 * [WHEN_NOT_NORTH] - visible only map is not orientated to North.
 * [NEVER] - always not shown.
 */
public enum class NorthButtonVisibility {
    ALWAYS, WHEN_NOT_NORTH, NEVER,
}