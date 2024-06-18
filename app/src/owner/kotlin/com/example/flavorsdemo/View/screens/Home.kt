package com.example.flavorsdemo.View.screens

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.Model.User
import com.example.flavorsdemo.OwnerMainActivity
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.AddOfficeComp
import com.example.flavorsdemo.View.components.CarCard
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoBar
import com.example.flavorsdemo.View.components.OfficeComponent
import com.example.flavorsdemo.View.components.car
import com.example.flavorsdemo.View.components.carImages
import com.example.flavorsdemo.View.components.discountGlobal
import com.example.flavorsdemo.View.components.filterFuel
import com.example.flavorsdemo.View.components.filterSortBy
import com.example.flavorsdemo.View.components.filterTransmission
import com.example.flavorsdemo.View.components.fromWhere
import com.example.flavorsdemo.View.components.imageMap
import com.example.flavorsdemo.View.components.imageMapOffice
import com.example.flavorsdemo.View.components.imageMaps
import com.example.flavorsdemo.View.components.office
import com.example.flavorsdemo.View.components.officeMainImage
import com.example.flavorsdemo.View.components.officesGlobal
import com.example.flavorsdemo.View.components.user
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import com.example.flavorsdemo.ViewModel.CarViewModel
import com.example.flavorsdemo.ViewModel.DiscountViewModel
import com.example.flavorsdemo.ViewModel.OfficeImageViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.example.flavorsdemo.currentUser
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

lateinit var fusedLocationProviderClient: FusedLocationProviderClient

