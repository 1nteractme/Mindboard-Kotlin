package com.interactme.mindboard.data.database

import com.interactme.mindboard.data.dao.IdeaDao
import com.interactme.mindboard.data.entity.IdeaEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import com.interactme.mindboard.data.entity.CollectionEntity

@Database(
    entities = [IdeaEntity::class, CollectionEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ideaDao(): IdeaDao
}