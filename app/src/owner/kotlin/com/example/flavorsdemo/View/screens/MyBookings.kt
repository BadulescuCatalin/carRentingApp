package com.example.flavorsdemo.View.screens

import android.os.Build
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.RemovedBookings
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.components.DisplayBooking
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoBar
import com.example.flavorsdemo.View.components.car
import com.example.flavorsdemo.View.components.countriesList
import com.example.flavorsdemo.View.components.officesGlobal
import com.example.flavorsdemo.ViewModel.BookingViewModel
import com.example.flavorsdemo.ViewModel.CarViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.example.flavorsdemo.ViewModel.RemovedBookingsViewModel
import com.example.flavorsdemo.currentUser
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyBooking(navController: NavHostController) {

    val bookingViewModel: BookingViewModel = viewModel()
//    val carViewModel: CarViewModel = viewModel()
//    val removedBookingsViewModel: RemovedBookingsViewModel = viewModel()
    val officeViewModel: OfficeViewModel = viewModel()
    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
    val myOffices = offices.filter { it.userId == currentUser.id }
    val bookings = bookingViewModel.bookings.observeAsState(initial = emptyList())
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)
    var showPopup by remember { mutableStateOf(false) }

//    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val myCompanyBookings = bookings.value.filter { it ->
        it.status == "Active" && it.officeId in myOffices.map { it.id } &&
                (LocalDate.parse(formattedDate, formatter)
                    .isBefore(LocalDate.parse(it.endDate, formatter)) &&
                        LocalDate.parse(formattedDate, formatter)
                            .isAfter(LocalDate.parse(it.startDate, formatter)) ||
                        LocalDate.parse(formattedDate, formatter)
                            .isEqual(LocalDate.parse(it.endDate, formatter)) ||
                        LocalDate.parse(formattedDate, formatter)
                            .isEqual(LocalDate.parse(it.startDate, formatter)))

    }
    var currentBooking: Booking = myCompanyBookings.firstOrNull() ?: Booking()
    Log.d("MyBooking", myCompanyBookings.toString())
//    val myBookings by remember { mutableStateOf(myCompanyBookings) }
    var showFilters by remember { mutableStateOf(false) }
    var reason = remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InfoBar(
                firstName = currentUser.firstName ?: "",
                lastName = currentUser.lastName ?: "",
                searchValue = "",
                onValueChange = { },
                showFilters = showFilters,
                setShowFilters = { showFilters = !showFilters },
                navController = navController,
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Bookings",
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    )

                }
                for (booking in myCompanyBookings) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .padding(horizontal = 8.dp)
                                .height(150.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = 4.dp
                        ) {
                            DisplayBooking(booking = booking,
                                onClickAction = {
                                    showPopup = true
                                    currentBooking = booking
                                })

                        }
                    }
                }
            }
        }
        DownMenuBar(
            "calendar",
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .align(Alignment.BottomCenter)
                .shadow(2.dp, spotColor = colorResource(id = R.color.light_blue)),
            navController = navController
        )
        if (showPopup) {
            AlertDialog(
                containerColor = colorResource(id = R.color.white),
                onDismissRequest = { },
                title = { Text(text = "Confirm Deletion") },
                text = {
//                    Text(text = "Are you sure you want to delete this car? This action cannot be undone.")
                    Column(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            text = "Are you sure you want to delete this car? This action cannot be undone.",
                            fontSize = 17.sp
                        )
                        TextField(
                            value = reason.value,
                            onValueChange = {
                                reason.value = it
                            },
                            label = {
                                Text(
                                    "Enter the reason for deleting the customer's booking",
                                    color = colorResource(id = R.color.light_blue)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = colorResource(id = R.color.light_grey),
                                disabledTextColor = Color.Black,
                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black,
                                disabledIndicatorColor = Color.Black,

                                ),
                            textStyle = TextStyle.Default.copy(fontSize = 17.sp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (reason.value.isEmpty()) {
                                Toast.makeText(
                                    navController.context,
                                    "Please enter a reason for deleting the booking",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
//                                var myDeletedBooking = RemovedBookings()
//                                myDeletedBooking.id = currentBooking.id
//                                myDeletedBooking.userId = currentUser.id
//                                myDeletedBooking.startDate = currentBooking.startDate
//                                myDeletedBooking.endDate = currentBooking.endDate
//                                val carrr =
//                                    carViewModel.cars.value?.find { it.id == currentBooking.carId }
//                                myDeletedBooking.car = (carrr?.brand + " " + carrr?.model)
//                                myDeletedBooking.description = reason.value
//                                myDeletedBooking.status = "ToBeDeleted"
//                                myDeletedBooking.price = currentBooking.price
//                                myDeletedBooking.hasGps = currentBooking.hasGps
//                                myDeletedBooking.hasCargoCarrier = currentBooking.hasCargoCarrier
//                                myDeletedBooking.numberOfAdditionalDrivers = currentBooking.numberOfAdditionalDrivers
//                                myDeletedBooking.numberOfChildSeats = currentBooking.numberOfChildSeats
//                                myDeletedBooking.numberOfCameras = currentBooking.numberOfCameras
//                                val bookingToUpdate = currentBooking
                                currentBooking.status = "ToBeDeleted"
                                coroutineScope.launch {
                                    bookingViewModel.updateBooking(currentBooking)
                                    showPopup = false
                                }

                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.light_blue)
                        )
                    ) {
                        Text(
                            text = "Delete",

                            )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showPopup = false }
                    ) {
                        Text("Cancel", color = colorResource(id = R.color.light_blue))
                    }
                }
            )

        }
    }
}