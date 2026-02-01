package com.interactme.mindboard.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

fun Modifier.glass(
    liquidState: LiquidState,
    cornerRadius: RoundedCornerShape,

    frost: Dp = 0.dp,
    tint: Color = Color.White.copy(alpha = 0.05f),
    refraction: Float = 0.25f,
    curve: Float = 0.5f,
    edge: Float = 0f,
    saturation: Float = 1f,
    dispersion: Float = 0.25f
): Modifier {
    return this.liquid(liquidState) {
        this.frost = frost
        this.refraction = refraction
        this.curve = curve
        this.edge = edge
        this.tint = tint
        this.saturation = saturation
        this.dispersion = dispersion
        this.shape = cornerRadius
    }
}