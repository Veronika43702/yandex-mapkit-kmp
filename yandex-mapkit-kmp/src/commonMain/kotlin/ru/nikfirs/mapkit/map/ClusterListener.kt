package ru.nikfirs.mapkit.map

import ru.nikfirs.mapkit.geometry.Cluster

public expect abstract class ClusterListener() {

    public abstract fun onClusterAdded(cluster: Cluster)

}

public inline fun ClusterListener(crossinline onClusterAdded: (cluster: Cluster) -> Unit): ClusterListener {
    return object : ClusterListener() {
        override fun onClusterAdded(cluster: Cluster) {
            onClusterAdded.invoke(cluster)
        }
    }
}