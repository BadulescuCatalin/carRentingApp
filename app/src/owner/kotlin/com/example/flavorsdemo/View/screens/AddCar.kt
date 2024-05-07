package com.example.flavorsdemo.View.screens

import ConfirmationDialog
import DropDown
import InfoTitle
import TableRow
import TableRows
import TopBar
import android.widget.Space
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
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
    var officeSelected by remember { mutableStateOf( if (car.officeId == "") "Assign Office" else "Office: " +
            officesGlobal.find { it.id == car.officeId }?.name.toString())}

    val viewModel: CarViewModelOwner = viewModel()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(colorResource(id = R.color.light_blue))
                .padding(top = 8.dp)
        ) {
            Column {
                TopBar(navController = navController, title = "Car Details")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            LazyColumn() {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoTitle(title = "Car Informations", icon = R.drawable.car)
                    Spacer(modifier = Modifier.height(8.dp))
                    TableRows(
                        text = "Brand:",
                        placeholder = "Brand",
                        value = carBrand,
                        onValueChange = {
                            carBrand = it
                            car.brand = it
                        },
                        text2 = "Model:",
                        placeholder2 = "Model",
                        value2 = carModel,
                        onValueChange2 = {
                            carModel = it
                            car.model = it
                        }
                    )
                    TableRows(
                        text = "Year:",
                        placeholder = "Year",
                        value = carYear,
                        onValueChange = {
                            carYear = it
                            car.year = it
                        },
                        text2 = "Color:",
                        placeholder2 = "Color",
                        value2 = carColor,
                        onValueChange2 = {
                            carColor = it
                            car.color = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoTitle(title = "Vehicle Specifications", icon = R.drawable.specifications)
                    Spacer(modifier = Modifier.height(8.dp))
                    TableRows(
                        text = "Type:",
                        placeholder = "Car Type",
                        value = carType,
                        onValueChange = {
                            carType = it
                            car.type = it
                        },
                        text2 = "Range:",
                        placeholder2 = "Range",
                        value2 = carMileage,
                        onValueChange2 = {
                            carMileage = it
                            car.mileage = it
                        }
                    )
                    TableRow(
                        text = "Car Transmission:",
                        placeholder = "Transmission",
                        value = carTransmission,
                        onValueChange = {
                            carTransmission = it
                            car.transmission = it
                        }
                    )
                    TableRow(
                        text = "Number of seats:",
                        placeholder = "Number of seats",
                        value = carNumberOfSeats,
                        onValueChange = {
                            carNumberOfSeats = it
                            car.numberOfSeats = it
                        }
                    )
                    TableRow(
                        text = "Number of doors:",
                        placeholder = "Number of doors",
                        value = carNumberOfDoors,
                        onValueChange = {
                            carNumberOfDoors = it
                            car.numberOfDoors = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoTitle(title = "Fuel & Consumption", icon = R.drawable.gas)
                    Spacer(modifier = Modifier.height(8.dp))
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
                        text = "Urban consumption:",
                        placeholder = "Consumption",
                        value = carUrbanFuelConsumption,
                        onValueChange = {
                            carUrbanFuelConsumption = it
                            car.urbanFuelConsumption = it
                        }
                    )
                    TableRow(
                        text = "Extra urban consumption:",
                        placeholder = "Consumption",
                        value = carExtraUrbanFuelConsumption,
                        onValueChange = {
                            carExtraUrbanFuelConsumption = it
                            car.extraUrbanFuelConsumption = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoTitle(title = "Cost & Description", icon = R.drawable.contract)
                    Spacer(modifier = Modifier.height(8.dp))
                    TableRow(
                        text = "Price per day:",
                        placeholder = "Car cost per day",
                        value = carPrice,
                        onValueChange = {
                            carPrice = it
                            car.price = it
                        }
                    )
                    DropDown(officesGlobal, officeSelected)
                    Spacer(modifier = Modifier.height(4.dp))
                    var offset by remember { mutableStateOf(0f) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(10.dp))
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
                            label = { Text("Enter a description which will be displayed for the clients") },
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
                    },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(id = R.color.light_blue)
                    )
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
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.light_blue)
                        ),
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
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.light_blue)
                        ),
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