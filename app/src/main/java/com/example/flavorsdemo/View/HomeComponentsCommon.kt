package com.example.flavorsdemo.View

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.flavorsdemo.R


@Composable
fun CarCard() {
    val configuration: Configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = configuration.screenHeightDp.dp / 2.3F)
            .background(color = colorResource(id = R.color.light_grey))
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = configuration.screenHeightDp.dp / 2.3F)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = colorResource(id = R.color.white))


        ) {
            Text(text = "Car Card")
        }
    }

}
