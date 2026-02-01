package com.interactme.mindboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interactme.mindboard.contract.IdeaUiEffect
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.domain.repository.IdeaRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IdeaViewModel(private val repository: IdeaRepository) : ViewModel() {

    val ideas: StateFlow<List<Idea>> = repository.observeIdeas().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )

    private val _selectedIdea = MutableStateFlow<Idea?>(null)
    val selectedIdea: StateFlow<Idea?> = _selectedIdea.asStateFlow()

//    val isDialogVisible: StateFlow<Boolean> = selectedIdea
//        .map { it != null }
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    private val _effects = MutableSharedFlow<IdeaUiEffect>()
    val effects = _effects.asSharedFlow()

    fun onIdeaMenuClick(idea: Idea) {
        _selectedIdea.value = idea
    }

    fun onEditClicked() = viewModelScope.launch {
        val idea = _selectedIdea.value ?: return@launch
        _effects.emit(IdeaUiEffect.NavigateToEdit(idea.id))
        _selectedIdea.value = null

        dismissDialog()
    }

    fun onDeleteClicked() = viewModelScope.launch {
        val idea = _selectedIdea.value ?: return@launch
        repository.deleteIdea(idea)
        _selectedIdea.value = null

        dismissDialog()
    }

    fun dismissDialog() {
        _selectedIdea.value = null
    }
}