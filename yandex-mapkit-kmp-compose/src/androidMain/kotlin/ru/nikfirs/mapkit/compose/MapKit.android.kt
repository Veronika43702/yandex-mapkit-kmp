package ru.nikfirs.mapkit.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.yandex.mapkit.MapKitFactory
import ru.nikfirs.mapkit.MapKit

@Composable
public actual fun rememberAndInitializeMapKit(): MapKit {
    val context = LocalContext.current
    return remember {
        MapKitFactory.initialize(context)
        MapKit.getInstance()
    }
}