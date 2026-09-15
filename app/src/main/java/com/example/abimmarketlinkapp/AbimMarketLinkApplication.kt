package com.example.abimmarketlinkapp

import android.app.Application
import com.example.abimmarketlinkapp.data.AppContainer
import com.example.abimmarketlinkapp.data.AppDataContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AbimMarketLinkApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
