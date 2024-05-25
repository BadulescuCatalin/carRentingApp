package com.example.flavorsdemo.View.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.flavorsdemo.R

@Composable
fun AboutCar() {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .offset(y = (-56).dp)
            .background(colorResource(id = R.color.white))
            .padding(bottom = 16.dp)

    ) {
        item {
            Column {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = car.type,
                        color = colorResource(id = R.color.light_blue)
                    )
                    Text(
                        text = car.price + " / day",
                        color = colorResource(id = R.color.light_blue)
                    )
                }
                Divider(
                    color = colorResource(id = R.color.light_grey)
                )
            }
        }
        item {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = car.brand + " " + car.model,
                    color = colorResource(id = R.color.black)
                )
                Divider(
                    color = colorResource(id = R.color.light_grey)
                )
            }
        }
        item {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.shifter), // Replace with your manual transmission icon resource
                            contentDescription = "Manual Transmission",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 4.dp)
                        )
                        Text(text = car.transmission)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.gas), // Replace with your petrol icon resource
                            contentDescription = "Petrol Fuel",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 4.dp)
                        )
                        Text(text = car.fuelType)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.car_seat), // Replace with your petrol icon resource
                            contentDescription = "Car Seat",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 2.dp)
                        )
                        Text(
                            text = car.numberOfSeats
                        )
                    }
                }
                Divider(
                    color = colorResource(id = R.color.light_grey)
                )
            }
        }
        item {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = "Description"
                )
                Text(
                    text = car.description,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Divider(
                    color = colorResource(id = R.color.light_grey)
                )
            }
        }
        item {
            Column(
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Consumption"
                )
                BulletPointText(text = "Urban consumption: " + car.urbanFuelConsumption)
                BulletPointText(text = "Extra-urban consumption: " + car.extraUrbanFuelConsumption)
                BulletPointText(text = "Driving range: " + car.mileage)
                Divider(
                    color = colorResource(id = R.color.light_grey)
                )
            }
        }
        item {
            Column(
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Specifications"
                )
                BulletPointText(text = "Air conditioning: yes")
                BulletPointText(text = "Abs: yes")
                BulletPointText(text = "Airbags: yes")
                BulletPointText(text = "Year: " + car.year)
                BulletPointText(text = "Color: " + car.color)
                BulletPointText(text = "Number Of Doors: " + car.numberOfDoors)
                Divider(
                    color = colorResource(id = R.color.light_grey)
                )
            }

        }
    }
}

@Composable
fun BulletPointText(text: String) {
    Column(
        modifier = Modifier.padding(start = 16.dp)
    ) {
        BasicText(
            text = "\u2022 $text",
            style = MaterialTheme.typography.body1.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Composable
fun CarGallery() {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .offset(y = (-56).dp)
            .background(Color.White)
    ) {
        item {
            Column {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "Gallery Icon",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = "Car Photos",
                        color = colorResource(id = R.color.black),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        imageMaps[car.id]?.forEach {imageUri ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .padding(horizontal = 8.dp)
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUri)
                                .placeholder(R.drawable.loading)
                                .build()
                        )
                        Image(
                            painter = painter,
                            contentDescription = "Car Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                }
            }
        }
    }
}
