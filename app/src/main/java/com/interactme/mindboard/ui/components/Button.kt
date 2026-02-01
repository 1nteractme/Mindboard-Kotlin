package com.interactme.mindboard.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(onClick: () -> Unit, icon: ImageVector) {
    FloatingActionButton(
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp
        ), onClick = onClick, containerColor = Color.White, contentColor = Color.Black
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Add Idea",
            modifier = Modifier.size(24.dp)
        )
    }
}