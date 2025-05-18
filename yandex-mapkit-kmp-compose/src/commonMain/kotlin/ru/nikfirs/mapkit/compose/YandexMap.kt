package ru.nikfirs.mapkit.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nikfirs.mapkit.compose.composition.MapUpdaterState
import ru.nikfirs.mapkit.compose.composition.launchMapComposition
import ru.nikfirs.mapkit.compose.models.NavigationButtonModel
import ru.nikfirs.mapkit.compose.models.ZoomButtonAction
import ru.nikfirs.mapkit.compose.models.ZoomButtonModel
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

@YandexMapsComposeExperimentalApi
@Composable
public fun YandexMapWithButtons(
    modifier: Modifier = Modifier,
    locationState: UserLocationState,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    locationConfig: UserLocationConfig = UserLocationConfig(),
    config: MapConfig = MapConfig(isNightModeEnabled = isSystemInDarkTheme()),
    navigationButtonModel: NavigationButtonModel? = NavigationButtonModel(
        zoomButtonModel = ZoomButtonModel(
            contentColor = MaterialTheme.colorScheme.onSurface,
            backgroundColor = MaterialTheme.colorScheme.surface,
        )
    ),
    content: @[Composable YandexMapComposable] () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var isZooming by remember { mutableStateOf(false) }
    var zoomDirection by remember { mutableStateOf(0) }

    LaunchedEffect(isZooming, zoomDirection) {
        if (isZooming && zoomDirection != 0) {
            while (isZooming) {
                val currentZoom = cameraPositionState.position.zoom
                val newZoom = currentZoom + (zoomDirection * 0.1f)
                cameraPositionState.position = CameraPosition(
                    cameraPositionState.position.target,
                    newZoom.coerceIn(2f, 21f),
                    cameraPositionState.position.tilt,
                    cameraPositionState.position.azimuth
                )
                delay(navigationButtonModel?.zoomButtonModel?.zoomSpeed?.toLong() ?: 30)
            }
        }
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

        if (navigationButtonModel != null) {
            Column(
                modifier = navigationButtonModel.leftButtonCardModifier,
                verticalArrangement = Arrangement.spacedBy(
                    navigationButtonModel.leftCardButtonArrangementSpace
                ),
            ) {
                navigationButtonModel.zoomButtonModel?.let { zoomModel ->
                    MapControlZoom(
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
                                isZooming = false
                                zoomDirection = 0
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
                        contentColor = zoomModel.contentColor,
                        backgroundColor = zoomModel.backgroundColor,
                    )
                }
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
            cameraPositionState.position.tilt,
            cameraPositionState.position.azimuth
        )
        delay((durationMs / steps).toLong())
    }
}

internal val LocalMap = compositionLocalOf<Map> { error("No Map instance provided") }