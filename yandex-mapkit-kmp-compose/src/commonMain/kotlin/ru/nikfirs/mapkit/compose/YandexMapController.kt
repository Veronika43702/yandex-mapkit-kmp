package ru.nikfirs.mapkit.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import ru.nikfirs.mapkit.compose.utils.LifecycleEffect
import ru.nikfirs.mapkit.map.MapWindow
import ru.nikfirs.mapkit.mapview.MapView

/**
 * Create and remember YandexMapController.
 * Don't pass to multiple YandexMap. Don't save it anywhere, contains native references.
 */
@Composable
public fun rememberYandexMapController(): YandexMapController {
    return remember { YandexMapController() }
}

/**
 * Contains common implementation of MapWindow, that uses for controlling YandexMap
 */
public class YandexMapController internal constructor() {

    internal val mapWindowOwner = MapWindowOwner()
    public val mapWindow: MapWindow? get() = mapWindowOwner.mapWindow

}

/**
 * Binds calling [MapView.onStart] and [MapView.onStop] to [LifecycleOwner] and composition
 */
@Composable
internal fun MapView.bindToLifecycleOwner(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    LifecycleEffect(
        lifecycleOwner = lifecycleOwner,
        onStart = ::onStart,
        onStop = ::onStop,
        onDispose = ::onStop,
    )
}