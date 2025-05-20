package ru.nikfirs.mapkit.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nikfirs.mapkit.compose.composition.MapUpdaterState
import ru.nikfirs.mapkit.compose.composition.launchMapComposition
import ru.nikfirs.mapkit.compose.models.ButtonColor
import ru.nikfirs.mapkit.compose.models.NavigationButtonModel
import ru.nikfirs.mapkit.compose.models.NorthButtonVisibility
import ru.nikfirs.mapkit.compose.models.ZoomButtonAction
import ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition
import ru.nikfirs.mapkit.compose.ui.ButtonOrientNorth
import ru.nikfirs.mapkit.compose.ui.MapControlZoom
import ru.nikfirs.mapkit.compose.user_location.UserLocationConfig
import ru.nikfirs.mapkit.compose.user_location.UserLocationState
import ru.nikfirs.mapkit.compose.user_location.UserLocationUpdater
import ru.nikfirs.mapkit.compose.user_location.UserLocationUpdaterState
import ru.nikfirs.mapkit.map.CameraPosition
import ru.nikfirs.mapkit.map.Map

@Composable
public fun YandexMap(
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    modifier: Modifier = Modifier,
    config: MapConfig = MapConfig(isNightModeEnabled = isSystemInDarkTheme()),
    content: @[Composable YandexMapComposable] () -> Unit = {},
) {
    val mapUpdaterState = remember {
        MapUpdaterState(
            cameraPositionState = cameraPositionState,
            config = config,
        )
    }.also {
        it.cameraPositionState = cameraPositionState
        it.config = config
    }

    val parentComposition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    var subcompositionJob by remember { mutableStateOf<Job?>(null) }
    val parentCompositionScope = rememberCoroutineScope()

    NativeYandexMap(
        modifier = modifier,
        update = { mapView ->
            if (subcompositionJob == null) {
                subcompositionJob = parentCompositionScope.launchMapComposition(
                    parentComposition = parentComposition,
                    mapView = mapView,
                    mapUpdaterState = mapUpdaterState,
                    content = currentContent,
                )
            }
        }
    )
}

@YandexMapsComposeExperimentalApi
@Composable
public fun YandexMap(
    locationState: UserLocationState,
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    locationConfig: UserLocationConfig = UserLocationConfig(),
    config: MapConfig = MapConfig(isNightModeEnabled = isSystemInDarkTheme()),
    content: @[Composable YandexMapComposable] () -> Unit = {},
) {
    val userLocationUpdaterState = remember {
        UserLocationUpdaterState(userLocation = locationConfig)
    }.also {
        it.userLocation = locationConfig
    }
    YandexMap(
        cameraPositionState = cameraPositionState,
        modifier = modifier,
        config = config,
    ) {
        UserLocationUpdater(locationState, userLocationUpdaterState)
        content()
    }
}

/**
 * Function with map and navigation control buttons:
 * * zooming ([ButtonZoomIn][ru.nikfirs.mapkit.compose.ui.ButtonZoomIn] and [ButtonZoomOut][ru.nikfirs.mapkit.compose.ui.ButtonZoomOut])
 * * setting user location [ButtonCurrentPosition][ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition]
 * * setting map to North azimuth [ButtonOrientNorth][ru.nikfirs.mapkit.compose.ui.ButtonOrientNorth]
 *
 * @param navigationButtonModel sets properties for navigation control buttons.
 * When null - no button is shown (better then use YandexMap function - it's without buttons).
 * You can customise all buttons directly using appropriate functions
 * instead of default button arrangement and view by navigationButtonModel.
 * @param onNoPermissionGranted executed when
 * [ButtonCurrentPosition][ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition] was clicked and
 * [hasPermission] equals false.
 * @param hasPermission shows if there's location permission from user. If it is true, click on
 * [ButtonCurrentPosition][ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition] works, if false
 * [ButtonCurrentPosition][ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition] executes on click.
 */
