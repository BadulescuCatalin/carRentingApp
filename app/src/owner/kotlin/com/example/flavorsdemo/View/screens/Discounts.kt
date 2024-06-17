package com.example.flavorsdemo.View.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.DiscountComp
import com.example.flavorsdemo.View.components.DisplayBooking
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoBar
import com.example.flavorsdemo.ViewModel.BookingViewModel
import com.example.flavorsdemo.ViewModel.DiscountViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.example.flavorsdemo.currentUser
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Discounts(navController: NavHostController) {
    val discountsViewModel: DiscountViewModel = viewModel()
    val officeViewModel: OfficeViewModel = viewModel()
    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
    val myOffices = offices.filter { it.userId == currentUser.id }
    val discounts = discountsViewModel.discounts.observeAsState(initial = emptyList())
    val discountsToShow = discounts.value.filter { it -> 
        it.officeId.split(",").any { it -> myOffices.map { it.id }.contains(it) } }
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = currentDate.format(formatter)

    val validDiscountsToShow = discountsToShow.filter {
        LocalDate.parse(formattedDate, formatter).isBefore(LocalDate.parse(it.endDate,formatter)) &&
                LocalDate.parse(formattedDate, formatter).isAfter(LocalDate.parse(it.startDate,formatter)) ||
                LocalDate.parse(formattedDate, formatter).isEqual(LocalDate.parse(it.endDate,formatter)) ||
                LocalDate.parse(formattedDate, formatter).isEqual(LocalDate.parse(it.startDate,formatter))
    }
    Log.d("date", validDiscountsToShow.toString())
    val allDiscounts by remember { mutableStateOf(validDiscountsToShow) }
    Log.d("date", allDiscounts.toString())

    var showFilters by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.light_grey))
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Discounts",
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.black),
                        )
                        Button(
                            onClick = {
                                if (myOffices.isNotEmpty()) {
                                    navController.navigate(Screen.AddDiscount.route)
                                } else {
                                    Toast.makeText(
                                        navController.context,
                                        "You need to add an office in order to add a discount",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.White)
                        ) {
                            Text(text = "Add", fontSize = 14.sp, color = Color.Black)
                        }
                    }
                }
                for (discount in validDiscountsToShow) {
                    item {
                        DiscountComp(discount = discount, navController = navController)
                    }
                }
            }
        }
        DownMenuBar(
            "favourites",
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