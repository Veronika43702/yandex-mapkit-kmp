package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.layers.ObjectEvent
import YandexMapKit.YMKUserLocationAnchorChanged as NativeUserLocationAnchorChanged

public actual class UserLocationAnchorChanged internal constructor(
    private val nativeUserLocationAnchorChanged: NativeUserLocationAnchorChanged,
) : ObjectEvent(nativeUserLocationAnchorChanged) {

    override fun toNative(): NativeUserLocationAnchorChanged {
        return nativeUserLocationAnchorChanged
    }

    public actual val anchorType: UserLocationAnchorType
        get() = nativeUserLocationAnchorChanged.anchorType.toCommon()

}

public fun NativeUserLocationAnchorChanged.toCommon(): UserLocationAnchorChanged {
    return ru.nikfirs.mapkit.user_location.UserLocationAnchorChanged(this)
}