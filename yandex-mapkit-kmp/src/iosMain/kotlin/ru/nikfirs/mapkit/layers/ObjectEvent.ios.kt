package ru.nikfirs.mapkit.layers

import ru.nikfirs.mapkit.user_location.toCommon
import YandexMapKit.YMKObjectEvent as NativeObjectEvent
import YandexMapKit.YMKUserLocationAnchorChanged as NativeUserLocationAnchorChanged
import YandexMapKit.YMKUserLocationIconChanged as NativeUserLocationIconChanged

public actual open class ObjectEvent internal constructor(private val nativeObjectEvent: NativeObjectEvent) {

    public open fun toNative(): NativeObjectEvent {
        return nativeObjectEvent
    }

}

public fun NativeObjectEvent.toCommon(): ObjectEvent {
    return when (this) {
        is NativeUserLocationAnchorChanged -> toCommon()
        is NativeUserLocationIconChanged -> toCommon()
        else -> ru.nikfirs.mapkit.layers.ObjectEvent(this)
    }
}

