package com.interactme.mindboard.data.mapper

import com.interactme.mindboard.data.entity.IdeaEntity
import com.interactme.mindboard.domain.model.Idea

fun IdeaEntity.toDomain(): Idea = Idea(
    id = id,
    title = title,
    description = description,
    color = color,
    collectionId = collectionId,
    createdAt = createdAt,
    imageUri = imageUri,
    isFavorite = isFavorite,
)

fun Idea.toEntity(): IdeaEntity = IdeaEntity(
    id = id,
    title = title,
    description = description,
    color = color,
    createdAt = createdAt,
    collectionId = collectionId,
    imageUri = imageUri,
    isFavorite = isFavorite,
)
