package ru.nikfirs.mapkit.user_location

import ru.nikfirs.mapkit.layers.ObjectEvent
import com.yandex.mapkit.user_location.UserLocationIconChanged as NativeUserLocationIconChanged

public actual class UserLocationIconChanged internal constructor(
    private val nativeUserLocationIconChanged: NativeUserLocationIconChanged,
) : ObjectEvent(nativeUserLocationIconChanged) {

    override fun toNative(): NativeUserLocationIconChanged {
        return nativeUserLocationIconChanged
    }

    public actual val iconType: UserLocationIconType
        get() = nativeUserLocationIconChanged.iconType.toCommon()

}

public fun NativeUserLocationIconChanged.toCommon(): UserLocationIconChanged {
    return UserLocationIconChanged(this)
}