package com.example.flavorsdemo.View.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R

@Composable
fun FilterPage(navController: NavHostController) {
    var sortState by remember { mutableStateOf(filterSortBy) }
    var transmission by remember { mutableStateOf(filterTransmission) }
    var fuel by remember { mutableStateOf(filterFuel) }
    var priceRangeStart by remember { mutableFloatStateOf(filterPriceRangeStart) }
    var priceRangeEnd by remember { mutableFloatStateOf(filterPriceRangeEnd) }
    val priceRange = 0f..500f

    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 24.dp)
    ) {
        Icon(
           imageVector = Icons.Default.ArrowBack,
            contentDescription = "Arrow Back Icon",
            tint = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(24.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Text("Sort by", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy((-12).dp)
        ) {
            SortingOption(label = "None", sortState = sortState, onClick = { sortState = "None" })
            SortingOption(
                label = "Car Name",
                sortState = sortState,
                onClick = { sortState = "Car Name" })
            SortingOption(
                label = "Car Rating",
                sortState = sortState,
                onClick = { sortState = "Car Rating" })
            SortingOption(
                label = "Price Ascending",
                sortState = sortState,
                onClick = { sortState = "Price Ascending" })
            SortingOption(
                label = "Price Descending",
                sortState = sortState,
                onClick = { sortState = "Price Descending" })
        }
        Text("Transmission", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy((-12).dp)
        ) {
            SortingOption(
                label = "All",
                sortState = transmission,
                onClick = { transmission = "All" })
            SortingOption(
                label = "Automatic",
                sortState = transmission,
                onClick = { transmission = "Automatic" })
            SortingOption(
                label = "Manual",
                sortState = transmission,
                onClick = { transmission = "Manual" })
        }
        Text("Fuel", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy((-12).dp)
        ) {
            SortingOption(
                label = "All",
                sortState = fuel,
                onClick = { fuel = "All" })
            SortingOption(
                label = "Petrol",
                sortState = fuel,
                onClick = { fuel = "Petrol" })
            SortingOption(
                label = "Diesel",
                sortState = fuel,
                onClick = { fuel = "Diesel" })
            SortingOption(
                label = "Electric",
                sortState = fuel,
                onClick = { fuel = "Electric" })
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Select Price Range", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray
        )
        RangeSlider(
            value = priceRangeStart..priceRangeEnd,
            onValueChange = { range ->
                priceRangeStart = range.start
                priceRangeEnd = range.endInclusive
            },
            valueRange = priceRange,
            steps = 0,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Range: €${priceRangeStart.toInt()} - €${priceRangeEnd.toInt()}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    sortState = "None"
                    transmission = "All"
                    fuel = "All"
                    priceRangeStart = 0f
                    priceRangeEnd = 500f
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.light_blue)
                )
            ) {
                Text(
                    "Reset Filters", fontSize = 18.sp
                )
            }
            Button(
                onClick = {
                    filterSortBy = sortState
                    filterTransmission = transmission
                    filterFuel = fuel
                    filterPriceRangeStart = priceRangeStart
                    filterPriceRangeEnd = priceRangeEnd
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.light_blue)
                )
            ) {
                Text("Apply Filters", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun SortingOption(label: String, sortState: String, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = sortState == label,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = colorResource(id = R.color.light_blue),
                unselectedColor = colorResource(id = R.color.black)
            )
        )
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}