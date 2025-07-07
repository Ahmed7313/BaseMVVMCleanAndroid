package com.aramex.mypos.Presentation.Components

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Brush.Companion.linearGradient


@SuppressLint("SuspiciousModifierThen")
fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
    )
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 100f, translateAnimation + 100f),
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush = brush, shape = shape))
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.shimmerBackground(shape: Shape = RectangleShape, active: Boolean): Modifier = composed {
    val brush: Brush
    if (active) {
        val transition = rememberInfiniteTransition()
        val translateAnimation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 200f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
                RepeatMode.Restart
            ),
        )

        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.4f),
        )

        brush = linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnimation, translateAnimation),
            end = Offset(translateAnimation + 100f, translateAnimation + 100f),
            tileMode = TileMode.Mirror,
        )
    } else {
        // Use a solid color when the image is not loading
        brush = SolidColor(Color.Gray)
    }
    return@composed this.then(background(brush = brush, shape = shape))
}





