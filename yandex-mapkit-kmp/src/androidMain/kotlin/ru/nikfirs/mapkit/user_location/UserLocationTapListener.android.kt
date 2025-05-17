package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.geometry.Point
import ru.nikfirs.mapkit.geometry.toCommon
import com.yandex.mapkit.user_location.UserLocationTapListener as NativeUserLocationTapListener

public actual abstract class UserLocationTapListener actual constructor() {
    private val nativeListener = NativeUserLocationTapListener {
        onUserLocationObjectTap(it.toCommon())
    }

    public fun toNative(): NativeUserLocationTapListener {
        return nativeListener
    }

    public actual abstract fun onUserLocationObjectTap(point: Point)
}