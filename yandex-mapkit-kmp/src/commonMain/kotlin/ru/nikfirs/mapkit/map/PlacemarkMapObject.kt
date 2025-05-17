package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Point

public expect class PlacemarkMapObject : MapObject {

    public var geometry: Point

    public var direction: Float

    public var opacity: Float

    public fun setText(
        text: String,
        style: TextStyle = TextStyle(),
    )

    public fun setTextStyle(style: TextStyle)

    public fun setIcon(
        image: ImageProvider,
        style: IconStyle = IconStyle(),
        onFinished: Callback? = null,
    )

}