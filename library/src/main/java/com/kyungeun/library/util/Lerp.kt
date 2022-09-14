package com.kyungeun.library.util
import androidx.annotation.FloatRange

public fun lerp(
    startValue: Float,
    endValue: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    return startValue + fraction * (endValue - startValue)
}
