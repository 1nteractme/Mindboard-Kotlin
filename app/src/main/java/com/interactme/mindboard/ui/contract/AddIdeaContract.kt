package com.interactme.mindboard.ui.contract

import androidx.compose.ui.graphics.Color

data class AddIdeaUiState(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val selectedColor: Color = Color.Unspecified,
    val imageUri: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isAddEnabled: Boolean = false,
    val isSheetVisible: Boolean = false,
    val isFavorite: Boolean = false,
    val selectedGroupIds: Set<String> = emptySet(),
)

sealed interface AddIdeaUiEvent {
    data class TitleChanged(val value: String) : AddIdeaUiEvent
    data class DescriptionChanged(val value: String) : AddIdeaUiEvent
    data class ColorSelected(val color: Color) : AddIdeaUiEvent
    data class ImageSelected(val uri: String) : AddIdeaUiEvent
    data class GroupSelected(val groupId: String, val isSelected: Boolean) : AddIdeaUiEvent
    object RemoveImage : AddIdeaUiEvent
    object SaveClicked : AddIdeaUiEvent
}

sealed class AddIdeaUiEffect {
    object NavigateBack : AddIdeaUiEffect()
    data class ShowError(val message: String) : AddIdeaUiEffect()
}
