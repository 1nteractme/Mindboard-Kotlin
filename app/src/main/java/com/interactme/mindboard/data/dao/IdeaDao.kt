package com.interactme.mindboard.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.interactme.mindboard.data.entity.IdeaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeaDao {
    @Query("SELECT * FROM ideas")
    fun observeAll(): Flow<List<IdeaEntity>>

    @Query("SELECT * FROM ideas WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): IdeaEntity?

    @Query("SELECT * FROM ideas WHERE title LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<IdeaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: IdeaEntity): Long

    @Update
    suspend fun update(entity: IdeaEntity)

    @Delete
    suspend fun delete(entity: IdeaEntity)
}
