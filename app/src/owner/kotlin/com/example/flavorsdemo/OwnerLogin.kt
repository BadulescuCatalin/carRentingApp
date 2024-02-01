package com.example.flavorsdemo

import android.app.Application

class OwnerLogin : Application() {
    override fun onCreate() {
        super.onCreate()
        FlavorConfig.userType = "Owner"
    }
}
