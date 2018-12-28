package com.tumoyakov.demonavigation

import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}