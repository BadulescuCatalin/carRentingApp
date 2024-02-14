package com.example.flavorsdemo

import android.app.Application

class OwnerMainActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        FlavorConfig.userType = "Owner"
    }
}
