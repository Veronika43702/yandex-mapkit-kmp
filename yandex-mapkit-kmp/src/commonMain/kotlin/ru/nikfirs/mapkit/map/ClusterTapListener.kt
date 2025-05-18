package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Cluster

public expect abstract class ClusterTapListener() {

    public abstract fun onClusterTap(cluster: Cluster): Boolean

}

public inline fun ClusterTapListener(crossinline onClusterTap: (cluster: Cluster) -> Boolean): ClusterTapListener {
    return object : ClusterTapListener() {
        override fun onClusterTap(cluster: Cluster): Boolean {
            return onClusterTap.invoke(cluster)
        }
    }
}