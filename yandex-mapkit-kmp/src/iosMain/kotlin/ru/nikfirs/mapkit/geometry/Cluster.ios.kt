package ru.nikfirs.mapkit.geometry

import ru.nikfirs.mapkit.map.ClusterTapListener
import ru.nikfirs.mapkit.map.PlacemarkMapObject
import ru.nikfirs.mapkit.map.toCommon
import YandexMapKit.YMKCluster as NativeCluster
import YandexMapKit.YMKPlacemarkMapObject as NativePlacemarkMapObject

public actual class Cluster internal constructor(private val nativeCluster: NativeCluster) {

    public fun toNative(): NativeCluster {
        return nativeCluster
    }

    public actual val placemarks: List<PlacemarkMapObject>
        @Suppress("UNCHECKED_CAST")
        get() = (nativeCluster.placemarks as List<NativePlacemarkMapObject>).map { it.toCommon() }

    public actual val size: Int
        get() = nativeCluster.size.toInt()
    public actual val appearance: PlacemarkMapObject
        get() = nativeCluster.appearance.toCommon()

    public actual fun addClusterTapListener(listener: ClusterTapListener) {
        nativeCluster.addClusterTapListenerWithClusterTapListener(listener.toNative())
    }

    public actual fun removeClusterTapListener(listener: ClusterTapListener) {
        nativeCluster.removeClusterTapListenerWithClusterTapListener(listener.toNative())
    }

}

public fun NativeCluster.toCommon(): Cluster {
    return ru.nikfirs.mapkit.geometry.Cluster(this)
}