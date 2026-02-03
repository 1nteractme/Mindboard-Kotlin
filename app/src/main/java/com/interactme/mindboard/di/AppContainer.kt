package com.interactme.mindboard.di

import android.content.Context
import androidx.room.Room
import androidx.core.content.ContextCompat
import com.interactme.mindboard.data.database.AppDatabase
import com.interactme.mindboard.data.database.MIGRATION_2_3
import com.interactme.mindboard.data.database.MIGRATION_3_4
import com.interactme.mindboard.data.repository.IdeaRepositoryImpl
import com.interactme.mindboard.data.repository.RoomGroupRepository
import com.interactme.mindboard.domain.repository.GroupRepository
import com.interactme.mindboard.domain.repository.IdeaRepository
import com.interactme.mindboard.ui.theme.GroupColors
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.interactme.mindboard.R

class AppContainer(context: Context) {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "mindboard_db"
    )
        .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
        .build()

    val ideaRepository: IdeaRepository = IdeaRepositoryImpl(database.ideaDao())
    val groupRepository: GroupRepository = RoomGroupRepository(
        collectionDao = database.collectionDao(),
        ideaCollectionDao = database.ideaCollectionDao(),
        appScope = appScope
    )

    val groupColors = GroupColors(
        systemAll = ContextCompat.getColor(context, R.color.group_system_all).toLong(),
        systemFavorites = ContextCompat.getColor(context, R.color.group_system_favorites).toLong(),
        defaultGroup = ContextCompat.getColor(context, R.color.group_default).toLong(),
    )

    val defaultIdeaColor: Color =
        Color(ContextCompat.getColor(context, R.color.idea_color_1))
}
