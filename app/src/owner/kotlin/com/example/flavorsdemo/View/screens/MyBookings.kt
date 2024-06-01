package com.example.flavorsdemo.View.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.components.DisplayBooking
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoBar
import com.example.flavorsdemo.ViewModel.BookingViewModel
import com.example.flavorsdemo.currentUser

@Composable
fun MyBooking(navController: NavHostController) {
    val bookingViewModel: BookingViewModel = viewModel()
    val bookings = bookingViewModel.bookings.observeAsState(initial = emptyList())
    val myBookings by remember { bookings}
    var showFilters by remember { mutableStateOf(false) }
    myBookings.filter { it.userId == currentUser.id }
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
                            .fillMaxWidth().padding(start = 16.dp, top = 16.dp)
                    )

                }
                for (booking in myBookings) {
                    item {
                        DisplayBooking(booking = booking)
                    }
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