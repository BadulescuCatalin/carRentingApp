package com.example.flavorsdemo.View.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.User
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen

@Composable
fun GetStartedPage(navController: NavHostController) {
    Image(
        painter = painterResource(id = R.drawable.login_car),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp)
            .padding(horizontal = 8.dp)
            .padding(top = 32.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.GetStarted.route)
                navController.navigate(Screen.Login.route)
            },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_brown)),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.5f)
                .padding(bottom = 54.dp)
        ) {
            Text(
                stringResource(R.string.get_started),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}
