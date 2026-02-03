package com.interactme.mindboard.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.interactme.mindboard.data.entity.IdeaCollectionCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeaCollectionDao {
    @Query("SELECT * FROM idea_collections")
    fun observeAll(): Flow<List<IdeaCollectionCrossRef>>

    @Query("SELECT collectionId FROM idea_collections WHERE ideaId = :ideaId")
    suspend fun getCollectionIdsForIdea(ideaId: Long): List<Long>

    @Query("SELECT ideaId FROM idea_collections WHERE collectionId = :collectionId")
    suspend fun getIdeaIdsForCollection(collectionId: Long): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: IdeaCollectionCrossRef)

    @Query("DELETE FROM idea_collections WHERE ideaId = :ideaId AND collectionId = :collectionId")
    suspend fun delete(ideaId: Long, collectionId: Long)

    @Query("DELETE FROM idea_collections WHERE ideaId = :ideaId")
    suspend fun deleteByIdeaId(ideaId: Long)
}
