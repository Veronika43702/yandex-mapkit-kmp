package ru.nikfirs.mapkit.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.nikfirs.mapkit.MapKit
import ru.nikfirs.mapkit.sample.ui.AppTheme

fun initMapKit() {
    MapKit.setApiKey(BuildKonfig.MAPKIT_API_KEY)
}

enum class NavItem {
    SELECTION,
    OLD_API,
    NEW_API_OBJECTS;

    companion object {
        val Saver: Saver<NavItem, Int> = Saver(
            save = { it.ordinal },
            restore = { NavItem.entries[it] }
        )
    }
}

@Composable
fun App(
    hasPermission: State<Boolean> = mutableStateOf(true),
    onNoPermissionGranted: () -> Unit = {},
) {
    AppTheme {
        var navItem by rememberSaveable(stateSaver = NavItem.Saver) { mutableStateOf(NavItem.SELECTION) }
        NavHost(
            navItem,
            onNavigate = { navItem = it },
            modifier = Modifier.fillMaxSize(),
            hasPermission = hasPermission,
            onNoPermissionGranted = onNoPermissionGranted,
        )
    }
}

@Composable
fun NavHost(
    navItem: NavItem,
    onNavigate: (NavItem) -> Unit,
    modifier: Modifier = Modifier,
    hasPermission: State<Boolean>,
    onNoPermissionGranted: () -> Unit = {},
) {
    when (navItem) {
        NavItem.SELECTION -> Selection(onNavigate, modifier)
        NavItem.OLD_API -> {}//MapScreen(modifier)
        NavItem.NEW_API_OBJECTS -> NewMapScreen(
            modifier,
            hasPermission,
            onNoPermissionGranted
        )
    }
}

@Composable
fun Selection(
    onNavigate: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedButton(
            onClick = {
                onNavigate(NavItem.OLD_API)
            }
        ) {
            Text("!!!!Old api objects")
        }
        OutlinedButton(
            onClick = {
                onNavigate(NavItem.NEW_API_OBJECTS)
            }
        ) {
            Text("New api objects")
        }
    }
}