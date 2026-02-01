package com.interactme.mindboard.ui.components.addIdea

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorCircle(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (isSelected) {
                    Modifier.border(width = 2.dp, color = Color(0xFF1F1F1F), shape = CircleShape)
                } else {
                    Modifier
                }
            )
            .clickable { onClick() }) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Selected",
                tint = Color(0xFF1F1F1F),
                modifier = Modifier.size(24.dp),
            )
        }
    }
}