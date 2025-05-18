package ru.nikfirs.mapkit.compose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import org.jetbrains.compose.resources.painterResource
import ru.nikfirs.mapkit.compose.models.ZoomButtonAction
import ru.nikfirs.mapkit.yandex_mapkit_kmp_compose.generated.resources.Res
import ru.nikfirs.mapkit.yandex_mapkit_kmp_compose.generated.resources.ic_navigation_24px
import ru.nikfirs.mapkit.yandex_mapkit_kmp_compose.generated.resources.ic_north_pointer
import ru.nikfirs.mapkit.yandex_mapkit_kmp_compose.generated.resources.ic_north_pointer_red_part

@Composable
public fun CombinedFilledTonalIconButton(
    onPressStart: (() -> Unit)? = null,
    onPressEnd: (() -> Unit)? = null,
    onTap: (() -> Unit)? = null,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(0.9f),
    shape: Shape = IconButtonDefaults.filledShape,
    contentColor: Color = Color.DarkGray,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    CombinedSurface(
        onPressStart = onPressStart,
        onPressEnd = onPressEnd,
        onTap = onTap,
        modifier = modifier.semantics { role = Role.Button },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        shape = shape,
        shadowElevation = 8.dp,
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@Composable
@NonRestartableComposable
public fun CombinedSurface(
    onPressStart: (() -> Unit)? = null,
    onPressEnd: (() -> Unit)? = null,
    onTap: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(0.9f),
    contentColor: Color = Color.DarkGray,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .surface(
                shape = shape,
                backgroundColor = backgroundColor,
                border = border,
                shadowElevation = shadowElevation
            ).tapClickable(
                interactionSource = interactionSource,
                onPressStart = onPressStart,
                onPressEnd = onPressEnd,
                onTap = onTap,
                indication = ripple(),
            ),
        propagateMinConstraints = true,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}


private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Dp,
) = this.shadow(shadowElevation, shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)


public fun Modifier.tapClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onPressStart: (() -> Unit)? = null,
    onPressEnd: (() -> Unit)? = null,
    onTap: (() -> Unit)? = null,
): Modifier {
    return this
        .indication(interactionSource, indication)
        .hoverable(enabled = enabled, interactionSource = interactionSource)
        .pointerInput(enabled) {
            coroutineScope {
                if (onPressStart != null && onPressEnd != null) {
                    detectTapGestures(
                        onTap = { onTap?.invoke() },
                        onPress = { _ ->
                            try {
                                onPressStart.invoke()
                                awaitRelease()
                                onPressEnd.invoke()
                            } catch (e: CancellationException) {
                                onPressEnd.invoke()
                            }
                        }
                    )
                } else {
                    detectTapGestures(
                        onTap = { onTap?.invoke() }
                    )
                }
            }
        }
        .focusable(enabled = enabled, interactionSource = interactionSource)
}

@Composable
public fun ButtonZoomIn(
    onPressStart: (() -> Unit)? = null,
    onPressEnd: (() -> Unit)? = null,
    onTap: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    size: Dp = 48.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit = {
        Text("+", fontSize = 40.sp, fontWeight = FontWeight.Light)
    },
) {
    CombinedFilledTonalIconButton(
        modifier = modifier,
        onPressStart = onPressStart,
        onPressEnd = onPressEnd,
        onTap = onTap,
        shape = shape,
        size = size,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        content()
    }
}

@Composable
public fun ButtonZoomOut(
    onPressStart: (() -> Unit)? = null,
    onPressEnd: (() -> Unit)? = null,
    onTap: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    size: Dp = 48.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable () -> Unit = {
        Text("–", fontSize = 40.sp, fontWeight = FontWeight.Light)
    },
) {
    CombinedFilledTonalIconButton(
        modifier = modifier,
        onPressStart = onPressStart,
        onPressEnd = onPressEnd,
        onTap = onTap,
        shape = shape,
        size = size,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        content()
    }
}

@Composable
public fun MapControlZoom(
    modifier: Modifier = Modifier,
    zoomInButtonAction: ZoomButtonAction = ZoomButtonAction(),
    zoomOutButtonAction: ZoomButtonAction = ZoomButtonAction(),
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    size: Dp = 48.dp,
    shape: Shape = RoundedCornerShape(16.dp),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        ButtonZoomIn(
            onPressStart = zoomInButtonAction.onPressStart,
            onPressEnd = zoomInButtonAction.onPressEnd,
            onTap = zoomInButtonAction.onTap,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            size = size,
            shape = shape,
        )

        ButtonZoomOut(
            onPressStart = zoomOutButtonAction.onPressStart,
            onPressEnd = zoomOutButtonAction.onPressEnd,
            onTap = zoomOutButtonAction.onTap,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            size = size,
            shape = shape,
        )
    }
}

@Composable
public fun ButtonCurrentPosition(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    size: Dp = 56.dp,
    shape: Shape = CircleShape,
    content: @Composable () -> Unit = {
        Icon(
            modifier = Modifier
                .size(36.dp)
                .padding(start = 4.dp, bottom = 4.dp)
                .graphicsLayer { rotationZ = 45f },
            painter = painterResource(Res.drawable.ic_navigation_24px),
            tint = contentColor,
            contentDescription = null,
        )
    },
) {
    CombinedFilledTonalIconButton(
        modifier = modifier,
        onTap = onClick,
        shape = shape,
        size = size,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        content()
    }
}

@Composable
public fun ButtonOrientNorth(
    modifier: Modifier = Modifier,
    rotation: Float = 0f,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    size: Dp = 48.dp,
    shape: Shape = CircleShape,
    content: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .size(36.dp)
                .graphicsLayer { rotationZ = rotation },
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(Res.drawable.ic_north_pointer_red_part),
                tint = Color.Red,
                contentDescription = null,
            )
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(Res.drawable.ic_north_pointer),
                tint = contentColor,
                contentDescription = null,
            )
        }
    },
) {
    CombinedFilledTonalIconButton(
        modifier = modifier,
        onTap = onClick,
        shape = shape,
        size = size,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        content()
    }
}