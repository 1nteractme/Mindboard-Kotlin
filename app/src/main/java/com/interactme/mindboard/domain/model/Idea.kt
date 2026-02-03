package com.interactme.mindboard.domain.model

data class Idea(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val color: Long,
    val collectionId: Long? = null,
    val createdAt: Long,
    val imageUri: String? = null,
    val isFavorite: Boolean = false,
)