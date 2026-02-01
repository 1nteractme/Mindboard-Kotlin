package com.interactme.mindboard

import android.app.Application
import androidx.room.Room
import com.interactme.mindboard.data.database.AppDatabase

class MindBoardApp : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mindboard_db"
        ).build()
    }
}