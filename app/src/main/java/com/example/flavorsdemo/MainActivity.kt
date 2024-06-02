package com.example.flavorsdemo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import com.example.flavorsdemo.Model.User
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.screens.AddCar
import com.example.flavorsdemo.View.screens.AddDiscount
import com.example.flavorsdemo.View.screens.AddImages
import com.example.flavorsdemo.View.screens.AddOffice
import com.example.flavorsdemo.View.screens.CarInfo
import com.example.flavorsdemo.View.screens.Discounts
import com.example.flavorsdemo.View.screens.ExtraOptions
import com.example.flavorsdemo.View.screens.FilterPage
import com.example.flavorsdemo.View.screens.GetStartedPage
import com.example.flavorsdemo.View.screens.Home
import com.example.flavorsdemo.View.screens.Login
import com.example.flavorsdemo.View.screens.MoreScreen
import com.example.flavorsdemo.View.screens.MyBooking
import com.example.flavorsdemo.View.screens.OfficeMap
import com.example.flavorsdemo.View.screens.PickDateAndTime
import com.example.flavorsdemo.View.screens.ProfileScreen
import com.example.flavorsdemo.View.screens.RegisterPage
import com.example.flavorsdemo.View.screens.WhereTo
import com.example.flavorsdemo.ui.theme.FlavorsDemoTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

var currentUser = User()

@AndroidEntryPoint
class GetStarted : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
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
//                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
//                    }
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val sharedPreferences =
                        context.getSharedPreferences("AppUser", Context.MODE_PRIVATE)
                    val autologin = sharedPreferences.getBoolean("autologin", false)
                    val sharedViewModel: SharedViewModel = viewModel()
                    val user = Firebase.auth.currentUser
                    var email = user?.email
                    sharedViewModel.fetchUserData(email ?: "")
                    val db = FirebaseFirestore.getInstance()
                    db.collection("users")
                        .whereEqualTo("emailAddress", email)
                        .get()
                        .addOnSuccessListener {
                            for (document in it) {
                                currentUser = document.toObject(User::class.java)
                            }
                        }
//                    val email = sharedPreferences.getString("email", null)
//                    val password = sharedPreferences.getString("password", null)
//                    val isUserLoggedIn = email != null && password != null
                    NavHost(
                        navController = navController,
                        startDestination =
                        if (autologin && FlavorConfig.userType == "Owner") Screen.Home.route
                        else if (!autologin) Screen.GetStarted.route
                        else Screen.WhereTo.route
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
                        composable(Screen.AddCar.route) {
                            AddCar(navController = navController)
                        }
                        composable(Screen.AddImages.route) {
                            AddImages(navController = navController)
                        }
                        composable(Screen.FilterPage.route) {
                            FilterPage(navController = navController)
                        }
                        composable(Screen.AddOffice.route) {
                            AddOffice(navController = navController)
                        }
                        composable(Screen.OfficeMap.route) {
                            OfficeMap(navController = navController)
                        }
                        composable(Screen.MoreScreen.route) {
                            MoreScreen(navController = navController)
                        }
                        composable(Screen.ProfileScreen.route) {
                            ProfileScreen(navController = navController)
                        }
                        composable(Screen.WhereTo.route) {
                            WhereTo(navController = navController)
                        }
                        composable(Screen.PickDateAndTime.route) {
                            PickDateAndTime(navController = navController)
                        }
                        composable(Screen.CarInfo.route) {
                            CarInfo(navController = navController)
                        }
                        composable(Screen.ExtraOptions.route) {
                            ExtraOptions(navController = navController)
                        }
                        composable(Screen.MyBooking.route) {
                            MyBooking(navController = navController)
                        }
                        composable(Screen.Discounts.route) {
                            Discounts(navController = navController)
                        }
                        composable(Screen.AddDiscount.route) {
                            AddDiscount(navController = navController)
                        }
                    }
                }
            }
        }
    }
}


