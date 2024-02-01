package com.example.flavorsdemo

import android.app.Application

class RenterLogin : Application() {
    override fun onCreate() {
        super.onCreate()
        FlavorConfig.userType = "Renter"
    }
}
