package com.interactme.mindboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.interactme.mindboard.ui.theme.MindBoardTheme
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

@Composable
fun Modifier.glass(
    liquidState: LiquidState,
    cornerRadius: RoundedCornerShape,
    enabled: Boolean,

    frost: Dp = 0.dp,
    tint: Color = Color.Unspecified,
    refraction: Float = 0.25f,
    curve: Float = 0.5f,
    edge: Float = 0f,
    saturation: Float = 1f,
    dispersion: Float = 0.25f,
    fallbackColor: Color = MindBoardTheme.colors.appSurface,
): Modifier {
    if (!enabled) return this.background(fallbackColor)

    val resolvedTint =
        if (tint == Color.Unspecified) MindBoardTheme.colors.white.copy(alpha = 0.05f) else tint

    return this.liquid(liquidState) {
        this.frost = frost
        this.refraction = refraction
        this.curve = curve
        this.edge = edge
        this.tint = resolvedTint
        this.saturation = saturation
        this.dispersion = dispersion
        this.shape = cornerRadius
    }
}
