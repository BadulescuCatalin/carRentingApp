package com.example.flavorsdemo

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flavorsdemo.ui.theme.FlavorsDemoTheme

class GetStarted : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlavorsDemoTheme {
                // action bar settings
                WindowCompat.setDecorFitsSystemWindows(window,
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme
                        .colorScheme
                        .background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController,
                        startDestination = Screen.GetStarted.route
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
                    }
                }
            }
        }
    }
}