fun fetchLocation(context: Context, action: (LatLng) -> Unit) {
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            action(LatLng(location.latitude, location.longitude))
        } else {
            Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController,
    sharedViewModel: SharedViewModel = viewModel(),
    carViewModel: CarViewModel = viewModel(),
    carImageViewModel: CarImageViewModel = viewModel(),
    officeViewModel: OfficeViewModel = viewModel(),
    officeImageViewModel: OfficeImageViewModel = viewModel(),
) {

    val context = LocalContext.current
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    fetchLocation(context) { location ->
        office.latitude = location.latitude.toString()
        office.longitude = location.longitude.toString()
    }
    val db = FirebaseFirestore.getInstance()
    var email = user?.email
//    db.collection("users")
//        .whereEqualTo("emailAddress", email)
//        .get()
//        .addOnSuccessListener {
//            for (document in it) {
//                currentUser = document.toObject(User::class.java)
//            }
//        }
    var searchValue by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    val userData by sharedViewModel.userData.collectAsState()
    val cars by carViewModel.cars.observeAsState(initial = emptyList())

    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
    officesGlobal = offices
    val myOffices = offices.filter { it.userId == currentUser.id }
    var officesString = ""
    myOffices.forEach() {
        officesString += it.id + ","
    }
    val discountsViewModel: DiscountViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val discounts = discountsViewModel.discounts.observeAsState(initial = emptyList())
    val discountsToEdit = discounts.value.filter { it.officeId in officesString }
    coroutineScope.launch {
            discountsViewModel.updateDiscountsOfficeIds(discountsToEdit, officesString)
    }
    discountGlobal.officeId = officesString
    var dummyOffice = listOf<String>("", "")
    var showLoading by remember { mutableStateOf(true) }
    val myCars = cars.filter { it -> it.officeId in myOffices.map { it.id } }
    if (ContextCompat.checkSelfPermission(LocalContext.current, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(LocalContext.current as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }
    var carsFiltered by remember { mutableStateOf(myCars) }
    when (filterSortBy) {
        "None" -> carsFiltered = myCars
        "Car Name" -> carsFiltered = myCars.sortedBy { it.brand + it.model }
        "Price Ascending" -> carsFiltered = myCars.sortedBy { it.price.split("€")[0].toDouble() }
        "Price Descending" -> carsFiltered =
            myCars.sortedByDescending { it.price.split("€")[0].toDouble() }

        "Car Rating" -> carsFiltered = myCars // de calculat rating
    }
    when (filterTransmission) {
        "All" -> carsFiltered = carsFiltered
        "Automatic" -> carsFiltered = carsFiltered.filter { it.transmission == "Automatic" }
        "Manual" -> carsFiltered = carsFiltered.filter { it.transmission == "Manual" }
    }
    when (filterFuel) {
        "All" -> carsFiltered = carsFiltered
        "Petrol" -> carsFiltered = carsFiltered.filter { it.fuelType == "Petrol" }
        "Diesel" -> carsFiltered = carsFiltered.filter { it.fuelType == "Diesel" }
        "Electric" -> carsFiltered = carsFiltered.filter { it.fuelType == "Electric" }
    }
    val carMainImages = carImageViewModel.carMainImages.observeAsState(mapOf()).value
    carMainImages.forEach() {
        imageMap[it.key] = it.value
    }

    Log.d("imageMap", imageMap.toString())
    val allCarImages = carImageViewModel.allCarImages.observeAsState(mapOf()).value
    allCarImages.forEach() {
        imageMaps[it.key] = it.value
    }
    val officeMainImages = officeImageViewModel.officeMainImages.observeAsState(mapOf()).value
    officeMainImages.forEach() {
        imageMapOffice[it.key] = it.value
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.light_grey))
        )
        {
            InfoBar(
                firstName = currentUser.firstName ?: "",
                lastName = currentUser.lastName ?: "",
                searchValue = searchValue,
                onValueChange = { searchValue = it },
                showFilters = showFilters,
                setShowFilters = { showFilters = !showFilters },
                navController = navController,
            )
            LazyColumn(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.light_grey))
                    .fillMaxSize()
                    .padding(bottom = 75.dp),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "My Office",
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    top = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                                .background(color = Color.Transparent),
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.black),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                        )
                        Button(
                            onClick = {
                                office = Office()
                                officeMainImage = Uri.EMPTY
                                navController.navigate(Screen.AddOffice.route)
                                fromWhere = ""
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .scale(0.9f)
                        ) {
                            Text(
                                text = "Add Office",
                                color = colorResource(id = R.color.black),
                                fontSize = 14.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
                            )
                        }
                    }
                }
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .padding(start = 8.dp, end = 8.dp)
                            .background(color = colorResource(id = R.color.white))
                    ) {
                        items(myOffices.size) { item ->
                            OfficeComponent(navController, myOffices[item])
                        }
//                        item {
//                            AddOfficeComp(navController = navController)
//                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = "Available cars",
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    top = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                                .background(color = Color.Transparent),
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.black),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                        )
                        Button(
                            onClick = {
                                if (myOffices.isEmpty()) {
                                    Toast.makeText(context, "You need to add an office first", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                navController.navigate(Screen.AddCar.route)
                                fromWhere = ""
                                car = Car()
                                carImages = CarImage()
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .scale(0.9f)
                        ) {
                            Text(
                                text = "Add car",
                                color = colorResource(id = R.color.black),
                                fontSize = 14.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
                            )
                        }
                    }
                }
                items(carsFiltered.size) { index ->
//                    val painter = rememberAsyncImagePainter(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(imageMap[carsFiltered[index].id])
//                            .placeholder(R.drawable.loading)
//                            .apply {
//                                crossfade(true)
//                            }
//                            .build(),
//                    )
                    if (searchValue == "" || carsFiltered[index].brand.contains(searchValue) || carsFiltered[index].model.contains(
                            searchValue
                        )
                    )
                        CarCard(carsFiltered[index], carImageViewModel, navController)
                }
            }
        }
        DownMenuBar(
            "home",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .align(Alignment.BottomCenter)
                .shadow(2.dp, spotColor = colorResource(id = R.color.light_blue)),
            navController = navController
        )
    }

}
