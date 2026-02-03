package com.interactme.mindboard.domain.repository

import com.interactme.mindboard.domain.model.Group
import kotlinx.coroutines.flow.StateFlow

enum class AddGroupResult {
    Success,
    DuplicateName
}

enum class UpdateGroupResult {
    Success,
    DuplicateName
}

interface GroupRepository {
    val customGroups: StateFlow<List<Group>>

    suspend fun addGroup(name: String, color: Long): AddGroupResult
    suspend fun removeGroup(groupId: String)
    suspend fun updateGroup(groupId: String, name: String, color: Long): UpdateGroupResult

    suspend fun addIdeaToGroup(ideaId: Long, groupId: String)
    suspend fun removeIdeaFromGroup(ideaId: Long, groupId: String)
    suspend fun removeIdeaFromAllGroups(ideaId: Long)

    fun getGroupById(groupId: String): Group?
    fun getGroupsForIdea(ideaId: Long): List<Group>
}
