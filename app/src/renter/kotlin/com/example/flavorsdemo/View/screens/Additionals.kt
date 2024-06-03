package com.example.flavorsdemo.View.screens

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
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.DiscountComp
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoBar
import com.example.flavorsdemo.ViewModel.DiscountViewModel
import com.example.flavorsdemo.currentUser

@Composable
fun AddCar(navController: NavHostController) {

}


@Composable
fun AddImages(navController: NavHostController) {

}

@Composable
fun AddOffice(navController: NavHostController) {

}

@Composable
fun OfficeMap(navController: NavHostController) {

}

@Composable
fun AddDiscount(navController: NavHostController) {

}

@Composable
fun Discounts(navController: NavHostController) {
    val discountsViewModel: DiscountViewModel = viewModel()
    val discounts = discountsViewModel.discounts.observeAsState(initial = emptyList())
    val allDiscounts by remember { discounts }
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
                            text = "Available Discounts",
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.black),
                        )
                        Button(
                            onClick = {
                                navController.navigate(Screen.AddDiscount.route)
                            },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            Text(text = "", fontSize = 14.sp, color = Color.Black)
                        }
                    }
                }
                for (discount in allDiscounts) {
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

