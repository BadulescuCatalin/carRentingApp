package com.example.flavorsdemo.View.screens

import android.app.Activity
import android.content.pm.PackageManager
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.components.CarCard
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoBar
import com.example.flavorsdemo.View.components.filterFuel
import com.example.flavorsdemo.View.components.filterSortBy
import com.example.flavorsdemo.View.components.filterTransmission
import com.example.flavorsdemo.View.components.imageMap
import com.example.flavorsdemo.View.components.imageMapOffice
import com.example.flavorsdemo.View.components.officesGlobal
import com.example.flavorsdemo.View.components.user
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import com.example.flavorsdemo.ViewModel.CarViewModel
import com.example.flavorsdemo.ViewModel.OfficeImageViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.example.flavorsdemo.currentUser
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Home(
    navController: NavHostController,
    sharedViewModel: SharedViewModel = viewModel(),
    carViewModel: CarViewModel = viewModel(),
    carImageViewModel: CarImageViewModel = viewModel(),
    officeViewModel: OfficeViewModel = viewModel(),
    officeImageViewModel: OfficeImageViewModel = viewModel(),
) {
    if (ContextCompat.checkSelfPermission(
            LocalContext.current,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            LocalContext.current as Activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }
    val userData by sharedViewModel.userData.collectAsState()
    val db = FirebaseFirestore.getInstance()
    var searchValue by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    var email = user?.email
    val cars by carViewModel.cars.observeAsState(initial = emptyList())
    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
    officesGlobal = offices
    var carsFiltered by remember { mutableStateOf(cars) }
    var dummyOffice = listOf<String>("", "")
    var showLoading by remember { mutableStateOf(true) }
    when (filterSortBy) {
        "None" -> carsFiltered = cars
        "Car Name" -> carsFiltered = cars.sortedBy { it.brand + it.model }
        "Price Ascending" -> carsFiltered = cars.sortedBy { it.price.split("€")[0].toDouble() }
        "Price Descending" -> carsFiltered =
            cars.sortedByDescending { it.price.split("€")[0].toDouble() }

        "Car Rating" -> carsFiltered = cars // de calculat rating
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
                .background(color = colorResource(id = R.color.white))
        )
        {
            InfoBar(
                firstName = userData?.firstName ?: "",
                lastName = userData?.lastName ?: "",
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
                    }
                }
                items(carsFiltered.size) { index ->
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