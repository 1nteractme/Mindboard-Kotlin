package com.interactme.mindboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.interactme.mindboard.ui.contract.UiEffectsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UiEffectsViewModel : ViewModel() {
    private val _state = MutableStateFlow(UiEffectsState())
    val state = _state.asStateFlow()

    fun toggleGlass() {
        _state.update {
            it.copy(isGlassEnabled = !it.isGlassEnabled)
        }
    }
}
