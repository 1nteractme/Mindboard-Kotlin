package com.interactme.mindboard.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    radius: Dp = 25.dp,
    liquidState: LiquidState,
    onHeightMeasured: (Int) -> Unit = {},
) {
    TopAppBar(
        title = { CustomText(title) },
        modifier = Modifier
            .fillMaxWidth()
            .glass(liquidState, RoundedCornerShape(radius))
            .onGloballyPositioned{ onHeightMeasured(it.size.height)},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}