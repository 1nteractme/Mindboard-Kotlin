package com.interactme.mindboard.contract

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiEffectsState(
    val isGlassEnabled: Boolean = false
)

class UiEffectsViewModel : ViewModel() {
    private val _state = MutableStateFlow(UiEffectsState())
    val state = _state.asStateFlow()

    fun toggleGlass() {
        _state.update {
            it.copy(isGlassEnabled = !it.isGlassEnabled)
        }
    }
}
