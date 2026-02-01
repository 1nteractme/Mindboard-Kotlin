package com.interactme.mindboard.contract

sealed interface IdeaUiEffect {
    data class NavigateToEdit(val ideaId: Long) : IdeaUiEffect
}