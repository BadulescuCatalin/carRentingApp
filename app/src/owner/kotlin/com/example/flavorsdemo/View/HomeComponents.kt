package com.example.flavorsdemo.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavorsdemo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBar(
    firstName : String,
    lastName : String,
    searchValue : String,
    onValueChange: (String) -> Unit,
    showFilters: Boolean,
    setShowFilters: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(colorResource(id = R.color.dark_brown))
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
//                    .padding(16.dp)
                .padding(bottom = 16.dp, end = 26.dp, top = 16.dp, start = 4.dp)
                .padding(top = 40.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(text = "${firstName} ${lastName}",
                fontSize = 16.sp,
                color = colorResource(id = R.color.light_brown),
                modifier = Modifier
                    .padding(start = 36.dp)
            )
            Spacer(modifier = Modifier.weight(0.8f))
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                tint = colorResource(id = R.color.light_brown),
                modifier = Modifier
                    .size(36.dp)
                    .padding(start = 8.dp)
                    .clickable {
                        // alta pagina
                    }
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, end = 26.dp)
                .align(Alignment.BottomCenter)
        )
        {

            TextField(
                value = searchValue,
                onValueChange = { onValueChange(it) },
                placeholder = { Text(text = "Search", fontSize = 14.sp) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.light_brown),
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .scale(scaleY = 0.8F, scaleX = 0.8F)
                    .padding(bottom = 12.dp),
//                        .align(Alignment.BottomCenter),
                singleLine = true,
                maxLines = 1
            )
            IconButton(
                onClick = setShowFilters,
                modifier = Modifier
                    .scale(scaleY = 1F, scaleX = 1F)
                    .padding(top = 4.dp)
                    .padding(end = 18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.filter), // Use your drawable resource
                    contentDescription = "Expand Filter List",
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.light_brown)), // Apply tint
                    modifier = Modifier
//                            .background(colorResource(id = R.color.light_brown))
                        .align(Alignment.CenterVertically)
//                            .size(40.dp, 40.dp)
                        //.scale(scaleY = 0.8F, scaleX = 0.8F)
                        .clickable { /* Handle click here
                        */
                        }
                )
            }
        }
    }
}
