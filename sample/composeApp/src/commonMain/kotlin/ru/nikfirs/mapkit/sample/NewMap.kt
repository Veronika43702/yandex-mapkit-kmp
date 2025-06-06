package ru.nikfirs.mapkit.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import ru.nikfirs.mapkit.compose.Circle
import ru.nikfirs.mapkit.compose.ClusterGroup
import ru.nikfirs.mapkit.compose.ClusterItem
import ru.nikfirs.mapkit.compose.Clustering
import ru.nikfirs.mapkit.compose.MapConfig
import ru.nikfirs.mapkit.compose.MapLogoConfig
import ru.nikfirs.mapkit.compose.Placemark
import ru.nikfirs.mapkit.compose.Polygon
import ru.nikfirs.mapkit.compose.Polyline
import ru.nikfirs.mapkit.compose.YandexMapComposable
import ru.nikfirs.mapkit.compose.YandexMapWithButtons
import ru.nikfirs.mapkit.compose.YandexMapsComposeExperimentalApi
import ru.nikfirs.mapkit.compose.bindToLifecycleOwner
import ru.nikfirs.mapkit.compose.imageProvider
import ru.nikfirs.mapkit.compose.models.ButtonColor
import ru.nikfirs.mapkit.compose.models.NavigationButtonModel
import ru.nikfirs.mapkit.compose.models.NorthButtonModel
import ru.nikfirs.mapkit.compose.models.PositionButtonModel
import ru.nikfirs.mapkit.compose.models.ZoomButtonModel
import ru.nikfirs.mapkit.compose.rememberAndInitializeMapKit
import ru.nikfirs.mapkit.compose.rememberCameraPositionState
import ru.nikfirs.mapkit.compose.rememberCircleState
import ru.nikfirs.mapkit.compose.rememberPlacemarkState
import ru.nikfirs.mapkit.compose.rememberPolygonState
import ru.nikfirs.mapkit.compose.rememberPolylineState
import ru.nikfirs.mapkit.compose.user_location.UserLocationConfig
import ru.nikfirs.mapkit.compose.user_location.rememberUserLocationState
import ru.nikfirs.mapkit.geometry.Circle
import ru.nikfirs.mapkit.geometry.Point
import ru.nikfirs.mapkit.logo.LogoAlignment
import ru.nikfirs.mapkit.logo.LogoHorizontalAlignment
import ru.nikfirs.mapkit.logo.LogoVerticalAlignment
import ru.nikfirs.mapkit.map.CameraPosition
import ru.nikfirs.mapkit.sample.ui.LocalCustomColors

