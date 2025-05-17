package ru.nikfirs.mapkit.map

import platform.UIKit.UIImage
import ru.nikfirs.mapkit.map.ImageProvider

public actual interface ImageProvider {

    public fun toNative(): UIImage

    public companion object

}

public fun ImageProvider.Companion.fromUIImage(uiImage: UIImage): ImageProvider {
    return UIImageImageProvider(uiImage)
}