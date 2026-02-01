package com.interactme.mindboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.interactme.mindboard.domain.repository.IdeaRepository
import com.interactme.mindboard.ui.navigation.AppNavHost
import com.interactme.mindboard.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.viewmodel.IdeaViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = (application as MindBoardApp).database
        val repository = IdeaRepository(database.ideaDao())

        val viewModel = IdeaViewModel(repository)
        val addIdeaViewModel = AddIdeaViewModel(repository)

        setContent {
            AppNavHost(
                viewModel = viewModel,
                addIdeaViewModel = addIdeaViewModel
            )
        }
    }
}