@YandexMapsComposeExperimentalApi
@Composable
public fun YandexMapWithButtons(
    modifier: Modifier = Modifier,
    locationState: UserLocationState,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    locationConfig: UserLocationConfig = UserLocationConfig(),
    config: MapConfig = MapConfig(isNightModeEnabled = isSystemInDarkTheme()),
    navigationButtonModel: NavigationButtonModel? = NavigationButtonModel(
        colors = ButtonColor(
            contentColor = MaterialTheme.colorScheme.onSurface,
            backgroundColor = MaterialTheme.colorScheme.surface,
        ),
    ),
    hasPermission: State<Boolean>,
    onNoPermissionGranted: () -> Unit = {},
    content: @[Composable YandexMapComposable] () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    // Zoom control
    var isZooming by remember { mutableStateOf(false) }
    var zoomDirection by remember { mutableStateOf(0) }
    LaunchedEffect(isZooming, zoomDirection) {
        if (isZooming && zoomDirection != 0) {
            while (isZooming) {
                val currentZoom = cameraPositionState.position.zoom
                val newZoom = currentZoom + (zoomDirection * 0.1f)
                cameraPositionState.position = cameraPositionState.position.copy(
                    zoom = newZoom.coerceIn(2f, 21f),
                )
                delay(navigationButtonModel?.zoomButtonModel?.zoomSpeed?.toLong() ?: 30)
            }
        }
    }

    // North Orientation Settings
    val currentAzimuth = remember { mutableStateOf(0f) }
    LaunchedEffect(cameraPositionState.position.azimuth) {
        val mapAzimuth = cameraPositionState.position.azimuth
        val normalizedMapAzimuth = ((mapAzimuth % 360) + 360) % 360

        currentAzimuth.value = -normalizedMapAzimuth
    }

    Box(
        modifier = modifier,
    ) {
        YandexMap(
            cameraPositionState = cameraPositionState,
            locationState = locationState,
            locationConfig = locationConfig,
            config = config,
            modifier = Modifier.fillMaxSize(),
        ) {
            content()
        }


        navigationButtonModel?.zoomButtonModel?.let { zoomModel ->
            MapControlZoom(
                modifier = navigationButtonModel.zoomButtonModel.modifier,
                zoomInButtonAction = ZoomButtonAction(
                    onTap = {
                        scope.launch {
                            val currentZoom = cameraPositionState.position.zoom
                            val targetZoom = (currentZoom + 1f).coerceAtMost(21f)
                            animateZoom(
                                cameraPositionState,
                                targetZoom,
                                zoomSpeed = zoomModel.zoomSpeed
                            )
                        }
                    },
                    onPressStart = {
                        isZooming = true
                        zoomDirection = 1
                    },
                    onPressEnd = {
                        zoomDirection = 0
                        isZooming = false
                    }
                ),
                zoomOutButtonAction = ZoomButtonAction(onTap = {
                    scope.launch {
                        val currentZoom = cameraPositionState.position.zoom
                        val targetZoom = (currentZoom + -1f).coerceAtMost(21f)
                        animateZoom(
                            cameraPositionState,
                            targetZoom,
                            zoomSpeed = zoomModel.zoomSpeed
                        )
                    }
                },
                    onPressStart = {
                        isZooming = true
                        zoomDirection = -1
                    },
                    onPressEnd = {
                        isZooming = false
                        zoomDirection = 0
                    }
                ),
                contentColor = zoomModel.colors?.contentColor
                    ?: navigationButtonModel.colors.contentColor,
                backgroundColor = zoomModel.colors?.backgroundColor
                    ?: navigationButtonModel.colors.backgroundColor,
            )
        }

        Row(
            modifier = navigationButtonModel?.positionAndNorthRowModifier ?: Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            navigationButtonModel?.northButtonModel?.let { northButton ->
                if (northButton.showNorthSetButton == NorthButtonVisibility.ALWAYS
                    || (northButton.showNorthSetButton == NorthButtonVisibility.WHEN_NOT_NORTH
                            && currentAzimuth.value != 0f)
                ) {
                    ButtonOrientNorth(
                        modifier = northButton.modifier,
                        onClick = {
                            scope.launch {
                                val currentPosition = cameraPositionState.position
                                val currentMapAzimuth =
                                    ((currentPosition.azimuth % 360) + 360) % 360
                                val targetAzimuth = if (currentMapAzimuth > 180) 360f else 0f
                                val anim = Animatable(currentMapAzimuth)
                                anim.animateTo(
                                    targetValue = targetAzimuth,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = FastOutSlowInEasing
                                    )
                                ) {
                                    cameraPositionState.position = currentPosition.copy(
                                        azimuth = this.value
                                    )
                                }
                                if (targetAzimuth == 360f) {
                                    cameraPositionState.position =
                                        currentPosition.copy(azimuth = 0f)
                                    currentAzimuth.value = 0f
                                }
                            }
                        },
                        rotation = currentAzimuth.value,
                        contentColor = northButton.colors?.contentColor
                            ?: navigationButtonModel.colors.contentColor,
                        backgroundColor = northButton.colors?.backgroundColor
                            ?: navigationButtonModel.colors.backgroundColor,
                    )
                }
            }

            navigationButtonModel?.positionButtonModel?.let { positionButton ->
                ButtonCurrentPosition(
                    modifier = positionButton.modifier,
                    onClick = {
                        if (hasPermission.value) {
                            locationState.cameraPosition?.let {
                                cameraPositionState.position = it.copy(azimuth = 0f, tilt = 0f)
                            }
                        } else {
                            onNoPermissionGranted()
                        }
                    },
                    shape = CircleShape,
                    size = 56.dp,
                    contentColor = positionButton.colors?.contentColor
                        ?: navigationButtonModel.colors.contentColor,
                    backgroundColor = positionButton.colors?.backgroundColor
                        ?: navigationButtonModel.colors.backgroundColor,
                )
            }
        }
    }
}

private suspend fun animateZoom(
    cameraPositionState: CameraPositionState,
    targetZoom: Float,
    zoomSpeed: Int,
    durationMs: Int = 300,
) {
    val startZoom = cameraPositionState.position.zoom
    val steps = (durationMs / zoomSpeed).coerceAtLeast(1)
    val zoomStep = (targetZoom - startZoom) / steps

    repeat(steps) {
        val newZoom = (startZoom + zoomStep * (it + 1)).coerceIn(2f, 21f)
        cameraPositionState.position = CameraPosition(
            cameraPositionState.position.target,
            newZoom,
            cameraPositionState.position.azimuth,
            cameraPositionState.position.tilt
        )
        delay((durationMs / steps).toLong())
    }
}

internal val LocalMap = compositionLocalOf<Map> { error("No Map instance provided") }