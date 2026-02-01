package com.interactme.mindboard.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interactme.mindboard.contract.AddIdeaUiEffect
import com.interactme.mindboard.contract.AddIdeaUiEvent
import com.interactme.mindboard.contract.AddIdeaUiState
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.domain.repository.IdeaRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddIdeaViewModel(private val repository: IdeaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AddIdeaUiState())
    val uiState = _uiState.asStateFlow()

    // TODO: IMPLEMENT EFFECTS USAGE TO COMPOSE
    private val _effects = MutableSharedFlow<AddIdeaUiEffect>()
//    val effects = _effects.asSharedFlow()

    fun startAdd() {
        _uiState.value = AddIdeaUiState(isSheetVisible = true)
    }

    fun startEdit(id: Long) = viewModelScope.launch {
        val idea = repository.getIdea(id) ?: return@launch

        _uiState.value = AddIdeaUiState(
            id = idea.id,
            title = idea.title,
            description = idea.description,
            selectedColor = Color(idea.color),
            imageUri = idea.imageUri,
            createdAt = idea.createdAt,
            isAddEnabled = true,
            isSheetVisible = true
        )
    }

    fun closeSheet() {
        _uiState.update { it.copy(isSheetVisible = false) }
    }

    fun onEvent(event: AddIdeaUiEvent) {
        when (event) {
            is AddIdeaUiEvent.TitleChanged -> update { copy(title = event.value) }

            is AddIdeaUiEvent.DescriptionChanged -> update { copy(description = event.value) }

            is AddIdeaUiEvent.ColorSelected -> update { copy(selectedColor = event.color) }

            is AddIdeaUiEvent.ImageSelected -> update { copy(imageUri = event.uri) }

            AddIdeaUiEvent.RemoveImage -> update { copy(imageUri = null) }

            AddIdeaUiEvent.SaveClicked -> save()
        }
    }

    private fun update(reducer: AddIdeaUiState.() -> AddIdeaUiState) {
        _uiState.update { it.reducer().copy(isAddEnabled = isValid(it.reducer())) }
    }

    private fun isValid(state: AddIdeaUiState): Boolean =
        state.title.isNotBlank() && state.description.isNotBlank()

    private fun save() = viewModelScope.launch {
        val state = _uiState.value

        if (!isValid(state)) {
            _effects.emit(AddIdeaUiEffect.ShowError("Fill all required fields"))
            return@launch
        }

        val idea = Idea(
            id = state.id,
            title = state.title,
            description = state.description,
            color = state.selectedColor.toArgb().toLong(),
            imageUri = state.imageUri,
            collectionId = null,
            createdAt = if (state.id == 0L) System.currentTimeMillis() else state.createdAt,
        )

        if (state.id == 0L) {
            repository.addIdea(idea)
        } else {
            repository.updateIdea(idea)
        }

        _effects.emit(AddIdeaUiEffect.NavigateBack)
    }
}