@OptIn(YandexMapsComposeExperimentalApi::class)
@Composable
fun NewMapScreen(
    modifier: Modifier = Modifier,
    hasPermission: State<Boolean>,
    onNoPermissionGranted: () -> Unit = {},
) {
    val customColors = LocalCustomColors.current
    rememberAndInitializeMapKit().bindToLifecycleOwner()
    val cameraPositionState = rememberCameraPositionState { position = startPosition }
    val snackbarHostState = remember { SnackbarHostState() }
    val placemarks = remember { randomPlacemarks() }
    val circles = remember { randomCircles() }

    val scope = rememberCoroutineScope()

    val mapActionsState = rememberMapActionsState()

    val showMessage: (String) -> Unit = {
        scope.launch {
            snackbarHostState.showSnackbar(it, withDismissAction = true)
        }
    }
    var clicksCount by remember { mutableStateOf(0) }
    val density = LocalDensity.current
    val contentSize =
        with(density) { DpSize(75.dp, 10.dp + 12.sp.toDp()) }
    val clicksImage = imageProvider(size = contentSize, clicksCount) {
        Box(
            modifier = Modifier
                .background(Color.LightGray, MaterialTheme.shapes.medium)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline,
                    MaterialTheme.shapes.medium
                )
                .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            Text("clicks: $clicksCount", fontSize = 12.sp)
        }
    }

    val userLocationState = rememberUserLocationState()
    Scaffold(
        bottomBar = {
            MapActions(
                state = mapActionsState,
                onStartPosition = {
                    cameraPositionState.position = userLocationState.cameraPosition?.target?.let {
                        CameraPosition(it, 12f, 0f, 0f)
                    } ?: startPosition
                },
                onUserLocation = {
                    userLocationState.cameraPosition?.let { cameraPositionState.position = it }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = modifier,
    ) { paddingValue ->
        Box {
            YandexMapWithButtons(
                cameraPositionState = cameraPositionState,
                locationState = userLocationState,
                locationConfig = UserLocationConfig(
                    isVisible = true,
                    arrow = UserLocationConfig.LocationIcon(
                        image = imageProvider(Res.drawable.cluster),
                    ),
                    pin = UserLocationConfig.LocationIcon(
                        image = imageProvider(Res.drawable.cluster),
                    ),
                    accuracy = UserLocationConfig.LocationAccuracy(
                        fillColor = Color.Yellow
                    )
                ),
                config = MapConfig(
                    isNightModeEnabled = isSystemInDarkTheme(),
                    logo = MapLogoConfig(
                        alignment = LogoAlignment(
                            horizontal = LogoHorizontalAlignment.LEFT,
                            vertical = LogoVerticalAlignment.TOP,
                        )
                    )
                ),
                navigationButtonModel = NavigationButtonModel(
                    zoomButtonModel = ZoomButtonModel(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 10.dp),
                    ),
                    positionAndNorthRowModifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            end = 10.dp,
                            bottom = paddingValue.calculateBottomPadding() + 10.dp
                        ),
                    positionButtonModel = PositionButtonModel(),
                    northButtonModel = NorthButtonModel(
                        colors = ButtonColor(
                            contentColor = customColors.northTint,
                            backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        )
                    ),
                    colors = ButtonColor(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    ),
                ),
                onNoPermissionGranted = onNoPermissionGranted,
                hasPermission = hasPermission,
                modifier = Modifier.fillMaxSize(),
            ) {
                if (mapActionsState.isPlacemarksEnabled) {
                    if (mapActionsState.isPlacemarksClsuteringEnabled) {
                        PlacemarksCluster(placemarks, mapActionsState.isComposableContentEnabled)
                    } else {
                        Placemarks(
                            placemarks,
                            mapActionsState = mapActionsState,
                            onShowMessage = showMessage,
                        )
                    }
                }
                if (mapActionsState.isCirclesEnabled) {
                    Circles(
                        circles = circles,
                        onShowMessage = showMessage,
                    )
                }
                if (mapActionsState.isPolygonsEnabled) {
                    Polygons(onShowMessage = showMessage)
                }
                if (mapActionsState.isPolylinesEnabled) {
                    Polylines()
                }
                if (mapActionsState.isComposableContentEnabled) {
                    Placemark(
                        icon = clicksImage,
                        state = rememberPlacemarkState(composablePlacemark),
                        onTap = {
                            clicksCount++
                            true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Polygons(
    onShowMessage: (String) -> Unit,
) {
    Polygon(
        state = rememberPolygonState(polygon),
        onTap = {
            onShowMessage("Tap on Polygon")
            true
        }
    )
}

@Composable
fun Polylines() {
    Polyline(
        state = rememberPolylineState(polyline),
        outlineColor = Color.DarkGray.copy(alpha = 0.4f),
        strokeColor = Color.Gray.copy(alpha = 0.4f)
    )
}

@YandexMapComposable
@Composable
fun Circles(
    circles: List<Pair<Circle, MapObjectUserData>>,
    onShowMessage: (String) -> Unit,
) {
    val typeToColor = mapOf(
        MapObjectType.YELLOW to Color.Yellow.copy(alpha = 0.6f),
        MapObjectType.RED to Color.Red.copy(alpha = 0.6f),
        MapObjectType.GREEN to Color.Green.copy(alpha = 0.6f)
    )
    circles.forEach {
        Circle(
            state = rememberCircleState(it.first),
            color = typeToColor[it.second.type]!!,
            strokeWidth = 2.5f,
            onTap = { point ->
                onShowMessage("Tap on ${it.second.name} point $point")
                true
            }
        )
    }
}

@YandexMapComposable
@Composable
fun Placemarks(
    placemarks: List<Pair<Point, MapObjectUserData>>,
    mapActionsState: MapActionsState,
    onShowMessage: (String) -> Unit,
) {
    val pinRedImage = imageProvider(Res.drawable.pin_red)
    val pinGreenImage = imageProvider(Res.drawable.pin_green)
    val pinYellowImage = imageProvider(Res.drawable.pin_yellow)
    val typeToImageMap = mapOf(
        MapObjectType.YELLOW to pinYellowImage,
        MapObjectType.RED to pinRedImage,
        MapObjectType.GREEN to pinGreenImage
    )
    placemarks.forEach {
        Placemark(
            state = rememberPlacemarkState(it.first),
            icon = typeToImageMap[it.second.type]!!,
            draggable = mapActionsState.isDragEnabled,
            onTap = if (mapActionsState.isDragEnabled) null else { point ->
                onShowMessage("Tap on ${it.second.name} point $point")
                true
            }
        )
    }
}

@YandexMapComposable
@Composable
fun PlacemarksCluster(
    placemarks: List<Pair<Point, MapObjectUserData>>,
    isComposableContentEnabled: Boolean,
) {
    val pinRedImage = imageProvider(Res.drawable.pin_red)
    val pinGreenImage = imageProvider(Res.drawable.pin_green)
    val pinYellowImage = imageProvider(Res.drawable.pin_yellow)
    val clusterIcon = imageProvider(Res.drawable.cluster)

    val redPlacemarks = placemarks.asSequence().filter { it.second.type == MapObjectType.RED }.map {
        ClusterItem(it.first, it.second)
    }.toImmutableList()

    val greenPlacemarks =
        placemarks.asSequence().filter { it.second.type == MapObjectType.GREEN }.map {
            ClusterItem(it.first, it.second)
        }.toImmutableList()
    val yellowPlacemarks =
        placemarks.asSequence().filter { it.second.type == MapObjectType.YELLOW }.map {
            ClusterItem(it.first, it.second)
        }.toImmutableList()
    if (isComposableContentEnabled) {
//        Clustering(
//            groups = persistentListOf(
//                ClusterGroup(
//                    placemarks = redPlacemarks,
//                    icon = pinRedImage,
//                ),
//                ClusterGroup(
//                    placemarks = greenPlacemarks,
//                    icon = pinGreenImage,
//                ),
//                ClusterGroup(
//                    placemarks = yellowPlacemarks,
//                    icon = pinYellowImage,
//                ),
//            ),
//            content = {
//                Box(
//                    modifier = Modifier
//                        .background(Color.LightGray, MaterialTheme.shapes.medium)
//                        .border(
//                            1.dp,
//                            MaterialTheme.colorScheme.outline,
//                            MaterialTheme.shapes.medium
//                        )
//                        .padding(vertical = 5.dp, horizontal = 10.dp)
//                ) {
//                    Text("${it.size}")
//                }
//            }
//        )
    } else {
        Clustering(
            groups = persistentListOf(
                ClusterGroup(
                    placemarks = redPlacemarks,
                    icon = pinRedImage,
                ),
                ClusterGroup(
                    placemarks = greenPlacemarks,
                    icon = pinGreenImage,
                ),
                ClusterGroup(
                    placemarks = yellowPlacemarks,
                    icon = pinYellowImage,
                ),
            ),
            icon = clusterIcon,
        )
    }
}