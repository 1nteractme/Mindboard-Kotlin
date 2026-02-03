package com.interactme.mindboard.domain.model

data class Group(
    val id: String,
    val name: String,
    val color: Long,
    val isSystem: Boolean = false,
    val ideaIds: Set<Long> = emptySet(),
) {
    companion object {
        const val ALL_ID = "system_all"
        const val FAV_ID = "system_favorites"
        const val ALL_NAME = "All"
        const val FAV_NAME = "Favorites"
    }
}
