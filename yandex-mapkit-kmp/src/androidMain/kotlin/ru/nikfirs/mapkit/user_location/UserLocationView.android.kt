package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.map.CircleMapObject
import ru.nikfirs.mapkit.map.PlacemarkMapObject
import ru.nikfirs.mapkit.map.toCommon
import com.yandex.mapkit.user_location.UserLocationView as NativeUserLocationView

public actual class UserLocationView internal constructor(
    private val nativeUserLocationView: NativeUserLocationView,
) {
    public fun toNative(): NativeUserLocationView {
        return nativeUserLocationView
    }

    public actual val arrow: PlacemarkMapObject
        get() = nativeUserLocationView.arrow.toCommon()
    public actual val pin: PlacemarkMapObject
        get() = nativeUserLocationView.pin.toCommon()
    public actual val accuracyCircle: CircleMapObject
        get() = nativeUserLocationView.accuracyCircle.toCommon()
}

public fun NativeUserLocationView.toCommon(): UserLocationView {
    return UserLocationView(this)
}