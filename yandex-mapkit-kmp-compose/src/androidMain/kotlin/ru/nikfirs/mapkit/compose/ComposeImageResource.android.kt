package ru.nikfirs.mapkit.compose

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import ru.nikfirs.mapkit.map.ImageProvider
import ru.nikfirs.mapkit.map.toImageProvider

public actual fun ImageBitmap.toImageProvider(): ImageProvider {
    return asAndroidBitmap().toImageProvider()
}