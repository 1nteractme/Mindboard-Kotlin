package com.interactme.mindboard.domain.model

import com.interactme.mindboard.data.entity.IdeaEntity

data class Idea(
    val id: Long,
    val title: String,
    val description: String,
    val color: Long,
    val collectionId: Long?,
    val createdAt: Long,
    val imageUri: String? = null,
) {
    companion object {
        fun fromEntity(e: IdeaEntity) =
            Idea(e.id, e.title, e.description, e.color, e.collectionId, e.createdAt, e.imageUri)
    }
}

fun Idea.toEntity() = IdeaEntity(
    id = id,
    title = title,
    description = description,
    color = color,
    createdAt = createdAt,
    collectionId = collectionId,
    imageUri = imageUri,
)

fun IdeaEntity.toDomain() = Idea(
    id = id,
    title = title,
    description = description,
    color = color,
    createdAt = createdAt,
    collectionId = collectionId,
    imageUri = imageUri,
)