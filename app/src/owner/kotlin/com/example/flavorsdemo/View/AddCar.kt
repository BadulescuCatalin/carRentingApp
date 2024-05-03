package com.example.flavorsdemo.View

import ConfirmationDialog
import TableRow
import TopBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.R
import com.example.flavorsdemo.ViewModel.CarViewModelOwner
import kotlinx.coroutines.launch

var car = Car()
var fromWhere = ""
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCar(navController: NavHostController) {
    var carBrand by remember { mutableStateOf(car.brand) }
    var carModel by remember { mutableStateOf(car.model) }
    var carYear by remember { mutableStateOf(car.year) }
    var carPrice by remember { mutableStateOf(car.price) }
    var carMileage by remember { mutableStateOf(car.mileage) }
    var carColor by remember { mutableStateOf(car.color) }
    var carFuelType by remember { mutableStateOf(car.fuelType) }
    var carTransmission by remember { mutableStateOf(car.transmission) }
    var carDescription by remember { mutableStateOf(car.description) }
    var carNumberOfSeats by remember { mutableStateOf(car.numberOfSeats) }
    var carType by remember { mutableStateOf(car.type) }
    var carNumberOfDoors by remember { mutableStateOf(car.numberOfDoors) }
    var carUrbanFuelConsumption by remember { mutableStateOf(car.urbanFuelConsumption) }
    var carExtraUrbanFuelConsumption by remember { mutableStateOf(car.extraUrbanFuelConsumption) }
    var showConfirmationDialog by remember { mutableStateOf(false) }


    val viewModel: CarViewModelOwner = viewModel()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Box(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(colorResource(id = R.color.dark_brown))
                .padding(top = 8.dp)
        ) {
            Column {
                TopBar(navController = navController, title = "Car Details")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn() {
                item {
                    // Display the selected image

                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                            .background(colorResource(id = R.color.add_car_title_background))
                            .border(
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ),
                    )
                    {
                        Text(
                            text = "Car Informations",
                            fontSize = 16.sp,
                            modifier = androidx.compose.ui.Modifier
                                .padding(8.dp)
                                .align(Alignment.Center),
                            color = colorResource(id = R.color.white)
                        )
                    }
                    TableRow(
                        text = "Brand:",
                        placeholder = "Enter car brand",
                        value = carBrand,
                        onValueChange = {
                            carBrand = it
                            car.brand = it
                        }
                    )
                    TableRow(
                        text = "Model:",
                        placeholder = "Enter car model",
                        value = carModel,
                        onValueChange = {
                            carModel = it
                            car.model = it
                        }
                    )
                    TableRow(
                        text = "Year:",
                        placeholder = "Enter car manufacture year",
                        value = carYear,
                        onValueChange = {
                            carYear = it
                            car.year = it
                        }
                    )
                    TableRow(
                        text = "Color:",
                        placeholder = "Enter car color",
                        value = carColor,
                        onValueChange = {
                            carColor = it
                            car.color = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                            .background(colorResource(id = R.color.add_car_title_background))
                            .border(
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ),
                    )
                    {
                        Text(
                            text = "Vehicle Specifications",
                            fontSize = 16.sp,
                            modifier = androidx.compose.ui.Modifier
                                .padding(8.dp)
                                .align(Alignment.Center),
                            color = colorResource(id = R.color.white)
                        )
                    }
                    TableRow(
                        text = "Type:",
                        placeholder = "Enter car type",
                        value = carType,
                        onValueChange = {
                            carType = it
                            car.type = it
                        }
                    )
                    TableRow(
                        text = "Transmission:",
                        placeholder = "Enter car transmission",
                        value = carTransmission,
                        onValueChange = {
                            carTransmission = it
                            car.transmission = it
                        }
                    )
                    TableRow(
                        text = "Mileage:",
                        placeholder = "Enter car mileage",
                        value = carMileage,
                        onValueChange = {
                            carMileage = it
                            car.mileage = it
                        }
                    )
                    TableRow(
                        text = "Number of seats:",
                        placeholder = "Enter number of seats",
                        value = carNumberOfSeats,
                        onValueChange = {
                            carNumberOfSeats = it
                            car.numberOfSeats = it
                        }
                    )
                    TableRow(
                        text = "Number of doors:",
                        placeholder = "Enter number of doors",
                        value = carNumberOfDoors,
                        onValueChange = {
                            carNumberOfDoors = it
                            car.numberOfDoors = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                            .background(colorResource(id = R.color.add_car_title_background))
                            .border(
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ),
                    )
                    {
                        Text(
                            text = "Fuel & Consumption",
                            fontSize = 16.sp,
                            modifier = androidx.compose.ui.Modifier
                                .padding(8.dp)
                                .align(Alignment.Center),
                            color = colorResource(id = R.color.white)
                        )
                    }
                    TableRow(
                        text = "Fuel Type:",
                        placeholder = "Enter car fuel type",
                        value = carFuelType,
                        onValueChange = {
                            carFuelType = it
                            car.fuelType = it
                        }
                    )
                    TableRow(
                        text = "Urban Consumption:",
                        placeholder = "Urban consumption",
                        value = carUrbanFuelConsumption,
                        onValueChange = {
                            carUrbanFuelConsumption = it
                            car.urbanFuelConsumption = it
                        }
                    )
                    TableRow(
                        text = "Extra Urban Consumption:",
                        placeholder = "Extra urban consumption",
                        value = carExtraUrbanFuelConsumption,
                        onValueChange = {
                            carExtraUrbanFuelConsumption = it
                            car.extraUrbanFuelConsumption = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                            .background(colorResource(id = R.color.add_car_title_background))
                            .border(
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ),
                    )
                    {
                        Text(
                            text = "Cost & Description",
                            fontSize = 16.sp,
                            modifier = androidx.compose.ui.Modifier
                                .padding(8.dp)
                                .align(Alignment.Center),
                            color = colorResource(id = R.color.white)
                        )
                    }
                    TableRow(
                        text = "Price per day:",
                        placeholder = "Enter car price",
                        value = carPrice,
                        onValueChange = {
                            carPrice = it
                            car.price = it
                        }
                    )
                    var offset by remember { mutableStateOf(0f) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .border(
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    )
                    {
                        TextField(
                            value = carDescription,
                            onValueChange = {
                                carDescription = it
                                car.description = it
                            },
                            label = { Text("Enter car description") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = colorResource(id = R.color.white),
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .scrollable(
                                    orientation = Orientation.Vertical,
                                    state = rememberScrollableState { delta ->
                                        offset += delta
                                        delta
                                    }
                                )
                        )
                    }
                    Spacer(modifier = Modifier.height(96.dp))
                }
            }
            if (showConfirmationDialog) {
                ConfirmationDialog(onConfirm = {
                    viewModel.deleteCar(car.id)
                    navController.navigate(Screen.Home.route)
                }, onDismiss = {
                    showConfirmationDialog = false
                })
            }
            if (fromWhere == "") {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(start = 24.dp, end = 12.dp, bottom = 24.dp)
                        .align(Alignment.BottomEnd),
                    onClick = {
                        coroutineScope.launch {
                            viewModel.addCar(car)
                        }

                        navController.navigate(Screen.Home.route)
                    }
                ) {
                    Text(text = "Save")
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                        Button(
                            modifier = Modifier
                                .padding(16.dp)
                                .padding(start = 12.dp, end = 12.dp, bottom = 24.dp),
                            onClick = {
                                showConfirmationDialog = true
                            }
                        ) {
                            Text(text = "Delete")
                        }
                        Button(
                            modifier = Modifier
                                .padding(16.dp)
                                .padding(start = 24.dp, end = 12.dp, bottom = 24.dp),
                            onClick = {
                                viewModel.updateCar(car)
                                navController.navigate(Screen.Home.route)
                            }
                        ) {
                            Text(text = "Save")
                        }
                    }
            }
        }
    }
}