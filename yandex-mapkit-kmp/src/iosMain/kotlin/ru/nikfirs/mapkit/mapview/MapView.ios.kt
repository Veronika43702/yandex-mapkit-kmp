package ru.nikfirs.mapkit.mapview

import ru.nikfirs.mapkit.map.MapWindow
import ru.nikfirs.mapkit.map.toCommon
import YandexMapKit.YMKMapView as NativeMapView

public actual class MapView internal constructor(private val nativeMapView: NativeMapView) {

    public fun toNative(): NativeMapView {
        return nativeMapView
    }

    public actual fun onStart() {

    }

    public actual fun onStop() {
    }

    public actual val mapWindow: MapWindow = nativeMapView.mapWindow!!.toCommon()

    public actual fun setNonInteractive(value: Boolean) {
        nativeMapView.setNoninteractive(value)
    }

}

public fun NativeMapView.toCommon(): MapView {
    return ru.nikfirs.mapkit.mapview.MapView(this)
}