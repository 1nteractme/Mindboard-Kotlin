package com.interactme.mindboard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.interactme.mindboard.contract.IdeaUiEffect
import com.interactme.mindboard.contract.UiEffectsViewModel
import com.interactme.mindboard.ui.components.FloatingBar
import com.interactme.mindboard.ui.components.IdeaDialogBar
import com.interactme.mindboard.ui.components.IdeasGrid
import com.interactme.mindboard.ui.components.TopAppBar
import com.interactme.mindboard.ui.components.addIdea.AddIdeaSheetContent
import com.interactme.mindboard.ui.components.groups.GroupBar
import com.interactme.mindboard.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.viewmodel.IdeaViewModel
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    ideaViewModel: IdeaViewModel,
    addIdeaViewModel: AddIdeaViewModel,
    uiEffectsViewModel: UiEffectsViewModel
) {

    val items by ideaViewModel.ideas.collectAsState()
    val selectedIdea by ideaViewModel.selectedIdea.collectAsState()
    val state by addIdeaViewModel.uiState.collectAsState()
    val liquidState = rememberLiquidState()
    val uiState by uiEffectsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        ideaViewModel.effects.collect { effect ->
            when (effect) {
                is IdeaUiEffect.NavigateToEdit -> addIdeaViewModel.startEdit(effect.ideaId)
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1F1F))
    )
    {
        IdeasGrid(items, Modifier.liquefiable(liquidState), ideaViewModel)

        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(20.dp)) {
            TopAppBar(liquidState, uiState.isGlassEnabled)

            GroupBar(liquidState, uiState.isGlassEnabled)
        }

        FloatingBar({ addIdeaViewModel.startAdd() }, liquidState, uiState.isGlassEnabled)

        if (selectedIdea != null) {
            IdeaDialogBar(
                ideaViewModel::onDeleteClicked,
                ideaViewModel::onEditClicked,
                liquidState,
                uiState.isGlassEnabled
            )
        }
    }

    val sheetState = rememberModalBottomSheetState(true)

    if (state.isSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState, containerColor = Color.White, onDismissRequest = {
                addIdeaViewModel.closeSheet()
            }) {
            AddIdeaSheetContent(viewModel = addIdeaViewModel)
        }
    }

    LaunchedEffect(sheetState) { sheetState.expand() }
}