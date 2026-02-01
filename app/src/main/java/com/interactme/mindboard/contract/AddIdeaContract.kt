package com.interactme.mindboard.contract

import androidx.compose.ui.graphics.Color

data class AddIdeaUiState(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val selectedColor: Color = Color.White,
    val imageUri: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isAddEnabled: Boolean = false,
    val isSheetVisible: Boolean = false
)

sealed interface AddIdeaUiEvent {
    data class TitleChanged(val value: String) : AddIdeaUiEvent

    data class DescriptionChanged(val value: String) : AddIdeaUiEvent

    data class ColorSelected(val color: Color) : AddIdeaUiEvent

    data class ImageSelected(val uri: String) : AddIdeaUiEvent
    object RemoveImage : AddIdeaUiEvent

    object SaveClicked : AddIdeaUiEvent
}

sealed class AddIdeaUiEffect {
    object NavigateBack : AddIdeaUiEffect()
    data class ShowError(val message: String) : AddIdeaUiEffect()
}