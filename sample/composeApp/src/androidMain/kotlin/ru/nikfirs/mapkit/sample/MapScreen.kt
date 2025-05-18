package ru.nikfirs.mapkit.sample

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.nikfirs.mapkit.compose.ui.ButtonCurrentPosition
import ru.nikfirs.mapkit.compose.ui.ButtonOrientNorth
import ru.nikfirs.mapkit.compose.ui.MapControlZoom
import ru.nikfirs.mapkit.sample.ui.AppTheme
import ru.nikfirs.mapkit.sample.ui.LocalCustomColors

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MapScreenPreview() {
    AppTheme {
        val customColors = LocalCustomColors.current

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterEnd),
            ) {
                MapControlZoom( )

                ButtonCurrentPosition(
                    onClick = {},
                )

                ButtonOrientNorth(
                    onClick = {},
                    contentColor = customColors.northTint,
                )
            }
        }
    }
}