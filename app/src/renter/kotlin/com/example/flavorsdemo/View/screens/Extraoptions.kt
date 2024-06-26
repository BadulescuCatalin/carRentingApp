package com.example.flavorsdemo.View.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.AddBabySeats
import com.example.flavorsdemo.View.components.AddCarCamera
import com.example.flavorsdemo.View.components.AddCargoCarrier
import com.example.flavorsdemo.View.components.AddGps
import com.example.flavorsdemo.View.components.bookingToUpdate
import com.example.flavorsdemo.View.components.car
import com.example.flavorsdemo.View.components.dateEnd
import com.example.flavorsdemo.View.components.dateStart
import com.example.flavorsdemo.View.components.discountedCarPrice
import com.example.flavorsdemo.View.components.extraDriver
import com.example.flavorsdemo.View.components.selectedOfficeGlobal
import com.example.flavorsdemo.View.components.timeEnd
import com.example.flavorsdemo.View.components.timeStart
import com.example.flavorsdemo.ViewModel.BookingViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.example.flavorsdemo.currentUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExtraOptions(navController: NavHostController) {
    val officeViewModel: OfficeViewModel = viewModel()
    var numberOfAdditionalDrivers by remember { mutableStateOf("") }
    var hasGps by remember { mutableStateOf(false) }
    var numberOfBabySeats by remember { mutableStateOf("") }
    var numberOfAdditionalCarCameras by remember { mutableStateOf("") }
    var hasCargoCarrier by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) } // State to control dialog visibility
    var showDialogPoints by remember { mutableStateOf(false) } // State to control dialog visibility
    var totalPrice by remember { mutableStateOf(0f) }
    var discountedPrice by remember { mutableStateOf(-1.0f) }
    // Function to handle paying online
    val context = LocalContext.current
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val cardNumber = remember { mutableStateOf("") }
    val expirationDate = remember { mutableStateOf("") }
    val cvv = remember { mutableStateOf("") }
    var wantsDisocunt = false
    val bookingViewModel: BookingViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    fun onPayOnline() {
        val url = "https://www.stripe.com" // Replace with your actual payment URL
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        context.startActivity(intent)
    }

    var showOnlinePaymentDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_grey))
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.light_grey))
                .clip(RoundedCornerShape(30.dp))
                .padding(2.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back Icon",
                    tint = colorResource(id = R.color.black),
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                        .clip(CircleShape)
                        .background(colorResource(id = R.color.white))
                )
                Text(
                    text = "Add Extra Options",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 56.dp)
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    item {
                        extraDriver(searchValue = numberOfAdditionalDrivers, onValueChange = {
                            numberOfAdditionalDrivers = it
                        })
                        Divider(
                            color = colorResource(id = R.color.light_grey),
                            thickness = 2.dp,
                        )
                    }
                    item {
                        AddGps(
                            onAddClick = {
                                hasGps = true
                            },
                            onRemoveClick = {
                                hasGps = false
                            },
                        )
                        Divider(
                            color = colorResource(id = R.color.light_grey),
                            thickness = 2.dp,
                        )
                    }
                    item {
                        AddBabySeats(
                            searchValue = numberOfBabySeats,
                            onValueChange = {
                                numberOfBabySeats = it
                            },
                        )
                        Divider(
                            color = colorResource(id = R.color.light_grey),
                            thickness = 2.dp,
                        )
                    }
                    item {
                        AddCarCamera(
                            searchValue = numberOfAdditionalCarCameras,
                            onValueChange = {
                                numberOfAdditionalCarCameras = it
                            },
                        )
                        Divider(
                            color = colorResource(id = R.color.light_grey),
                            thickness = 2.dp,
                        )
                    }
                    item {
                        AddCargoCarrier(
                            onAddClick = {
                                hasCargoCarrier = true
                            },
                            onRemoveClick = {
                                hasCargoCarrier = false
                            },
                        )
                        Divider(
                            color = colorResource(id = R.color.light_grey),
                            thickness = 2.dp,
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    colorResource(id = R.color.light_grey)
                                )
                            )
                        )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 40.dp)
                    .background(colorResource(id = R.color.white))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp),
                ) {
//                    val dayStart = dateStart.split("/")[0]
//                    val dayEnd = dateEnd.split("/")[0]
//                    val daysBetween = dayEnd.toInt() - dayStart.toInt() + 1
                    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                    val startDate = LocalDate.parse(dateStart, dateFormat)
                    val endDate = LocalDate.parse(dateEnd, dateFormat)

                    // Calculate the number of days between the two dates
                    val daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1
                    val carCost = discountedCarPrice.split("€")[0].toDouble() * daysBetween
                    var additionalCost = 0
                    additionalCost += if (numberOfAdditionalCarCameras != "") numberOfAdditionalCarCameras.toInt() * 10 else 0
                    additionalCost += if (numberOfBabySeats != "") numberOfBabySeats.toInt() * 15 else 0
                    additionalCost += if (numberOfAdditionalDrivers != "") numberOfAdditionalDrivers.toInt() * 20 else 0
                    additionalCost += if (hasGps) 20 else 0
                    additionalCost += if (hasCargoCarrier) 10 else 0
                    totalPrice = (carCost + additionalCost.toDouble()).toFloat()
                    Text(
                        text = "Car Cost: $carCost€",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Additional Cost: $additionalCost€",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Divider(
                        color = colorResource(id = R.color.light_grey),
                        thickness = 2.dp,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Cost: ${carCost + additionalCost}€",
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Button(
                            onClick = {
                                if (car.fuelType == "Electric" && currentUser.points > 0)
                                    showDialogPoints = true
                                else
                                    showDialog = true

                            }, // Show dialog when button is clicked
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.light_blue)
                            )
                        ) {
                            Text(text = "Continue", color = Color.White)
                        }
                    }
                }
            }
        }
    }
    if (showDialogPoints) {
        AlertDialog(
            containerColor = colorResource(id = R.color.white),
            onDismissRequest = { showDialog = false },
            modifier = Modifier
                .background(Color.Transparent)
                .clip(RoundedCornerShape(16.dp)),
            title = { Text(text = "Use points for discount", fontSize = 24.sp) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.light_grey))
                            .clickable { /* Handle click */ }
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(text = "Get Discount", fontSize = 18.sp)
                            val discount = currentUser.points / 10
                            val newPrice = totalPrice - discount
                            Text(
                                text = "10 points = 1€. Get $discount€ discount for this booking. The new price will be:",
                                fontSize = 14.sp
                            )
                            Text(
                                text = "$newPrice€",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val discount = currentUser.points / 10
                        val newPrice = totalPrice - discount
                        totalPrice = newPrice
                        discountedPrice = newPrice
                        showDialog = true
                        showDialogPoints = false
                        wantsDisocunt = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(id = R.color.light_blue)
                    ),
                ) {
                    Text(text = "Select", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = true
                        showDialogPoints = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(id = R.color.light_blue)
                    ),
                ) {
                    Text(text = "Close", color = Color.White)
                }
            }
        )
    }
    if (showOnlinePaymentDialog) {
        AlertDialog(
            onDismissRequest = {
                showOnlinePaymentDialog = false
                showDialog = true
            },
            modifier = Modifier
                .background(Color.White)
                .clip(RoundedCornerShape(16.dp)),
            containerColor = colorResource(id = R.color.white),
            title = { Text(text = "Card Details") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Text("First Name")


                    TextField(
                        value = firstName.value,
                        onValueChange = {
                            firstName.value = it
                        },
                        placeholder = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.light_grey),
                            disabledTextColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Black,

                            )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Last Name")
                    TextField(
                        value = lastName.value,
                        onValueChange = {
                            lastName.value = it
                        },
                        placeholder = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.light_grey),
                            disabledTextColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Black,

                            )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Card number")
                    TextField(
                        value = cardNumber.value,
                        onValueChange = {
                            cardNumber.value = it
                        },
                        placeholder = { Text("Card Number") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.light_grey),
                            disabledTextColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Black,

                            )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Expiration Date")
                    TextField(
                        value = expirationDate.value,
                        onValueChange = {
                            expirationDate.value = it
                        },
                        placeholder = { Text("Expiration Date") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.light_grey),
                            disabledTextColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Black,

                            )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("CVV")
                    TextField(
                        value = cvv.value,
                        onValueChange = {
                            cvv.value = it
                        },
                        placeholder = { Text("CVV") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.light_grey),
                            disabledTextColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Black,

                            )
                    )
                }

            },
            confirmButton = {
                androidx.compose.material3.Button(
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        colorResource(id = R.color.light_blue)
                    ),
                    onClick = {
                        if (firstName.value == "" || lastName.value == "" || cardNumber.value == "" || expirationDate.value == "" || cvv.value == "") {
                            Toast.makeText(
                                context,
                                "Please complete the card details",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            // de scazunt nr gps si chestii
                            val booking = bookingToUpdate
                            booking.price =
                                (if (discountedPrice == -1.0f) "$totalPrice, 0" else discountedPrice.toString()) + ", " + (currentUser.points).toString()
                            booking.endDate = dateEnd
                            booking.endTime = timeEnd
                            booking.numberOfAdditionalDrivers =
                                if (numberOfAdditionalDrivers == "") 0 else numberOfAdditionalDrivers.toInt()
                            booking.numberOfChildSeats =
                                if (numberOfBabySeats == "") 0 else numberOfBabySeats.toInt()
                            booking.numberOfCameras =
                                if (numberOfAdditionalCarCameras == "") 0 else numberOfAdditionalCarCameras.toInt()
                            booking.hasGps = hasGps
                            booking.hasCargoCarrier = hasCargoCarrier
                            booking.userId = currentUser.id
                            booking.carId = car.id
                            booking.officeId = car.officeId
                            booking.endTime = timeEnd
                            booking.startDate = dateStart
                            booking.startTime = timeStart
//                            val dayStart = dateStart.split("/")[0]
//                            val dayEnd = dateEnd.split("/")[0]
//                            val daysBetween = dayEnd.toInt() - dayStart.toInt() + 1
                            val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                            // Parse the input dates
                            val startDate = LocalDate.parse(dateStart, dateFormat)
                            val endDate = LocalDate.parse(dateEnd, dateFormat)

                            // Calculate the number of days between the two dates
                            val daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1

                            val pointsEarned =
                                (daysBetween * 5.0f + totalPrice / 20).toInt() // 5 puncte pe zi + 5% din pret
//                            currentUser.points += pointsEarned
                            if (discountedPrice != -1.0f) {
                                currentUser.points = 0
                                val db = FirebaseFirestore.getInstance()
                                db.collection("users")
                                    .document(currentUser.id)
                                    .set(currentUser)
                                    .addOnSuccessListener {
                                    }
                                    .addOnFailureListener { e ->
                                        Log.d(
                                            "TAG",
                                            "Error adding user in the database",
                                            e
                                        )
                                    }
                            }
                            val officeToUpdate = selectedOfficeGlobal
                            officeToUpdate.numberOfGps =
                                (officeToUpdate.numberOfGps.toInt() - if (hasGps) 1 else 0).toString()
                            officeToUpdate.numberOfChildSeats =
                                (officeToUpdate.numberOfChildSeats.toInt() - if (numberOfBabySeats != "") numberOfBabySeats.toInt() else 0).toString()
                            officeToUpdate.numberOfCameras =
                                (officeToUpdate.numberOfCameras.toInt() - if (numberOfAdditionalCarCameras != "") numberOfAdditionalCarCameras.toInt() else 0).toString()
                            officeToUpdate.numberOfAdditionalCarTrunks = (officeToUpdate.numberOfAdditionalCarTrunks.toInt() - if (hasCargoCarrier) 1 else 0).toString()
                            coroutineScope.launch {
                                officeViewModel.updateOffice(officeToUpdate)
                            }

                            // save to firestore + mvvm
                            coroutineScope.launch {
                                if (bookingToUpdate.officeId == "")
                                    bookingViewModel.addBooking(booking)
                                else bookingViewModel.updateBooking(booking)
                            }
                            showDialog = false
                            showOnlinePaymentDialog = false
                            Toast.makeText(context, "Booking added", Toast.LENGTH_SHORT)
                                .show()
                            navController.navigate(Screen.Home.route)
                        }
                    }
                ) {

                    Text("Pay")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showOnlinePaymentDialog = false
                        showDialog = true
                    }
                ) {
                    Text("Cancel", color = colorResource(id = R.color.light_blue))
                }
            }
        )
    }
    if (showDialog) {
        AlertDialog(
            containerColor = colorResource(id = R.color.white),
            onDismissRequest = { showDialog = false },
            modifier = Modifier
                .background(Color.Transparent)
                .clip(RoundedCornerShape(16.dp)),
            title = { Text(text = "Choose payment method", fontSize = 24.sp) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.light_grey))
                            .clickable { /* Handle click */ }
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(text = "Pay online", fontSize = 18.sp)
                            Text(
                                text = "Your credit card will be debited once you finish your booking.",
                                fontSize = 14.sp
                            )
                            Text(
                                text = if (discountedPrice == -1.0f) "$totalPrice€" else "$discountedPrice€",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = {
                                    showOnlinePaymentDialog = true
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    colorResource(id = R.color.light_blue)
                                ),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(text = "Select", color = Color.White)
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(id = R.color.light_grey))
                            .clickable { /* Handle click */ }
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(text = "Pay at desk", fontSize = 18.sp)
                            Text(
                                text = "You will pay at the counter the day of the pick up.",
                                fontSize = 14.sp
                            )
                            Text(
                                text = if (discountedPrice == -1.0f) "$totalPrice€" else "$discountedPrice€",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = {
                                    val booking = bookingToUpdate
                                    booking.price =
                                        (if (discountedPrice == -1.0f) "$totalPrice, 0" else discountedPrice.toString()) + ", " + (currentUser.points).toString()
                                    booking.endDate = dateEnd
                                    booking.endTime = timeEnd
                                    booking.numberOfAdditionalDrivers =
                                        if (numberOfAdditionalDrivers == "") 0 else numberOfAdditionalDrivers.toInt()
                                    booking.numberOfChildSeats =
                                        if (numberOfBabySeats == "") 0 else numberOfBabySeats.toInt()
                                    booking.numberOfCameras =
                                        if (numberOfAdditionalCarCameras == "") 0 else numberOfAdditionalCarCameras.toInt()
                                    booking.hasGps = hasGps
                                    booking.hasCargoCarrier = hasCargoCarrier
                                    booking.userId = currentUser.id
                                    booking.carId = car.id
                                    booking.officeId = car.officeId
                                    booking.endTime = timeEnd
                                    booking.startDate = dateStart
                                    booking.startTime = timeStart
//                                    val dayStart = dateStart.split("/")[0]
//                                    val dayEnd = dateEnd.split("/")[0]
//                                    val daysBetween = dayEnd.toInt() - dayStart.toInt() + 1
                                    val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                                    // Parse the input dates
                                    val startDate = LocalDate.parse(dateStart, dateFormat)
                                    val endDate = LocalDate.parse(dateEnd, dateFormat)
                                    val officeToUpdate = selectedOfficeGlobal
                                    officeToUpdate.numberOfGps =
                                        (officeToUpdate.numberOfGps.toInt() - if (hasGps) 1 else 0).toString()
                                    officeToUpdate.numberOfChildSeats =
                                        (officeToUpdate.numberOfChildSeats.toInt() - if (numberOfBabySeats != "") numberOfBabySeats.toInt() else 0).toString()
                                    officeToUpdate.numberOfCameras =
                                        (officeToUpdate.numberOfCameras.toInt() - if (numberOfAdditionalCarCameras != "") numberOfAdditionalCarCameras.toInt() else 0).toString()
                                    officeToUpdate.numberOfAdditionalCarTrunks = (officeToUpdate.numberOfAdditionalCarTrunks.toInt() - if (hasCargoCarrier) 1 else 0).toString()
                                    coroutineScope.launch {
                                        officeViewModel.updateOffice(officeToUpdate)
                                    }
                                    // Calculate the number of days between the two dates
                                    val daysBetween =
                                        ChronoUnit.DAYS.between(startDate, endDate) + 1
                                    val pointsEarned =
                                        (daysBetween * 5.0f + totalPrice / 20).toInt() // 5 puncte pe zi + 5% din pret
                                    if (discountedPrice != -1.0f) {
                                        currentUser.points = 0
//                                    currentUser.points += pointsEarned
                                        val db = FirebaseFirestore.getInstance()
                                        db.collection("users")
                                            .document(currentUser.id)
                                            .set(currentUser)
                                            .addOnSuccessListener {
                                            }
                                            .addOnFailureListener { e ->
                                                Log.d(
                                                    "TAG",
                                                    "Error adding user in the database",
                                                    e
                                                )
                                            }
                                    }
                                    // save to firestore + mvvm
                                    coroutineScope.launch {
                                        if (bookingToUpdate.officeId == "")
                                            bookingViewModel.addBooking(booking)
                                        else bookingViewModel.updateBooking(booking)
                                    }
                                    showDialog = false
                                    Toast.makeText(context, "Booking added", Toast.LENGTH_SHORT)
                                        .show()
                                    navController.navigate(Screen.Home.route)

                                },
                                colors = ButtonDefaults.buttonColors(
                                    colorResource(id = R.color.light_blue)
                                ),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(text = "Select", color = Color.White)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(id = R.color.light_blue)
                    ),
                ) {
                    Text(text = "Close", color = Color.White)
                }
            }
        )
    }
}


