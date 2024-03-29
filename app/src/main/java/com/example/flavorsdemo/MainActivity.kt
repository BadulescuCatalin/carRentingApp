package com.example.flavorsdemo

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.View.GetStartedPage
import com.example.flavorsdemo.View.Home
import com.example.flavorsdemo.View.Login
import com.example.flavorsdemo.View.RegisterPage
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.ui.theme.FlavorsDemoTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStarted : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavorsDemoTheme {
                // action bar settings
                WindowCompat.setDecorFitsSystemWindows(
                    window,
                    false
                )
                window.setFlags(
                    WindowManager
                        .LayoutParams
                        .FLAG_LAYOUT_NO_LIMITS,
                    WindowManager
                        .LayoutParams
                        .FLAG_LAYOUT_NO_LIMITS
                )
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme
                        .colorScheme
                        .background
                ) {
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val sharedPreferences =
                        context.getSharedPreferences("AppUser", Context.MODE_PRIVATE)
                    val autologin = sharedPreferences.getBoolean("autologin", false)
                    val sharedViewModel: SharedViewModel = viewModel()
                    val user = Firebase.auth.currentUser
                    var email = user?.email
                    sharedViewModel.fetchUserData(email?:"")
//                    val email = sharedPreferences.getString("email", null)
//                    val password = sharedPreferences.getString("password", null)
//                    val isUserLoggedIn = email != null && password != null
                    NavHost(
                        navController = navController,
                        startDestination = if (autologin) Screen.Home.route else Screen.GetStarted.route
                    ) {
                        composable(Screen.GetStarted.route) {
                            GetStartedPage(navController = navController)
                        }
                        composable(Screen.Login.route) {
                            Login(navController = navController)
                        }
                        composable(Screen.Register.route) {
                            RegisterPage(navController = navController)
                        }
                        composable(Screen.Home.route) {
                            Home(navController = navController, sharedViewModel = sharedViewModel)
                        }
                    }
                }
            }
        }
    }
}


