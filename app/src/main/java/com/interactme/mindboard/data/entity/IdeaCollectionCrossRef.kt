package com.interactme.mindboard.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "idea_collections",
    primaryKeys = ["ideaId", "collectionId"],
    foreignKeys = [
        ForeignKey(
            entity = IdeaEntity::class,
            parentColumns = ["id"],
            childColumns = ["ideaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["collectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ideaId"), Index("collectionId")]
)
data class IdeaCollectionCrossRef(
    val ideaId: Long,
    val collectionId: Long,
)
