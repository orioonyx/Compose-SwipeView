package com.kyungeun.library.util

import androidx.annotation.FloatRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.pow

public fun Modifier.verticalGradientScrim(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0) startYPercentage: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) endYPercentage: Float = 1f,
    decay: Float = 1.0f,
    numStops: Int = 16
): Modifier = composed {
    val colors = remember(color, numStops) {
        if (decay != 1f) {
            // If we have a non-linear decay, we need to create the color gradient steps
            // manually
            val baseAlpha = color.alpha
            List(numStops) { i ->
                val x = i * 1f / (numStops - 1)
                val opacity = x.pow(decay)
                color.copy(alpha = baseAlpha * opacity)
            }
        } else {
            // If we have a linear decay, we just create a simple list of start + end colors
            listOf(color.copy(alpha = 0f), color)
        }
    }

    var height by remember { mutableStateOf(0f) }
    val brush = remember(color, numStops, startYPercentage, endYPercentage, height) {
        Brush.verticalGradient(
            colors = colors,
            startY = height * startYPercentage,
            endY = height * endYPercentage
        )
    }

    drawBehind {
        height = size.height
        drawRect(brush = brush)
    }
}
