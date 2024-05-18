package com.example.flavorsdemo.View.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.screens.carImages
import com.example.flavorsdemo.View.screens.office

@Composable
fun TopBarOffice(
    navController: androidx.navigation.NavHostController,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Arrow Back Icon",
            tint = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(start = 4.dp, top = 22.dp, end = 50.dp)
                .size(24.dp)
                .clickable {
                    if (title == "Office Details") {
                        office = Office()
                    }
                    navController.popBackStack()
                }

        )
        Text(
            text = title,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(top = 16.dp, end = 68.dp),
            fontSize = 22.sp
        )
        Text("")
    }
    Divider(
        color = colorResource(id = R.color.light_brown),
        modifier = Modifier
            .padding(top = 4.dp)
    )
    Row(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Button(
            onClick = {
                if (title != "Office Details") {
                    navController.popBackStack()
                    navController.navigate(Screen.AddOffice.route)
                }
            },
            modifier = Modifier
                .padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        )
        {
            Text(
                text = "Office Details",
                color = colorResource(id = R.color.white),
            )

        }
        Divider(
            color = colorResource(id = R.color.light_grey),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Button(
            onClick = {
                if (title != "Map Location") {
                    navController.navigate(Screen.OfficeMap.route)
                }
            },
            modifier = Modifier
                .padding(end = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text(
                text = "Map Location",
                color = colorResource(id = R.color.white)
            )
        }
    }
}