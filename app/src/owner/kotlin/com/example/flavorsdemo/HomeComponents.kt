package com.example.flavorsdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun InfoBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(colorResource(id = R.color.dark_brown))
    )
}

@Composable
fun UserProfileScreen(sharedViewModel: SharedViewModel) {
    // Example email, replace with actual email obtained through your app logic
    val userEmail = "user@example.com"

    // Fetch user data when the composable enters composition
    LaunchedEffect(userEmail) {
        sharedViewModel.fetchUserData(userEmail)
    }

    val userData = sharedViewModel.userData.collectAsState().value

    userData?.let { user ->
        // Now use userData to display user details
        Text(text = "Welcome ${user.firstName} ${user.lastName}")
    }
}