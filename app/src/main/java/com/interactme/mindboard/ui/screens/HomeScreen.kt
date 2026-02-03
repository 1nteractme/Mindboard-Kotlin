package com.interactme.mindboard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.interactme.mindboard.ui.contract.IdeaUiEffect
import com.interactme.mindboard.ui.contract.AddIdeaUiEffect
import com.interactme.mindboard.ui.viewmodel.UiEffectsViewModel
import com.interactme.mindboard.ui.components.FloatingBar
import com.interactme.mindboard.ui.components.IdeaDialogBar
import com.interactme.mindboard.ui.components.IdeasGrid
import com.interactme.mindboard.ui.components.TopAppBar
import com.interactme.mindboard.ui.components.addIdea.AddIdeaSheetContent
import com.interactme.mindboard.ui.components.addIdea.ColorSelectionWidget
import com.interactme.mindboard.ui.components.addIdea.CustomTextField
import com.interactme.mindboard.ui.components.groups.GroupBar
import com.interactme.mindboard.domain.model.Group
import com.interactme.mindboard.ui.viewmodel.AddIdeaViewModel
import com.interactme.mindboard.ui.viewmodel.IdeaViewModel
import com.interactme.mindboard.ui.theme.MindBoardTheme
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    ideaViewModel: IdeaViewModel,
    addIdeaViewModel: AddIdeaViewModel,
    uiEffectsViewModel: UiEffectsViewModel,
) {
    val scope = rememberCoroutineScope()

    val selectedIdea by ideaViewModel.selectedIdea.collectAsState()
    val filteredIdeas by ideaViewModel.filteredIdeas.collectAsState()

    val selectedGroupId by ideaViewModel.selectedGroupId.collectAsState()
    val groups by ideaViewModel.groups.collectAsState()

    val addIdeaUiState by addIdeaViewModel.uiState.collectAsState()
    val uiEffectsState by uiEffectsViewModel.state.collectAsState()
    val liquidState = rememberLiquidState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var editGroupId by remember { mutableStateOf<String?>(null) }
    val editGroup = groups.find { it.id == editGroupId }

    LaunchedEffect(Unit) {
        ideaViewModel.effects.collect { effect ->
            when (effect) {
                is IdeaUiEffect.NavigateToEdit -> addIdeaViewModel.startEdit(effect.ideaId)
                is IdeaUiEffect.ShowError -> scope.launch { }
                IdeaUiEffect.GroupCreatedSuccessfully -> Unit
            }
        }
    }

    LaunchedEffect(Unit) {
        addIdeaViewModel.effects.collect { effect ->
            when (effect) {
                AddIdeaUiEffect.NavigateBack -> addIdeaViewModel.closeSheet()
                is AddIdeaUiEffect.ShowError -> scope.launch { }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MindBoardTheme.colors.appBg)
    )
    {
        IdeasGrid(filteredIdeas, Modifier.liquefiable(liquidState), ideaViewModel)

        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(20.dp)) {
            TopAppBar(liquidState, uiEffectsState.isGlassEnabled)

            GroupBar(
                liquidState,
                isGlassEnabled = uiEffectsState.isGlassEnabled,
                groups = groups,
                selectedGroupId = selectedGroupId,
                onAddGroupClick = { ideaViewModel.createQuickGroup() },
                onGroupSelected = { groupId -> ideaViewModel.selectGroup(groupId) },
                onBackClick = { ideaViewModel.selectGroup(Group.ALL_ID) },
                onEditGroupClick = { groupId -> editGroupId = groupId }
            )
        }

        FloatingBar({ addIdeaViewModel.startAdd() }, liquidState, uiEffectsState.isGlassEnabled)

        selectedIdea?.let {
            IdeaDialogBar(
                ideaViewModel::onDeleteClicked,
                ideaViewModel::onEditClicked,
                liquidState,
                uiEffectsState.isGlassEnabled
            )
        }
    }

    if (addIdeaUiState.isSheetVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = MindBoardTheme.colors.white,
            onDismissRequest = { addIdeaViewModel.closeSheet() }
        ) {
            AddIdeaSheetContent(viewModel = addIdeaViewModel)
        }

        LaunchedEffect(addIdeaUiState.isSheetVisible) {
            sheetState.expand()
        }
    }

    if (editGroup != null) {
        var name by remember(editGroup.id) { mutableStateOf(editGroup.name) }
        var color by remember(editGroup.id) { mutableStateOf(Color(editGroup.color.toInt())) }

        AlertDialog(
            onDismissRequest = { editGroupId = null },
            title = {
                Text(
                    text = "Edit group",
                    fontSize = 18.sp,
                    color = MindBoardTheme.colors.textPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Group name",
                        singleLine = true
                    )
                    ColorSelectionWidget(
                        selectedColor = color,
                        onColorSelected = { color = it }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val trimmed = name.trim()
                        if (trimmed.isNotEmpty()) {
                            ideaViewModel.updateGroup(
                                editGroup.id,
                                trimmed,
                                color.toArgb().toLong()
                            )
                            editGroupId = null
                        }
                    },
                    enabled = name.trim().isNotEmpty()
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { editGroupId = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}