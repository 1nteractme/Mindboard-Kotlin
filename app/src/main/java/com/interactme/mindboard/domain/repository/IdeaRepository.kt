package com.interactme.mindboard.domain.repository

import com.interactme.mindboard.domain.model.Idea
import kotlinx.coroutines.flow.Flow

interface IdeaRepository {
    fun observeIdeas(): Flow<List<Idea>>

    // TODO: add search when UI is ready
    // fun searchIdeas(query: String): Flow<List<Idea>>

    suspend fun getIdea(id: Long): Idea?
    suspend fun addIdea(idea: Idea): Long
    suspend fun updateIdea(idea: Idea)
    suspend fun deleteIdea(idea: Idea)
}
