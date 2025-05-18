package ru.nikfirs.mapkit.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.nikfirs.mapkit.compose.ui.MapControlZoom


@Preview
@Composable
fun MapScreenPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        MapControlZoom(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterEnd),
            contentColor = Color.DarkGray,
            backgroundColor = Color.White.copy(alpha = 0.95f),
        )
    }
}