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
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun BoxScope.IdeaDialogBar(
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    liquidState: LiquidState,
    offsetY: Dp = (-100).dp,
    offsetX: Dp = (12).dp,
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .wrapContentWidth()
            .align(Alignment.BottomStart)
            .offset(y = offsetY, x = offsetX)
    ) {
        Box(
            modifier = Modifier.glass(liquidState, RoundedCornerShape(25.dp), frost = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomButton(onEdit, Icons.Rounded.Edit)
                CustomButton(onDelete, Icons.Rounded.Delete)
            }
        }
    }
}