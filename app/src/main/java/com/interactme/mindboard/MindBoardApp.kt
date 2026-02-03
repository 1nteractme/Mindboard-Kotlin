package com.interactme.mindboard

import android.app.Application
import com.interactme.mindboard.di.AppContainer

class MindBoardApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(applicationContext)
    }
}
