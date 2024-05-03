package com.example.flavorsdemo.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.Model.User
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.components.CarCard
import com.example.flavorsdemo.View.components.CustomClickableText
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import com.example.flavorsdemo.ViewModel.CarViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

val user = Firebase.auth.currentUser
var imageMap = mutableMapOf<String, String>()
var imageMaps = mutableMapOf<String, List<String>>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController,
    sharedViewModel: SharedViewModel = viewModel(),
    carViewModel: CarViewModel = viewModel(),
    carImageViewModel: CarImageViewModel = viewModel()
) {

    val db = FirebaseFirestore.getInstance()
    var email = user?.email
    var currentUser by remember { mutableStateOf(User()) }
    var searchValue by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    val userData by sharedViewModel.userData.collectAsState()
    val cars by carViewModel.cars.observeAsState(initial = emptyList())

    val carMainImages = carImageViewModel.carMainImages.observeAsState(mapOf()).value
    carMainImages.forEach() {
        imageMap[it.key] = it.value
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    )
    {
        InfoBar(firstName = userData?.firstName ?: "",
            lastName = userData?.lastName ?: "",
            searchValue = searchValue,
            onValueChange = { searchValue = it },
            showFilters = showFilters,
            setShowFilters = { showFilters = !showFilters },
            navController = navController
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = colorResource(id = R.color.light_grey))
        )
        {
            LazyColumn(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.light_grey))
                    .fillMaxSize()
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
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                                .background(color = Color.Transparent),
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.black),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
                        )
                        CustomClickableText(
                            text = "Add car",
                            onClickAction = {
                                fromWhere = ""
                                car = Car()
                                carImages = CarImage()
                                navController.navigate(Screen.AddCar.route)
                            },
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .background(color = Color.Transparent)

                        )
                    }
                }
                items(cars.size) { index ->
                    if (searchValue == "" || cars[index].brand.contains(searchValue) || cars[index].model.contains(searchValue))
                        CarCard(cars[index], carImageViewModel, navController)
                }
            }
        }
    }
}
