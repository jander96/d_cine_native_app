package com.devj.dcine.core.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

@Composable
fun rememberGasmonicBrush(
    colors: List<Color> = listOf(
        Color(0xFF0F2027),
        Color(0xFF203A43),
    ),
    durationMillis: Int = 10000,
    maxOffset: Float = 1000f
): Brush {
    val transition = rememberInfiniteTransition(label = "gasmonic_transition")

    val offsetX = transition.animateFloat(
        initialValue = 0f,
        targetValue = maxOffset,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetX"
    )

    val offsetY = transition.animateFloat(
        initialValue = maxOffset,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis * 2, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    return Brush.linearGradient(
        colors = colors,
        start = Offset(offsetX.value, offsetY.value),
        end = Offset(offsetY.value, offsetX.value)
    )
}