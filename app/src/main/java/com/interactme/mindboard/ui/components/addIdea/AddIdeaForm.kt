package com.interactme.mindboard.ui.components.addIdea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.interactme.mindboard.ui.components.CustomText

@Composable
fun AddIdeaForm(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    imagePicker: @Composable () -> Unit,
    isAddEnabled: Boolean,
    padding: Dp,
    onSave: () -> Unit,

    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp, start = padding, top = 0.dp, end = padding),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CustomTextField(
            value = title,
            onValueChange = onTitleChange,
            label = "Title",
            singleLine = true
        )

        CustomTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = "Description",
            singleLine = false,
            maxLines = 5
        )

        imagePicker()

        ColorSelectionWidget(selectedColor = selectedColor, onColorSelected = onColorSelected)

        val buttonColors = ButtonDefaults.buttonColors(
            containerColor = selectedColor,
            disabledContainerColor = Color(0xFF1F1F1F),
        )

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            enabled = isAddEnabled,
            colors = buttonColors
        ) {
            CustomText(
                "Add Idea",
                if (isAddEnabled) Color(0xFF1F1F1F) else Color(0xFF888888),
                16.sp
            )
        }
    }
}