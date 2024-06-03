package com.example.flavorsdemo.View.screens

import android.widget.Gallery
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.AboutCar
import com.example.flavorsdemo.View.components.CarGallery
import com.example.flavorsdemo.View.components.RatingBar
import com.example.flavorsdemo.View.components.car
import com.example.flavorsdemo.View.components.imageMap
import com.example.flavorsdemo.View.components.infoCarTab

@Composable
fun CarInfo(navController: NavHostController) {
    var infoTab by remember { mutableStateOf(infoCarTab) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.light_grey))
                .clip(
                    RoundedCornerShape(30.dp)
                )
                .padding(2.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back Icon",
                    tint = colorResource(id = R.color.black),
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.white))

                )
                Text(text = "Car Details", fontSize = 24.sp)
                Icon(
                    painter = painterResource(id = R.drawable.empty_heart),
                    contentDescription = "Favorite",
                    modifier = Modifier.size(24.dp)
                )
            }
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(imageMap[car.id])
                    .placeholder(R.drawable.loading).build()
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                RatingBar(
                    rating = 4.5f, modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
                Image(
                    painter = painter,
                    contentDescription = "Car Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .offset(x = (-32).dp, y = (-32).dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-56).dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        infoCarTab = "About"
                        infoTab = "About"
                    },
                    modifier = Modifier,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        Color.Transparent
                    )
                ) {
                    Text(
                        text = "About",
                        color = if (infoTab == "About") colorResource(id = R.color.light_blue) else colorResource(
                            id = R.color.black
                        ),
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = {
                        infoCarTab = "Gallery"
                        infoTab = "Gallery"
                    },
                    modifier = Modifier,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        Color.Transparent
                    )
                ) {
                    Text(
                        text = "Gallery",
                        color = if (infoTab == "Gallery") colorResource(id = R.color.light_blue) else colorResource(
                            id = R.color.black
                        ),
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = {
                        infoCarTab = "Reviews"
                        infoTab = "Reviews"
                    },
                    modifier = Modifier,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        Color.Transparent
                    )
                ) {
                    Text(
                        text = "Reviews",
                        color = if (infoTab == "Reviews") colorResource(id = R.color.light_blue) else colorResource(
                            id = R.color.black
                        ),
                        fontSize = 16.sp
                    )
                }
            }
            when (infoTab) {
                "About" -> {
                    AboutCar()
                }
                "Gallery" -> {
                    CarGallery()
                }
                else -> {
                    Column {
                        androidx.compose.material.Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp)
                                .height(150.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = 4.dp
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)

                            ) {
                                Text("Vlad", fontSize = 16.sp)
                                RatingBar(rating = 5f, modifier = Modifier.padding(top = 8.dp))
                                Text(
                                    "I recently rented a this car for a week-long business trip, and I couldn't be more satisfied with my experience",
                                    fontSize = 16.sp
                                )
                            }
                        }
                        androidx.compose.material.Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp)
                                .height(150.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = 4.dp
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)

                            ) {
                                Text("Catalin", fontSize = 16.sp)
                                RatingBar(rating = 4f, modifier = Modifier.padding(top = 8.dp))
                                Text(
                                    "Me and my family rented the car for a weekend getaway, and overall, it was a great experience",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(colorResource(id = R.color.white))
                .offset(y = (-40).dp)
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.ExtraOptions.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    colorResource(id = R.color.light_blue)
                )

            )
            {
                Text(
                    text = "Rent Car", fontSize = 16.sp,)
            }
        }
    }
}