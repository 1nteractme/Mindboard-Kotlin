package com.interactme.mindboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun BoxScope.FloatingBar(
    onAddClick: () -> Unit,
    liquidState: LiquidState,
    isGlassEnabled: Boolean,
    radius: Dp = 25.dp,
    offsetY: Dp = (-100).dp,
    offsetX: Dp = (-8).dp,
) {
    Surface(
        color = Color.Transparent,
        /// TODO: FLOATING BAR ITEMS & ACTIONS
        modifier = Modifier
            .wrapContentWidth()
            .align(Alignment.BottomEnd)
            .offset(y = offsetY, x = offsetX)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(radius))
                .glass(liquidState, RoundedCornerShape(radius), isGlassEnabled, frost = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                CustomButton(onClick = onAddClick, icon = Icons.Rounded.FilterAlt)
//                CustomButton(onClick = onAddClick, icon = Icons.Rounded.Search)
                CustomButton(onClick = onAddClick, icon = Icons.Rounded.Add)
            }
        }
    }
}