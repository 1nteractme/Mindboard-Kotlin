package com.interactme.mindboard.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.interactme.mindboard.contract.IdeaUiEffect
import com.interactme.mindboard.ui.components.FloatingBar
import com.interactme.mindboard.ui.components.IdeaDialogBar
import com.interactme.mindboard.ui.components.IdeasGrid
import com.interactme.mindboard.ui.components.TopAppBar
import com.interactme.mindboard.ui.components.addIdea.AddIdeaSheetContent
import com.interactme.mindboard.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.viewmodel.IdeaViewModel
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: IdeaViewModel, addIdeaViewModel: AddIdeaViewModel) {

    val items by viewModel.ideas.collectAsState()
    val selectedIdea by viewModel.selectedIdea.collectAsState()
    val state by addIdeaViewModel.uiState.collectAsState()
    val liquidState = rememberLiquidState()


    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is IdeaUiEffect.NavigateToEdit ->
                    addIdeaViewModel.startEdit(effect.ideaId)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1F1F))
    ) {

        IdeasGrid(
            items = items,
            modifier = Modifier.liquefiable(liquidState),
            viewModel = viewModel,
        )

        TopAppBar(
            title = "Mind Board",
            liquidState = liquidState,
        )

        FloatingBar(
            liquidState = liquidState,
            onAddClick = { addIdeaViewModel.startAdd() }
        )

        if (selectedIdea != null) {
            IdeaDialogBar(
                liquidState = liquidState,
                onDelete = viewModel::onDeleteClicked,
                onEdit = viewModel::onEditClicked,
            )
        }
    }

    val sheetState = rememberModalBottomSheetState(true)

    if (state.isSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = Color.White,
            onDismissRequest = {
                addIdeaViewModel.closeSheet()
            }
        ) {
            AddIdeaSheetContent(viewModel = addIdeaViewModel)
        }
    }

    LaunchedEffect(sheetState) { sheetState.expand() }
}