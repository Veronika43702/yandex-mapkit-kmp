package ru.nikfirs.mapkit.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.nikfirs.mapkit.mapview.MapView

@Composable
internal expect fun NativeYandexMap(
    modifier: Modifier = Modifier,
    onRelease: (MapView) -> Unit = {},
    update: (MapView) -> Unit = {},
)