package com.example.flavorsdemo

sealed class Screen(val route : String) {
    data object GetStarted : Screen("get_started")
    data object Login : Screen("login")
    data object Register : Screen("register")
}