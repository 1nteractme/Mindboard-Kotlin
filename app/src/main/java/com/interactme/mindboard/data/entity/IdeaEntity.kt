package com.interactme.mindboard.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ideas")
data class IdeaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val color: Long,
    val createdAt: Long,
    val collectionId: Long? = null,
    val imageUri: String? = null,
)