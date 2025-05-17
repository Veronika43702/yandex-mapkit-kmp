package ru.nikfirs.mapkit.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import ru.nikfirs.mapkit.mapview.MapView
import ru.nikfirs.mapkit.mapview.toCommon
import YandexMapKit.YMKMapView as NativeMapView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal actual fun NativeYandexMap(
    modifier: Modifier,
    onRelease: (MapView) -> Unit,
    update: (MapView) -> Unit,
) {
    val nativeMapView = remember { NativeMapView() }
    val mapView = remember(nativeMapView) { nativeMapView.toCommon() }
    mapView.bindToLifecycleOwner()
    UIKitView(
        factory = { nativeMapView },
        onRelease = {
            onRelease(mapView)
            mapView.onStop()
        },
        update = {
            update(mapView)
        },
        modifier = modifier,
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
        )
    )
}