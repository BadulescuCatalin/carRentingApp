package com.example.flavorsdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GetStartedPage(modifier: Modifier = Modifier) {

    Image(
        painter = painterResource(id = R.drawable.login_car),
        contentDescription = null, // Decorative image
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop // Fill the screen, maintaining aspect ratio
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp), // Adjust bottom padding for margin from the bottom edge
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { /* Handle the click here */ },
            shape = RoundedCornerShape(50.dp), // Rounded corners
            // Apply padding for margin around the button and fillMaxWidth for horizontal size
            modifier = Modifier
                .fillMaxWidth(0.5f) // Makes the button fill the maximum width available
                .padding(vertical = 32.dp) // Apply horizontal padding for side margins and vertical padding for top/bottom margins if needed
                .padding(bottom = 16.dp)
        ) {
            Text("Get Started",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(4.dp)) // You can adjust padding inside Text for larger height
        }
    }

}
