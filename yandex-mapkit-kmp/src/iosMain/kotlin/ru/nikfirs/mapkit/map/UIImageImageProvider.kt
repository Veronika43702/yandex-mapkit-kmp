package ru.nikfirs.mapkit.map

import platform.UIKit.UIImage
import ru.nikfirs.mapkit.map.ImageProvider

public class UIImageImageProvider internal constructor(private val uiImage: UIImage) :
    ImageProvider {
    override fun toNative(): UIImage {
        return uiImage
    }
}