package com.example.flavorsdemo.View.components

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.flavorsdemo.FlavorConfig
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

var car = Car()
var infoCarTab = "About"
var fromWhere = ""
var carImages = CarImage()
val user = Firebase.auth.currentUser
var imageMap = mutableMapOf<String, String>()
var imageMapOffice = mutableMapOf<String, String>()
var imageMaps = mutableMapOf<String, List<String>>()
var filterSortBy = "None"
var filterTransmission = "All"
var filterFuel = "All"
var filterPriceRangeStart = 0f
var filterPriceRangeEnd = 500f
var officesGlobal = listOf<Office>()
var selectedOfficeGlobal = Office()
var office = Office()
var officeMainImage: Uri = Uri.EMPTY

@SuppressLint("Range")
@Composable
fun CarCard(
    thisCar: Car,
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
                    carImages = CarImage(
                        Uri.parse(imageMap[thisCar.id]),
                        imageMaps[thisCar.id]?.map { Uri.parse(it) } ?: listOf())
                car = thisCar
                fromWhere = "carCard"
                if (FlavorConfig.userType == "Owner")
                    navController.navigate(Screen.AddCar.route)
                else {
                    car = thisCar
                    infoCarTab = "About"
                    navController.navigate(Screen.CarInfo.route)
                }
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
                    RatingBar(rating = 4.5F, modifier = Modifier)
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
                    Text(text = thisCar.brand + " " + thisCar.model, fontWeight = FontWeight.Bold)
                    Text(
                        text = thisCar.price + " / day",
                        color = colorResource(id = R.color.light_blue)
                    )
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
fun RatingBar(rating: Float, modifier: Modifier) {
    Row (
        modifier = modifier
    ) {
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

@Composable
fun DownMenuBar(
    menuItemName: String,
    modifier: Modifier,
    navController: NavHostController
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
             horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Home Icon",
                tint = colorResource(if (menuItemName == "home") R.color.light_blue else R.color.black),
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.navigate(Screen.Home.route)
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendar Icon",
                tint = colorResource(if (menuItemName == "calendar") R.color.light_blue else R.color.black),
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        // navigation action
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Icon",
                tint = colorResource(if (menuItemName == "profile") R.color.light_blue else R.color.black),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.navigate(Screen.ProfileScreen.route)
                    }
            )
            Icon(
                painter = painterResource(id = if(FlavorConfig.userType == "Owner") R.drawable.discount else R.drawable.empty_heart),
                contentDescription = "Favourites Icon",
                tint = colorResource(if (menuItemName == "favourites") R.color.light_blue else R.color.black),
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        if (FlavorConfig.userType == "Owner") {
//                            navController.navigate(Screen.DiscountScreen.route)
                        } else {
//                            navController.navigate(Screen.FavouritesScreen.route)
                        }
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "More Icon",
                tint = colorResource(if (menuItemName == "more") R.color.light_blue else R.color.black),
                modifier = Modifier
                    .size(26.dp)
                    .clickable {
                        navController.navigate(Screen.MoreScreen.route)
                    }
            )
        }
    }
}

@Composable
fun LoadingImage() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

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
