package com.interactme.mindboard.data.repository

import com.interactme.mindboard.data.dao.CollectionDao
import com.interactme.mindboard.data.dao.IdeaCollectionDao
import com.interactme.mindboard.data.entity.CollectionEntity
import com.interactme.mindboard.data.entity.IdeaCollectionCrossRef
import com.interactme.mindboard.domain.model.Group
import com.interactme.mindboard.domain.repository.AddGroupResult
import com.interactme.mindboard.domain.repository.GroupRepository
import com.interactme.mindboard.domain.repository.UpdateGroupResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Locale

class RoomGroupRepository(
    private val collectionDao: CollectionDao,
    private val ideaCollectionDao: IdeaCollectionDao,
    appScope: CoroutineScope,
) : GroupRepository {
    override val customGroups: StateFlow<List<Group>> =
        combine(
            collectionDao.observeAll(),
            ideaCollectionDao.observeAll()
        ) { collections, links ->
            val ideaIdsByCollection = links.groupBy { it.collectionId }
                .mapValues { entry -> entry.value.map { it.ideaId }.toSet() }

            collections.map { entity ->
                Group(
                    id = entity.id.toString(),
                    name = entity.name,
                    color = entity.color,
                    isSystem = false,
                    ideaIds = ideaIdsByCollection[entity.id].orEmpty()
                )
            }.sortedBy { it.name.lowercase(Locale.getDefault()) }
        }.stateIn(appScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    override suspend fun addGroup(name: String, color: Long): AddGroupResult {
        val exists = customGroups.value.any { it.name.equals(name, ignoreCase = true) }
        if (exists) return AddGroupResult.DuplicateName

        collectionDao.insert(
            CollectionEntity(
                name = name,
                color = color
            )
        )
        return AddGroupResult.Success
    }

    override suspend fun removeGroup(groupId: String) {
        val id = groupId.toLongOrNull() ?: return
        val entity = customGroups.value.find { it.id == groupId } ?: return
        collectionDao.delete(
            CollectionEntity(
                id = id,
                name = entity.name,
                color = entity.color
            )
        )
    }

    override suspend fun updateGroup(groupId: String, name: String, color: Long): UpdateGroupResult {
        val id = groupId.toLongOrNull() ?: return UpdateGroupResult.DuplicateName
        val exists = customGroups.value.any {
            it.id != groupId && it.name.equals(name, ignoreCase = true)
        }
        if (exists) return UpdateGroupResult.DuplicateName

        collectionDao.update(
            CollectionEntity(
                id = id,
                name = name,
                color = color
            )
        )
        return UpdateGroupResult.Success
    }

    override suspend fun addIdeaToGroup(ideaId: Long, groupId: String) {
        val id = groupId.toLongOrNull() ?: return
        ideaCollectionDao.insert(IdeaCollectionCrossRef(ideaId, id))
    }

    override suspend fun removeIdeaFromGroup(ideaId: Long, groupId: String) {
        val id = groupId.toLongOrNull() ?: return
        ideaCollectionDao.delete(ideaId, id)
    }

    override suspend fun removeIdeaFromAllGroups(ideaId: Long) {
        ideaCollectionDao.deleteByIdeaId(ideaId)
    }

    override fun getGroupById(groupId: String): Group? =
        customGroups.value.find { it.id == groupId }

    override fun getGroupsForIdea(ideaId: Long): List<Group> =
        customGroups.value.filter { it.ideaIds.contains(ideaId) }
}
