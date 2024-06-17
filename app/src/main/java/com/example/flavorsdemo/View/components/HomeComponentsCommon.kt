package com.example.flavorsdemo.View.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import android.widget.RatingBar
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.flavorsdemo.FlavorConfig
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.Model.Discount
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import com.example.flavorsdemo.ViewModel.DiscountViewModel
import com.example.flavorsdemo.currentUser
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
var discountValueGlobal = 0.0f
var selectedOfficeGlobal = Office()
var office = Office()
var discountGlobal = Discount()
var dateStart = ""
var dateEnd = ""
var timeStart = ""
var userProfileImage = Uri.EMPTY.toString()
var timeEnd = ""
var officeMainImage: Uri = Uri.EMPTY
val auxiliarComponent = 0
@SuppressLint("Range")
@Composable
fun CarCard(
    thisCar: Car,
    carImageViewModel: CarImageViewModel,
    navController: NavHostController
) {
    userProfileImage = "https://firebasestorage.googleapis.com/v0/b/carrentingapp-5537e.appspot.com/o/default%2Fprofile_image.jpg?alt=media&token=d9ff6858-0f12-48af-a721-d4bcb4ead832"
    carImageViewModel.fetchUserImage(currentUser.id)
    val discountViewModel: DiscountViewModel = viewModel()
    val discounts = discountViewModel.discounts.observeAsState(initial = emptyList())
    val allDiscounts by remember { discounts }
    val validDiscounts = allDiscounts.filter {
        it.discountType.lowercase() == thisCar.fuelType.lowercase() || it.discountType == "All" || thisCar.brand == it.discountType
    }
    var discount = validDiscounts.maxByOrNull { it.discountValue.split("%")[0].toFloat() }
    if (thisCar.fuelType == "Electric" && discount != null && discount.discountValue.split("%")[0].toFloat() < discountValueGlobal)
        discount.discountValue = discountValueGlobal.toString() + "%"
    else if (thisCar.fuelType == "Electric" && (discount == null || discount.discountValue.split("%")[0].toFloat() < discountValueGlobal)) {
        discount = Discount()
        discount.discountValue = discountValueGlobal.toString() + "%"
    }
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
                    if (dateEnd == "")
                        navController.navigate(Screen.WhereTo.route)
                    else
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
                    if (discount != null && discount.discountValue.split("%")[0].toFloat() > 0.0F){
//                        Icon(
//                            painter = painterResource(id = R.drawable.empty_heart), // Replace with your favorite icon resource
//                            contentDescription = "Favorite",
//                            modifier = Modifier
//                                .size(24.dp)
//                        )
                        val discountValue = discount.discountValue
                        Text(
                            text = "-$discountValue", color = Color.White,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .background(Color.Red)
                                .padding(6.dp),
                            fontSize = 15.sp
                        )

                    } else {
                        Text("")
                    }
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
                    Column {
                        if (discount != null && discount.discountValue.split("%")[0].toFloat() > 0.0F) {
                            val discountValue = discount.discountValue.split("%")[0].toFloat()
                            val newPrice =
                                thisCar.price.split("€")[0].toFloat() - (thisCar.price.split("€")[0].toFloat() * discountValue / 100)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End

                            ) {
                                Text(
                                    text = "$newPrice€ / day",
                                    color = Color.Red
                                )
                                Text(
                                    text = thisCar.price + " / day",
                                    color = Color.Gray,
                                    style = TextStyle(textDecoration = TextDecoration.LineThrough),
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .offset(y = 2.dp)
                                )
                            }
                        } else {
                            Text(
                                text = thisCar.price + " / day",
                                color = colorResource(id = R.color.light_blue)
                            )
                        }
                    }
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
    Row(
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
                        navController.navigate(Screen.MyBooking.route)
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
                painter = painterResource(id = R.drawable.discount),
                contentDescription = "Favourites Icon",
                tint = colorResource(if (menuItemName == "favourites") R.color.light_blue else R.color.black),
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        if (FlavorConfig.userType == "Owner") {
                            navController.navigate(Screen.Discounts.route)
                        } else {
                            navController.navigate(Screen.Discounts.route)
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
    firstName: String,
    lastName: String,
    searchValue: String,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                    .padding(16.dp)
                .padding(bottom = 8.dp, end = 26.dp, top = 8.dp, start = 4.dp)
                .padding(top = 32.dp)
                .align(Alignment.TopCenter)
        ) {
            var points = currentUser.points
            Text(
                text = if(FlavorConfig.userType == "Owner") "$firstName $lastName  " else "$firstName $lastName $points" + "p",
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
        Row(
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
fun ecoInfo(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp)
            .background(Color.Transparent)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.eco_co2), contentDescription = null,
                modifier = Modifier.size(25.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "Electric cars are eco-friendly!",
            fontSize = 14.sp,
            style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.eco_green),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )

    }
}

@Composable
fun EcoFriendlyDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Electric Car Benefits") },
        text = {
            Text("Electric cars reduce CO2 emissions significantly compared to diesel and petrol cars, supporting a more sustainable environment. By choosing electric, you help reduce urban pollution and decrease fossil fuel dependency.")
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = colorResource(id = R.color.eco_green))
            ) {
                Text(text = "Understand", color = colorResource(id = R.color.eco_green))
            }
        },
//        properties = DialogProperties(usePlatformDefaultWidth = false),
        containerColor = colorResource(id = R.color.white)
    )
}