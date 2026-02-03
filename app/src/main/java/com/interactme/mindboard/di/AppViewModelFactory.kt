package com.interactme.mindboard.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.graphics.Color
import com.interactme.mindboard.domain.repository.GroupRepository
import com.interactme.mindboard.domain.repository.IdeaRepository
import com.interactme.mindboard.ui.theme.GroupColors
import com.interactme.mindboard.ui.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.ui.viewmodel.IdeaViewModel
import com.interactme.mindboard.ui.viewmodel.UiEffectsViewModel

class AppViewModelFactory(
    private val ideaRepository: IdeaRepository,
    private val groupRepository: GroupRepository,
    private val groupColors: GroupColors,
    private val defaultIdeaColor: Color,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(IdeaViewModel::class.java) ->
                IdeaViewModel(ideaRepository, groupRepository, groupColors) as T
            modelClass.isAssignableFrom(AddIdeaViewModel::class.java) ->
                AddIdeaViewModel(ideaRepository, groupRepository, defaultIdeaColor) as T
            modelClass.isAssignableFrom(UiEffectsViewModel::class.java) ->
                UiEffectsViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
