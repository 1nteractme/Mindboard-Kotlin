package com.interactme.mindboard.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.domain.repository.GroupRepository
import com.interactme.mindboard.domain.repository.IdeaRepository
import com.interactme.mindboard.ui.contract.AddIdeaUiEffect
import com.interactme.mindboard.ui.contract.AddIdeaUiEvent
import com.interactme.mindboard.ui.contract.AddIdeaUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddIdeaViewModel(
    private val ideaRepository: IdeaRepository,
    private val groupRepository: GroupRepository,
    private val defaultIdeaColor: Color,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddIdeaUiState(selectedColor = defaultIdeaColor))
    val uiState = _uiState.asStateFlow()

    val groups = groupRepository.customGroups

    private val _effects = MutableSharedFlow<AddIdeaUiEffect>()
    val effects = _effects.asSharedFlow()

    fun startAdd() {
        _uiState.value = AddIdeaUiState(
            isSheetVisible = true,
            selectedColor = defaultIdeaColor
        )
    }

    fun startEdit(id: Long) = viewModelScope.launch {
        val idea = ideaRepository.getIdea(id) ?: return@launch
        val selectedGroupIds = groupRepository
            .getGroupsForIdea(id)
            .map { it.id }
            .toMutableSet()

        if (selectedGroupIds.isEmpty() && idea.collectionId != null) {
            selectedGroupIds.add(idea.collectionId.toString())
        }

        _uiState.value = AddIdeaUiState(
            id = idea.id,
            title = idea.title,
            description = idea.description,
            selectedColor = Color(idea.color.toInt()),
            imageUri = idea.imageUri,
            createdAt = idea.createdAt,
            isAddEnabled = true,
            isSheetVisible = true,
            isFavorite = idea.isFavorite,
            selectedGroupIds = selectedGroupIds.toSet()
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
            is AddIdeaUiEvent.GroupSelected -> update {
                val newGroups = if (event.isSelected) selectedGroupIds + event.groupId
                else selectedGroupIds - event.groupId
                copy(selectedGroupIds = newGroups)
            }

            AddIdeaUiEvent.RemoveImage -> update { copy(imageUri = null) }
            AddIdeaUiEvent.SaveClicked -> save()
        }
    }

    private fun update(reducer: AddIdeaUiState.() -> AddIdeaUiState) {
        _uiState.update { current ->
            val next = current.reducer()
            next.copy(isAddEnabled = isValid(next))
        }
    }

    private fun isValid(state: AddIdeaUiState): Boolean =
        state.title.isNotBlank() && state.description.isNotBlank()

    private fun save() = viewModelScope.launch {
        val state = _uiState.value

        if (!isValid(state)) {
            _effects.emit(AddIdeaUiEffect.ShowError("Fill all required fields"))
            return@launch
        }

        val primaryGroupId = state.selectedGroupIds
            .mapNotNull { it.toLongOrNull() }
            .sorted()
            .firstOrNull()

        val idea = Idea(
            id = state.id,
            title = state.title,
            description = state.description,
            color = state.selectedColor.toArgb().toLong(),
            imageUri = state.imageUri,
            collectionId = primaryGroupId,
            createdAt = if (state.id == 0L) System.currentTimeMillis() else state.createdAt,
            isFavorite = state.isFavorite
        )

        val ideaId = if (state.id == 0L) {
            ideaRepository.addIdea(idea)
        } else {
            ideaRepository.updateIdea(idea)
            idea.id
        }

        groupRepository.removeIdeaFromAllGroups(ideaId)
        state.selectedGroupIds.forEach { groupId ->
            groupRepository.addIdeaToGroup(ideaId, groupId)
        }

        _effects.emit(AddIdeaUiEffect.NavigateBack)
    }
}
