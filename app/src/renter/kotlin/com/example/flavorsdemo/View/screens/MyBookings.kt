package com.example.flavorsdemo.View.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.Feedback
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.DisplayBooking
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoBar
import com.example.flavorsdemo.View.components.PopupEditField
import com.example.flavorsdemo.View.components.bookingToUpdate
import com.example.flavorsdemo.View.components.carToDisplayAgain
import com.example.flavorsdemo.View.components.office
import com.example.flavorsdemo.View.components.selectedOfficeGlobal
import com.example.flavorsdemo.ViewModel.BookingViewModel
import com.example.flavorsdemo.ViewModel.CarViewModel
import com.example.flavorsdemo.ViewModel.FeedbackViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.example.flavorsdemo.currentUser
import com.google.android.play.integrity.internal.al
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyBooking(navController: NavHostController) {
    var rating by remember { mutableStateOf(0) }
    val bookingViewModel: BookingViewModel = viewModel()
    val feedbackViewModel : FeedbackViewModel = viewModel()
    val carViewModel: CarViewModel = viewModel()
    val cars  = carViewModel.cars.observeAsState(initial = emptyList())
    val officeViewModel : OfficeViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val offices = officeViewModel.offices.observeAsState(initial = emptyList())
    val bookings = bookingViewModel.bookings.observeAsState(initial = emptyList())
    val myBookings by remember { bookings }
    var showFilters by remember { mutableStateOf(false) }
    val myBookingsOfficial = myBookings.filter { it.userId == currentUser.id }
    val bookingsDeletedByOwner = myBookingsOfficial.filter { it.status == "ToBeDeleted" }
    Log.d("TAG2", "My bookings: $bookingsDeletedByOwner")
    val currentDate = LocalDate.now()
    val review = remember { mutableStateOf("") }
    val numberOfStarts = remember { mutableStateOf("0.0") }
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)
    val myClientBookings = myBookingsOfficial.filter { it ->
        it.status != "ToBeDeleted"  &&
                (LocalDate.parse(formattedDate, formatter)
                    .isBefore(LocalDate.parse(it.endDate, formatter)) &&
                        LocalDate.parse(formattedDate, formatter)
                            .isAfter(LocalDate.parse(it.startDate, formatter)) ||
                        LocalDate.parse(formattedDate, formatter)
                            .isEqual(LocalDate.parse(it.endDate, formatter)) ||
                        LocalDate.parse(formattedDate, formatter)
                            .isEqual(LocalDate.parse(it.startDate, formatter)) ||
                        LocalDate.parse(formattedDate, formatter)
                            .isBefore(LocalDate.parse(it.startDate, formatter))

                        )


    }
    val showFeedback by remember { mutableStateOf(false) }
    val expiredBookings = myBookingsOfficial.filter { LocalDate.parse(formattedDate, formatter)
        .isAfter(LocalDate.parse(it.endDate, formatter)) }


