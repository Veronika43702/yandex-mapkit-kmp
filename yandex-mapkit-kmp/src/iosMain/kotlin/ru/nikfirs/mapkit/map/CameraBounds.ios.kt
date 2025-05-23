package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.BoundingBox
import ru.nikfirs.mapkit.geometry.toCommon
import ru.nikfirs.mapkit.geometry.toNative
import YandexMapKit.YMKCameraBounds as NativeCameraBounds

public actual class CameraBounds internal constructor(private val nativeCameraBounds: NativeCameraBounds) {

    public fun toNative(): NativeCameraBounds {
        return nativeCameraBounds
    }

    /**
     * Minimum available zoom level considering zoom level hint provided via #setMinZoomPreference.
     */
    public actual val minZoom: Float
        get() = nativeCameraBounds.getMinZoom()

    /**
     * Maximum available zoom level considering zoom level hint provided via #setMinZoomPreference.
     */
    public actual val maxZoom: Float
        get() = nativeCameraBounds.getMaxZoom()

    /**
     * Latitudes should be in range [-89.3, 89.3].
     *
     * Longitudes should be in range [-180, 180).
     */
    public actual var latLngBounds: BoundingBox?
        get() = nativeCameraBounds.latLngBounds?.toCommon()
        set(value) {
            nativeCameraBounds.latLngBounds = value?.toNative()
        }

    /**
     * Reset minimum and maximum available zoom level hints.
     */
    public actual fun resetMinMaxZoomPreference() {
        nativeCameraBounds.resetMinMaxZoomPreference()
    }

    /**
     * Set minimum available zoom level hint.
     */
    public actual fun setMinZoomPreference(zoom: Float) {
        nativeCameraBounds.setMinZoomPreferenceWithZoom(zoom)
    }

    /**
     * Set maximum available zoom level hint.
     */
    public actual fun setMaxZoomPreference(zoom: Float) {
        nativeCameraBounds.setMaxZoomPreferenceWithZoom(zoom)
    }

}

public fun NativeCameraBounds.toCommon(): CameraBounds {
    return ru.nikfirs.mapkit.map.CameraBounds(this)
}