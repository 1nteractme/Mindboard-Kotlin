package com.interactme.mindboard.ui.contract

sealed interface IdeaUiEffect {
    data class NavigateToEdit(val ideaId: Long) : IdeaUiEffect
    data class ShowError(val message: String) : IdeaUiEffect
    object GroupCreatedSuccessfully : IdeaUiEffect
}
