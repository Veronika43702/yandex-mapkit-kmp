package ru.nikfirs.mapkit.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.nikfirs.mapkit.geometry.Cluster
import ru.nikfirs.mapkit.geometry.Point
import ru.nikfirs.mapkit.map.ClusterListener
import ru.nikfirs.mapkit.map.ClusterTapListener
import ru.nikfirs.mapkit.map.ClusterizedPlacemarkCollection
import ru.nikfirs.mapkit.map.IconStyle
import ru.nikfirs.mapkit.map.ImageProvider
import ru.nikfirs.mapkit.map.MapObjectTapListener
import ru.nikfirs.mapkit.map.PlacemarkMapObject
import ru.nikfirs.mapkit.map.TextStyle

internal class ClusterNode(
    groups: List<ClusterGroup>,
    config: ClusterizingConfig,
    mapObject: ClusterizedPlacemarkCollection,
    tapListener: ((Point) -> Boolean)?,
    var clusterItemTapListener: ((ClusterItem) -> Boolean)? = null,
) : MapObjectNode<ClusterizedPlacemarkCollection>(mapObject, tapListener) {

    private val nativeItemTapListener = MapObjectTapListener { mapObject, point ->
        clusterItemTapListener?.let {
            mapObject as PlacemarkMapObject
            it(ClusterItem(mapObject.geometry, mapObject.userData))
        } == true
    }

    init {
        if (groups.isNotEmpty()) {
            groups.forEach(::addGroup)
            clusterPlacemarks(config)
        }
    }

    var groups: List<ClusterGroup> = groups
        set(value) {
            updateItems(value)
            field = value
        }

    var config: ClusterizingConfig = config
        set(value) {
            clusterPlacemarks(value)
            field = value
        }

    private fun addGroup(group: ClusterGroup) {
        mapObject.addPlacemarks(group.placemarks.map { it.geometry }, group.icon, group.iconStyle)
            .forEach {
                it.setIcon(group.icon, group.iconStyle)
                if (group.text != null) {
                    it.setText(group.text, group.textStyle)
                }
                it.addTapListener(nativeItemTapListener)
            }
    }

    private fun updateItems(groups: List<ClusterGroup>) {
        mapObject.clear()
        groups.forEach(::addGroup)
        clusterPlacemarks(config)
    }

    internal fun clusterPlacemarks(config: ClusterizingConfig = this.config) {
        mapObject.clusterPlacemarks(config.clusterRadius, config.minZoom)
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun onAttached() {
        super.onAttached()
    }

    override fun onRemoved() {
        mapObject.clear()
        mapObject.parent.remove(mapObject)
    }
}

public data class ClusterItem(
    val geometry: Point,
    val data: Any? = null,
)

public data class ClusterGroup(
    val placemarks: ImmutableList<ClusterItem>,
    val icon: ImageProvider,
    val iconStyle: IconStyle = IconStyle(),
    val text: String? = null,
    val textStyle: TextStyle = TextStyle(),
)

public fun ClusterGroup(
    points: List<Point>,
    icon: ImageProvider,
    iconStyle: IconStyle = IconStyle(),
    text: String? = null,
    textStyle: TextStyle = TextStyle(),
): ClusterGroup {
    return ClusterGroup(
        placemarks = points.map { ClusterItem(it) }.toPersistentList(),
        icon = icon,
        iconStyle = iconStyle,
        text = text,
        textStyle = textStyle,
    )
}

public data class ClusterizingConfig(
    val clusterRadius: Double = DefaultClusterRadius,
    val minZoom: Int = DefaultMinZoom,
)

@YandexMapComposable
@Composable
public fun Clustering(
    group: ClusterGroup,
    icon: ImageProvider,
    iconStyle: IconStyle = IconStyle(),
    config: ClusterizingConfig = ClusterizingConfig(),
    onItemTap: ((ClusterItem) -> Boolean)? = null,
    onClusterTap: ((Cluster) -> Boolean)? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
) {
    Clustering(
        groups = persistentListOf(group),
        icon = icon,
        iconStyle = iconStyle,
        config = config,
        onItemTap = onItemTap,
        onClusterTap = onClusterTap,
        visible = visible,
        zIndex = zIndex
    )
}

@YandexMapComposable
@Composable
public fun Clustering(
    groups: ImmutableList<ClusterGroup>,
    icon: ImageProvider,
    iconStyle: IconStyle = IconStyle(),
    config: ClusterizingConfig = ClusterizingConfig(),
    onItemTap: ((ClusterItem) -> Boolean)? = null,
    onClusterTap: ((Cluster) -> Boolean)? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
) {
    val collection = LocalMapObjectCollection.current
    MapObjectNode(
        visible = visible,
        onTap = null,
        zIndex = zIndex,
        factory = {
            val nativeClusterTapListener = ClusterTapListener {
                onClusterTap?.invoke(it) ?: false
            }
            val listener = ClusterListener {
                it.appearance.setIcon(icon, iconStyle)
                it.addClusterTapListener(nativeClusterTapListener)
            }
            ClusterNode(
                groups = groups,
                config = config,
                mapObject = collection.addClusterizedPlacemarkCollection(listener),
                tapListener = null,
                clusterItemTapListener = onItemTap,
            )
        },
        update = {
            update(onItemTap) { this.clusterItemTapListener = onItemTap }
            update(groups) { this.groups = groups }
            update(config) { this.config = config }
            update(icon) { this.clusterPlacemarks() }
            update(iconStyle) { this.clusterPlacemarks() }
        }
    )
}

@YandexMapsComposeExperimentalApi
@YandexMapComposable
@Composable
public fun Clustering(
    group: ClusterGroup,
    contentSize: DpSize,
    iconStyle: IconStyle = IconStyle(),
    config: ClusterizingConfig = ClusterizingConfig(),
    onItemTap: ((ClusterItem) -> Boolean)? = null,
    onClusterTap: ((Cluster) -> Boolean)? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    content: @Composable (Cluster) -> Unit,
) {
    Clustering(
        groups = persistentListOf(group),
        icon = clusterImageProvider(contentSize, content),
        iconStyle = iconStyle,
        config = config,
        onItemTap = onItemTap,
        onClusterTap = onClusterTap,
        visible = visible,
        zIndex = zIndex,
    )
}

@YandexMapsComposeExperimentalApi
@YandexMapComposable
@Composable
public fun Clustering(
    groups: ImmutableList<ClusterGroup>,
    contentSize: DpSize,
    iconStyle: IconStyle = IconStyle(),
    config: ClusterizingConfig = ClusterizingConfig(),
    onItemTap: ((ClusterItem) -> Boolean)? = null,
    onClusterTap: ((Cluster) -> Boolean)? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    content: @Composable (Cluster) -> Unit,
) {
    Clustering(
        groups = groups,
        icon = clusterImageProvider(size = contentSize, content = content),
        iconStyle = iconStyle,
        config = config,
        onItemTap = onItemTap,
        onClusterTap = onClusterTap,
        visible = visible,
        zIndex = zIndex,
    )
}

@YandexMapsComposeExperimentalApi
@YandexMapComposable
@Composable
public fun Clustering(
    group: ClusterGroup,
    icon: ClusterImageProvider,
    iconStyle: IconStyle = IconStyle(),
    config: ClusterizingConfig = ClusterizingConfig(),
    onItemTap: ((ClusterItem) -> Boolean)? = null,
    onClusterTap: ((Cluster) -> Boolean)? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
) {
    Clustering(
        groups = persistentListOf(group),
        icon = icon,
        iconStyle = iconStyle,
        config = config,
        onItemTap = onItemTap,
        onClusterTap = onClusterTap,
        visible = visible,
        zIndex = zIndex,
    )
}

@YandexMapsComposeExperimentalApi
@YandexMapComposable
@Composable
public fun Clustering(
    groups: ImmutableList<ClusterGroup>,
    icon: ClusterImageProvider,
    iconStyle: IconStyle = IconStyle(),
    config: ClusterizingConfig = ClusterizingConfig(),
    onItemTap: ((ClusterItem) -> Boolean)? = null,
    onClusterTap: ((Cluster) -> Boolean)? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
) {
    val collection = LocalMapObjectCollection.current
    MapObjectNode(
        visible = visible,
        onTap = null,
        zIndex = zIndex,
        factory = {
            val nativeClusterTapListener = ClusterTapListener {
                onClusterTap?.invoke(it) ?: false
            }
            val listener = ClusterListener {
                it.appearance.setIcon(icon.toImageProvider(it), iconStyle)
                it.addClusterTapListener(nativeClusterTapListener)
            }
            ClusterNode(
                groups = groups,
                config = config,
                mapObject = collection.addClusterizedPlacemarkCollection(listener),
                tapListener = null,
                clusterItemTapListener = onItemTap,
            )
        },
        update = {
            update(onItemTap) { this.clusterItemTapListener = onItemTap }
            update(groups) { this.groups = groups }
            update(config) { this.config = config }
            update(iconStyle) { this.clusterPlacemarks() }
            update(icon) { this.clusterPlacemarks() }
        }
    )
}

private const val DefaultClusterRadius = 60.0
private const val DefaultMinZoom = 15