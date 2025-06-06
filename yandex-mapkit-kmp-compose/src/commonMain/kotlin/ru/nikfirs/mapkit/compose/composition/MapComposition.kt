package ru.nikfirs.mapkit.compose.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.CompositionLocalProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import ru.nikfirs.mapkit.compose.LocalCameraPositionState
import ru.nikfirs.mapkit.compose.LocalMap
import ru.nikfirs.mapkit.compose.LocalMapObjectCollection
import ru.nikfirs.mapkit.compose.YandexMapComposable
import ru.nikfirs.mapkit.mapview.MapView

internal inline fun CoroutineScope.launchMapComposition(
    parentComposition: CompositionContext,
    mapView: MapView,
    mapUpdaterState: MapUpdaterState,
    crossinline content: @[Composable YandexMapComposable] () -> Unit,
): Job {
    return launch(start = CoroutineStart.UNDISPATCHED) {
        val mapWindow = mapView.mapWindow
        val composition = Composition(
            applier = MapApplier(mapWindow),
            parent = parentComposition,
        )

        try {
            composition.setContent {
                MapUpdater(mapUpdaterState)

                CompositionLocalProvider(
                    LocalCameraPositionState provides mapUpdaterState.cameraPositionState,
                    LocalMapObjectCollection provides mapWindow.map.mapObjects,
                    LocalMap provides mapWindow.map,
                ) {
                    content()
                }
            }
            awaitCancellation()
        } finally {
            composition.dispose()
        }
    }
}