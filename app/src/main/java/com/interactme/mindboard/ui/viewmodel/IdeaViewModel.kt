package com.interactme.mindboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interactme.mindboard.domain.model.Group
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.domain.repository.AddGroupResult
import com.interactme.mindboard.domain.repository.GroupRepository
import com.interactme.mindboard.domain.repository.IdeaRepository
import com.interactme.mindboard.domain.repository.UpdateGroupResult
import com.interactme.mindboard.ui.contract.IdeaUiEffect
import com.interactme.mindboard.ui.theme.GroupColors
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class IdeaViewModel(
    private val ideaRepository: IdeaRepository,
    private val groupRepository: GroupRepository,
    private val groupColors: GroupColors,
) : ViewModel() {
    private val _selectedIdea = MutableStateFlow<Idea?>(null)
    val selectedIdea = _selectedIdea.asStateFlow()

    private val _selectedGroupId = MutableStateFlow<String?>(Group.ALL_ID)
    val selectedGroupId = _selectedGroupId.asStateFlow()

    val ideas: StateFlow<List<Idea>> = ideaRepository.observeIdeas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val groups: StateFlow<List<Group>> = combine(
        ideas,
        groupRepository.customGroups
    ) { ideas, customGroups ->
        val allGroup = Group(
            id = Group.ALL_ID,
            name = Group.ALL_NAME,
            color = groupColors.systemAll,
            isSystem = true,
            ideaIds = ideas.map { it.id }.toSet()
        )
        val favoritesGroup = Group(
            id = Group.FAV_ID,
            name = Group.FAV_NAME,
            color = groupColors.systemFavorites,
            isSystem = true,
            ideaIds = ideas.filter { it.isFavorite }.map { it.id }.toSet()
        )

        (listOf(allGroup, favoritesGroup) + customGroups).sortedWith(
            compareBy<Group>({ !it.isSystem }, { it.name.lowercase(Locale.getDefault()) })
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val filteredIdeas: StateFlow<List<Idea>> = combine(
        selectedGroupId,
        ideas,
        groups
    ) { selectedGroupId, ideas, groups ->
        when (selectedGroupId) {
            null -> emptyList()
            Group.ALL_ID -> ideas
            Group.FAV_ID -> ideas.filter { it.isFavorite }
            else -> {
                val group = groups.find { it.id == selectedGroupId } ?: return@combine emptyList()
                ideas.filter { it.id in group.ideaIds }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _effects = MutableSharedFlow<IdeaUiEffect>()
    val effects = _effects.asSharedFlow()

    fun onIdeaMenuClick(idea: Idea) {
        _selectedIdea.value = idea
    }

    fun onEditClicked() = viewModelScope.launch {
        val idea = _selectedIdea.value ?: return@launch
        _effects.emit(IdeaUiEffect.NavigateToEdit(idea.id))
        _selectedIdea.value = null
    }

    fun onDeleteClicked() = viewModelScope.launch {
        val idea = _selectedIdea.value ?: return@launch
        ideaRepository.deleteIdea(idea)
        groupRepository.removeIdeaFromAllGroups(idea.id)
        _selectedIdea.value = null
    }

    fun dismissDialog() {
        _selectedIdea.value = null
    }

    fun selectGroup(groupId: String?) {
        _selectedGroupId.value = groupId
    }

    fun toggleFavorite(ideaId: Long) = viewModelScope.launch {
        val idea = ideas.value.find { it.id == ideaId } ?: return@launch
        val updatedIdea = idea.copy(isFavorite = !idea.isFavorite)
        ideaRepository.updateIdea(updatedIdea)
    }

    fun getGroupsForIdea(ideaId: Long): List<Group> {
        val idea = ideas.value.find { it.id == ideaId } ?: return emptyList()
        val result = groupRepository.getGroupsForIdea(ideaId).toMutableList()
        result.add(
            Group(
                id = Group.ALL_ID,
                name = Group.ALL_NAME,
                color = groupColors.systemAll,
                isSystem = true,
                ideaIds = setOf(idea.id)
            )
        )
        if (idea.isFavorite) {
            result.add(
                Group(
                    id = Group.FAV_ID,
                    name = Group.FAV_NAME,
                    color = groupColors.systemFavorites,
                    isSystem = true,
                    ideaIds = setOf(idea.id)
                )
            )
        }
        return result
    }

    fun createQuickGroup() {
        val baseName = "New Group"
        val existing = groups.value.map { it.name.lowercase(Locale.getDefault()) }.toSet()
        var index = 1
        var name = baseName
        while (name.lowercase(Locale.getDefault()) in existing) {
            index += 1
            name = "$baseName $index"
        }
        createCustomGroup(name, groupColors.defaultGroup)
    }

    fun createCustomGroup(name: String, color: Long) {
        viewModelScope.launch {
            val result = groupRepository.addGroup(name, color)
            if (result == AddGroupResult.DuplicateName) {
                _effects.emit(IdeaUiEffect.ShowError("Группа с таким названием уже существует"))
            }
        }
    }

    fun updateGroup(groupId: String, name: String, color: Long) {
        viewModelScope.launch {
            val result = groupRepository.updateGroup(groupId, name, color)
            if (result == UpdateGroupResult.DuplicateName) {
                _effects.emit(IdeaUiEffect.ShowError("Группа с таким названием уже существует"))
            }
        }
    }

}
