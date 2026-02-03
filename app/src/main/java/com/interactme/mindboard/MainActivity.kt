package com.interactme.mindboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.interactme.mindboard.di.AppViewModelFactory
import com.interactme.mindboard.ui.navigation.AppNavHost
import com.interactme.mindboard.ui.theme.MindBoardTheme
import com.interactme.mindboard.ui.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.ui.viewmodel.IdeaViewModel
import com.interactme.mindboard.ui.viewmodel.UiEffectsViewModel

class MainActivity : ComponentActivity() {
    private val viewModelFactory: AppViewModelFactory by lazy {
        val container = (application as MindBoardApp).container
        AppViewModelFactory(
            container.ideaRepository,
            container.groupRepository,
            container.groupColors,
            container.defaultIdeaColor
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = ViewModelProvider(this, viewModelFactory)
        val ideaViewModel = provider[IdeaViewModel::class.java]
        val addIdeaViewModel = provider[AddIdeaViewModel::class.java]
        val uiEffectsViewModel = provider[UiEffectsViewModel::class.java]

        setContent {
            MindBoardTheme {
                AppNavHost(ideaViewModel, addIdeaViewModel, uiEffectsViewModel)
            }
        }
    }
}