//    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//    var finishedBookings = myBookings.filter { LocalDate.parse(it.endDate, formatter) < LocalDate.now() }
//    var unfinishedBookings = myBookings.filter { LocalDate.parse(it.endDate, formatter) >= LocalDate.now() }
    var displayPopup by remember { mutableStateOf(false) }
    var buttonPressed by remember { mutableStateOf("") }
    var btnValue by remember { mutableStateOf("") }
    var currentBooking: Booking = myClientBookings.firstOrNull() ?: Booking()
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
            for (deleteBooking in expiredBookings) {
                val car = cars.value.find { it.id == deleteBooking.carId }
                review.value = ""
                numberOfStarts.value = "0.0"
                AlertDialog(
                    onDismissRequest = { displayPopup = false },
                    modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(16.dp)),
                    containerColor = colorResource(id = R.color.white),
                    title = {
                        if (car != null) {
                            Text(text = "Rate the ride for ${car.brand} ${car.model} from ${deleteBooking.startDate} to ${deleteBooking.endDate}")
                        }
                    },
                    text = {
                        Column {
                            Text("Number of stars")
                            RatingBar(
                                rating = rating,
                                onRatingChanged = { newRating ->
                                    rating = newRating },
                                modifier = Modifier.padding(8.dp)
                            )
                            Text("Leave a review for the ride")
                            TextField(
                                value = review.value,
                                onValueChange = { review.value = it },
                                label = { Text("Review") },
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
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.light_blue)
                            ),
                            onClick = {
                                val feedback = Feedback()
                                feedback.userId = deleteBooking.userId
                                feedback.carId = deleteBooking.carId
                                feedback.feedback = review.value
                                feedback.stars = rating.toString()
                                coroutineScope.launch {
                                    feedbackViewModel.addFeedback(feedback)
                                    bookingViewModel.deleteBooking(deleteBooking.id)
                                }
                            }
                        ) {
                            Text("Ok")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    bookingViewModel.deleteBooking(deleteBooking.id)
                                }
                            }
                        ) {
                            Text("Cancel", color = colorResource(id = R.color.light_blue))
                        }
                    }
                )
            }
            if (bookingsDeletedByOwner.isNotEmpty()) {
                AlertDialog(
                    onDismissRequest = { displayPopup = false },
                    modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(16.dp)),
                    containerColor = colorResource(id = R.color.white),
                    title = { Text(text = "The following booking(s) have been deleted") },
                    text = {
                        Column {
                            for (booking in bookingsDeletedByOwner) {
                                Text(text = "The rental for the ${cars.value.find { it.id == booking.carId }?.brand} ${cars.value.find { it.id == booking.carId }?.model} rented from ${booking.startDate} to ${booking.endDate} because ${booking.reason.lowercase()}")
                            }
                        }
                    },
                    confirmButton = {},
                    dismissButton = {
                        TextButton(
                            onClick = {
                                bookingsDeletedByOwner.forEach {
                                    coroutineScope.launch {
                                        bookingViewModel.deleteBooking(it.id)
                                    }
                                }
                            }
                        ) {
                            Text("Ok", color = colorResource(id = R.color.light_blue))
                        }
                    }
                )
            }
            if (displayPopup) {
                AlertDialog(
                    onDismissRequest = { displayPopup = false },
                    modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(16.dp)),
                    containerColor = colorResource(id = R.color.white),
                    title = { Text(text = "Edit booking") },
                    text = { Text(text = "Are you sure you want to edit this booking?") },
                    confirmButton = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    Color.Red
                                ),
                                onClick = {

                                    displayPopup = false
                                    // corutina si de bagat si punctele inapoi si nr de gps is chestii
                                    val theOffice = offices.value.find { it.id == currentBooking.officeId }
                                    if (theOffice != null) {
                                        val office = theOffice
                                        office.numberOfCameras =
                                            (office.numberOfCameras.toInt() + currentBooking.numberOfCameras).toString()
                                        office.numberOfGps =
                                            (office.numberOfGps.toInt() + if (currentBooking.hasGps) 1 else 0).toString()
                                        office.numberOfChildSeats =
                                            (office.numberOfChildSeats.toInt() + currentBooking.numberOfChildSeats).toString()
                                        office.numberOfAdditionalCarTrunks =
                                            (office.numberOfAdditionalCarTrunks.toInt() + if (currentBooking.hasCargoCarrier) 1 else 0).toString()
                                        officeViewModel.updateOffice(office)
                                    }
                                    currentUser.points += currentBooking.price.split(",")[1].trim().toInt()
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
                                    coroutineScope.launch {
                                        bookingViewModel.deleteBooking(currentBooking.id)
                                    }
                                }
                            ) {

                                Text("Delete")
                            }
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    colorResource(id = R.color.light_blue)
                                ),
                                onClick = {
                                    displayPopup = false
                                    carToDisplayAgain = cars.value.find { it.id == currentBooking.carId }!!
                                    bookingToUpdate = currentBooking
                                    navController.navigate(Screen.PickDateAndTime.route)
                                }
                            ) {

                                Text("Edit")
                            }
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { displayPopup = false }
                        ) {
                            Text("Cancel", color = colorResource(id = R.color.light_blue))
                        }
                    }
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "My Bookings",
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    )

                }
                for (booking in myClientBookings) {
                    item {
                        androidx.compose.material.Card(
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
                                    displayPopup = true
                                    currentBooking = booking
                                }
                            )
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
    }
}

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Int = 32,
    starColor: Color = colorResource(id = R.color.darker_yellow)
) {
    Row(modifier = modifier) {
        for (i in 1..starCount) {
            Icon(
                imageVector = ImageVector.vectorResource(id = if (i <= rating) R.drawable.yellow_star else R.drawable.empty_star),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onRatingChanged(i) }
                    .size(starSize.dp),
                tint = starColor
            )
        }
    }
}