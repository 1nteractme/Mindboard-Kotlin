package com.interactme.mindboard.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.interactme.mindboard.data.dao.CollectionDao
import com.interactme.mindboard.data.dao.IdeaCollectionDao
import com.interactme.mindboard.data.dao.IdeaDao
import com.interactme.mindboard.data.entity.CollectionEntity
import com.interactme.mindboard.data.entity.IdeaCollectionCrossRef
import com.interactme.mindboard.data.entity.IdeaEntity

@Database(
    entities = [IdeaEntity::class, CollectionEntity::class, IdeaCollectionCrossRef::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ideaDao(): IdeaDao
    abstract fun collectionDao(): CollectionDao
    abstract fun ideaCollectionDao(): IdeaCollectionDao
}
