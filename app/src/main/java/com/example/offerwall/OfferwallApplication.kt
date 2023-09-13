package com.example.offerwall

import android.app.Application
import com.example.offerwall.data.AppContainer
import com.example.offerwall.data.AppDataContainer

class OfferwallApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}