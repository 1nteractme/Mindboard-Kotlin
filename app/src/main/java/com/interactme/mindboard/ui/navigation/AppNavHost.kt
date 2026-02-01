package com.interactme.mindboard.ui.navigation

import androidx.compose.runtime.Composable
import com.interactme.mindboard.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.ui.screen.HomeScreen
import com.interactme.mindboard.viewmodel.IdeaViewModel

@Composable
fun AppNavHost(viewModel: IdeaViewModel, addIdeaViewModel: AddIdeaViewModel) {
    HomeScreen(
        viewModel = viewModel,
        addIdeaViewModel = addIdeaViewModel,)
}