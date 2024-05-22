package com.example.flavorsdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RenterMainActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        FlavorConfig.userType = "Renter"
    }
}
