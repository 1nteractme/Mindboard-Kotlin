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
import com.interactme.mindboard.ui.components.CollectionChip
import com.interactme.mindboard.domain.model.Group
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import com.interactme.mindboard.ui.theme.MindBoardTheme

@Composable
fun AddIdeaForm(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    imagePicker: @Composable () -> Unit,
    groups: List<Group>,
    selectedGroupIds: Set<String>,
    onGroupSelected: (String, Boolean) -> Unit,
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

        if (groups.isNotEmpty()) {
            CustomText(
                title = "Groups",
                titleColor = MindBoardTheme.colors.textPrimary,
                fontSize = 16.sp,
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(end = 12.dp)
            ) {
                items(groups, key = { it.id }) { group ->
                    val selected = selectedGroupIds.contains(group.id)
                    CollectionChip(
                        name = group.name,
                        color = Color(group.color.toInt()),
                        selected = selected,
                        onClick = { onGroupSelected(group.id, !selected) }
                    )
                }
            }
        } else {
            Box(modifier = Modifier.height(4.dp))
        }

        val buttonColors = ButtonDefaults.buttonColors(
            containerColor = selectedColor,
            disabledContainerColor = MindBoardTheme.colors.appBg,
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
                if (isAddEnabled) MindBoardTheme.colors.textPrimary else MindBoardTheme.colors.textMuted,
                16.sp
            )
        }
    }
}
