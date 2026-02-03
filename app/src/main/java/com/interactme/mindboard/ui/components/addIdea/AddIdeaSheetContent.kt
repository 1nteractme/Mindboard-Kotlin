package com.interactme.mindboard.ui.components.addIdea

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.interactme.mindboard.ui.contract.AddIdeaUiEvent
import com.interactme.mindboard.ui.viewmodel.AddIdeaViewModel

@Composable
fun AddIdeaSheetContent(viewModel: AddIdeaViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val groups by viewModel.groups.collectAsState()

    val context = LocalContext.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                viewModel.onEvent(
                    AddIdeaUiEvent.ImageSelected(it.toString())
                )
            }
        }

    AddIdeaForm(
        title = uiState.title,
        onTitleChange = {
            viewModel.onEvent(AddIdeaUiEvent.TitleChanged(it))
        },

        description = uiState.description,
        onDescriptionChange = {
            viewModel.onEvent(AddIdeaUiEvent.DescriptionChanged(it))
        },

        selectedColor = uiState.selectedColor,
        onColorSelected = {
            viewModel.onEvent(AddIdeaUiEvent.ColorSelected(it))
        },

        imagePicker = {
            ImagePicker(
                imageUri = uiState.imageUri,
                onPickImage = { imagePickerLauncher.launch(arrayOf("image/*")) },
                onRemoveImage = { viewModel.onEvent(AddIdeaUiEvent.RemoveImage) }
            )
        },

        groups = groups,
        selectedGroupIds = uiState.selectedGroupIds,
        onGroupSelected = { groupId, isSelected ->
            viewModel.onEvent(AddIdeaUiEvent.GroupSelected(groupId, isSelected))
        },

        isAddEnabled = uiState.isAddEnabled,
        padding = 10.dp,
        onSave = {
            viewModel.onEvent(AddIdeaUiEvent.SaveClicked)
        }
    )
}
