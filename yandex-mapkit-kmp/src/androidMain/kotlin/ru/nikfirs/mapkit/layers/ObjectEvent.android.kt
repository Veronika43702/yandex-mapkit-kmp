package ru.nikfirs.mapkit.layers

import ru.nikfirs.mapkit.user_location.toCommon
import com.yandex.mapkit.layers.ObjectEvent as NativeObjectEvent
import com.yandex.mapkit.user_location.UserLocationAnchorChanged as NativeUserLocationAnchorChanged
import com.yandex.mapkit.user_location.UserLocationIconChanged as NativeUserLocationIconChanged

public actual open class ObjectEvent(private val nativeObjectEvent: NativeObjectEvent) {

    public open fun toNative(): NativeObjectEvent {
        return nativeObjectEvent
    }

}

public fun NativeObjectEvent.toCommon(): ObjectEvent {
    return when (this) {
        is NativeUserLocationAnchorChanged -> toCommon()
        is NativeUserLocationIconChanged -> toCommon()
        else -> ObjectEvent(this)
    }
}

