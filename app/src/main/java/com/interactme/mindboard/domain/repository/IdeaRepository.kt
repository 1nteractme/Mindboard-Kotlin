package com.interactme.mindboard.domain.repository

import com.interactme.mindboard.data.dao.IdeaDao
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.domain.model.toDomain
import com.interactme.mindboard.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IdeaRepository(private val dao: IdeaDao) {

    fun observeIdeas(): Flow<List<Idea>> =
        dao.observeAll().map { it.map(Idea::fromEntity) }

    //  TODO: IDEAS FILTERING
//    fun searchIdeas(query: String): Flow<List<Idea>> =
//        dao.search(query).map { it.map(Idea::fromEntity) }

    suspend fun getIdea(id: Long): Idea? =
        dao.getById(id)?.toDomain()

    suspend fun addIdea(idea: Idea) =
        dao.insert(idea.toEntity())

    suspend fun updateIdea(idea: Idea) =
        dao.update(idea.toEntity())

    suspend fun deleteIdea(idea: Idea) =
        dao.delete(idea.toEntity())
}