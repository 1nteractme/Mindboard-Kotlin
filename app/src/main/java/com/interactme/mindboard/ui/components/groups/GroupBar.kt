package com.interactme.mindboard.ui.components.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.interactme.mindboard.ui.components.CustomButton
import com.interactme.mindboard.ui.components.glass
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun GroupBar(liquidState: LiquidState, isGlassEnabled: Boolean, radius: Dp = 25.dp) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(Color.Transparent)
            .height(60.dp)
            .clip(RoundedCornerShape(radius))
            .glass(liquidState, RoundedCornerShape(radius), isGlassEnabled),
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            Arrangement.spacedBy(12.dp),
            Alignment.CenterVertically
        ) {
            CustomButton({ print("click") }, Icons.Rounded.Add)
            Spacer(Modifier
                .width(2.dp)
                .fillMaxHeight()
                .background(Color(0xFF444444))
                .clip(RoundedCornerShape(radius))
            )
        }
    }
}