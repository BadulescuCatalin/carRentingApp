package com.example.flavorsdemo.View.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.screens.fromWhere
import com.example.flavorsdemo.View.screens.imageMapOffice
import com.example.flavorsdemo.View.screens.office
import com.example.flavorsdemo.View.screens.officeMainImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoBar(
    firstName : String,
    lastName : String,
    searchValue : String,
    onValueChange: (String) -> Unit,
    showFilters: Boolean,
    setShowFilters: () -> Unit,
    navController: NavHostController,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .background(colorResource(id = R.color.light_blue))
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
//                    .padding(16.dp)
                .padding(bottom = 8.dp, end = 26.dp, top = 8.dp, start = 4.dp)
                .padding(top = 32.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(text = "$firstName $lastName",
                fontSize = 16.sp,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(start = 36.dp)
            )
            Spacer(modifier = Modifier.weight(0.8f))
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                tint = colorResource(id = R.color.white),
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
                .padding(end = 26.dp)
                .align(Alignment.BottomCenter)
        )
        {
            TextField(
                value = searchValue,
                onValueChange = { onValueChange(it) },
                placeholder = { Text(text = "Search", fontSize = 14.sp) },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.white),
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
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.white)), // Apply tint
                    modifier = Modifier
//                            .background(colorResource(id = R.color.light_brown))
                        .align(Alignment.CenterVertically)
//                            .size(40.dp, 40.dp)
                        //.scale(scaleY = 0.8F, scaleX = 0.8F)
                        .clickable {
                            navController.navigate(Screen.FilterPage.route)

                        }
                )
            }
        }
    }
}

@Composable
fun AddOfficeComp(navController: NavHostController) {
    Image(painter = painterResource(id = R.drawable.add_image),
        contentDescription = "Add Image",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(100.dp)
            .clickable {
                office = Office()
                officeMainImage = Uri.EMPTY
                navController.navigate(Screen.AddOffice.route)
            }
    )
}

@Composable
fun OfficeComponent(navController: NavHostController, thisOffice : Office) {
//    val painterRes = rememberAsyncImagePainter(
//        model = if (imageMapOffice[thisOffice.id] != Uri.EMPTY.toString()) {
//            imageMapOffice[thisOffice.id]
//        } else {
//            R.drawable.company
//        }
//    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp)
                .align(Alignment.Center)
        ) {
            Image(painter = painterResource(id = R.drawable.company),
                contentDescription = "Add Image",
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        office = thisOffice
                        officeMainImage = if (imageMapOffice.contains(thisOffice.id))
                            Uri.parse(imageMapOffice[thisOffice.id])
                        else
                            Uri.EMPTY
                        fromWhere = "OfficeComponent"
                        navController.navigate(Screen.AddOffice.route)
                    }
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = thisOffice.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally))
        }
    }
}