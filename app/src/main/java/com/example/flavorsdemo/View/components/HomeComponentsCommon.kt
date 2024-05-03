package com.example.flavorsdemo.View.components

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.R
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.car
import com.example.flavorsdemo.View.carImages
import com.example.flavorsdemo.View.fromWhere
import com.example.flavorsdemo.View.imageMap
import com.example.flavorsdemo.View.imageMaps


@Composable
fun CarCard(
    thisCar : Car,
    carImageViewModel: CarImageViewModel,
    navController: NavHostController
) {

    val configuration: Configuration = LocalConfiguration.current
    val imageUrls = carImageViewModel.getImageUrls(thisCar.id).observeAsState(initial = listOf())
//    if (!imageMap.contains(thisCar.id)) {
//        imageUrls.value.lastOrNull()?.let { imageMap.put(thisCar.id, it) }
//        if (imageUrls.value.isNotEmpty() && imageUrls.value.size > 1)
//        imageMaps[thisCar.id] = imageUrls.value.subList(0, imageUrls.value.size - 1)
//    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageMap[thisCar.id])
            .placeholder(R.drawable.loading)
            .build()
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = configuration.screenHeightDp.dp / 2.35F)
            .background(color = colorResource(id = R.color.light_grey))
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .clickable {
                if (car.id != thisCar.id)
                    carImages = CarImage(Uri.parse(imageMap[thisCar.id]), imageMaps[thisCar.id]?.map { Uri.parse(it) } ?: listOf())
                car = thisCar
                fromWhere = "carCard"
                navController.navigate(Screen.AddCar.route)
            },

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = configuration.screenHeightDp.dp / 2.35F)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = colorResource(id = R.color.white))


        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(rating = 4.5F)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.empty_heart), // Replace with your favorite icon resource
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                Image(
//                    painter = painterResource(id = R.drawable.hyundai_sonata), // Replace with your car image resource
                    painter = painter,
                    contentDescription = "Car Image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(Color.White),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = thisCar.brand + " " + thisCar.model , fontWeight = FontWeight.Bold)
                    Text(text = thisCar.price + " / day", color = colorResource(id = R.color.dark_brown))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = colorResource(id = R.color.light_grey),
                    thickness = 3.dp,
                    modifier = Modifier.fillMaxWidth(1.2F)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
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
                        Text(text = thisCar.transmission)
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
                        Text(text = thisCar.fuelType)
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
                            text = thisCar.numberOfSeats
                        )
                    }
                }
            }

        }
    }

}

@Composable
fun RatingBar(rating: Float) {
    Row {
        Text(text = "$rating")
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            painter = painterResource(id = R.drawable.yellow_star), // Replace with your petrol icon resource
            contentDescription = "Yellow Star",
            tint = colorResource(id = R.color.darker_yellow),
            modifier = Modifier.size(24.dp)
        )

    }
}