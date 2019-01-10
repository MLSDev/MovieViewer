package com.shykun.volodymyr.movieviewer.presentation

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.NavigatorHolder



class App : Application() {

    companion object {
        lateinit var instance: App
    }

    private lateinit var cicerone: Cicerone<Router>
    val navigatorHolder = cicerone.navigatorHolder
    val router = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this
        cicerone = Cicerone.create()
    }
}