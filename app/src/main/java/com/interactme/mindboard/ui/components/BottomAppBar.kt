package com.interactme.mindboard.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.interactme.mindboard.ui.theme.MindBoardTheme
import io.github.fletchmckee.liquid.LiquidState


// NOTE: UNUSED FOR NOW
@Composable
fun BoxScope.BottomAppBar(
    onAddClick: () -> Unit,
    isGlassEnabled: Boolean,
    dp: Dp = 25.dp,
    liquidState: LiquidState,
    onHeightMeasured: (Int) -> Unit = {},
) {
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .glass(liquidState, RoundedCornerShape(dp), isGlassEnabled)
            .onGloballyPositioned { onHeightMeasured(it.size.height) }
            .align(Alignment.BottomCenter),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MindBoardTheme.colors.white,
                contentColor = MindBoardTheme.colors.black,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Idea",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
