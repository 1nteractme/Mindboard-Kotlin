package com.interactme.mindboard.data.repository

import com.interactme.mindboard.data.dao.IdeaDao
import com.interactme.mindboard.data.mapper.toDomain
import com.interactme.mindboard.data.mapper.toEntity
import com.interactme.mindboard.domain.model.Idea
import com.interactme.mindboard.domain.repository.IdeaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IdeaRepositoryImpl(
    private val dao: IdeaDao
) : IdeaRepository {
    override fun observeIdeas(): Flow<List<Idea>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getIdea(id: Long): Idea? =
        dao.getById(id)?.toDomain()

    override suspend fun addIdea(idea: Idea): Long =
        dao.insert(idea.toEntity())

    override suspend fun updateIdea(idea: Idea) {
        dao.update(idea.toEntity())
    }

    override suspend fun deleteIdea(idea: Idea) {
        dao.delete(idea.toEntity())
    }
}
