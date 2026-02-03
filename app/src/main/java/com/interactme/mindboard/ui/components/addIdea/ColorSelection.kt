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
import com.interactme.mindboard.ui.theme.MindBoardTheme

@Composable
private fun ideaCardColors(): List<Color> = MindBoardTheme.colors.ideaPalette

@Composable
fun ColorSelectionWidget(selectedColor: Color, onColorSelected: (Color) -> Unit) {
    ColorSelectionGrid(
        colors = ideaCardColors(),
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
