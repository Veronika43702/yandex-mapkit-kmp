package ru.nikfirs.mapkit.compose

import androidx.compose.runtime.Composable
import ru.nikfirs.mapkit.MapKit

@Composable
public actual fun rememberAndInitializeMapKit(): MapKit {
    return rememberMapKit()
}