package com.interactme.mindboard.ui.components.addIdea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val cardColors = listOf(
    Color(0xFFEEEEEE),
    Color(0xFFCDB4DB),
    Color(0xFFFFAFCC),
    Color(0xFFFFC8DD),
    Color(0xFFBDE0FE),
    Color(0xFFA2D2FF),
    Color(0xFFA2FFAD),
)

@Composable
fun ColorSelectionWidget(selectedColor: Color, onColorSelected: (Color) -> Unit) {
    ColorSelectionGrid(
        colors = cardColors,
        selectedColor = selectedColor,
        onColorSelected = onColorSelected
    )
}

@Composable
fun ColorSelectionGrid(
    colors: List<Color>,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = modifier.fillMaxWidth()
    ) {
        items(colors) { color ->
            ColorCircle(
                color = color,
                isSelected = color == selectedColor,
                onClick = { onColorSelected(color) }
            )
        }
    }
}