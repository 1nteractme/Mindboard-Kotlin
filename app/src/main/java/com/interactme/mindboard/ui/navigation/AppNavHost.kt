package com.interactme.mindboard.ui.navigation

import androidx.compose.runtime.Composable
import com.interactme.mindboard.contract.UiEffectsViewModel
import com.interactme.mindboard.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.ui.screens.HomeScreen
import com.interactme.mindboard.viewmodel.IdeaViewModel

@Composable
fun AppNavHost(
    ideaViewModel: IdeaViewModel,
    addIdeaViewModel: AddIdeaViewModel,
    uiEffectsViewModel: UiEffectsViewModel
) {
    HomeScreen(ideaViewModel, addIdeaViewModel, uiEffectsViewModel)
}