package com.example.flavorsdemo.View.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.InfoTitle
import com.example.flavorsdemo.View.components.discountGlobal
import com.example.flavorsdemo.ViewModel.DiscountViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiscount(navController: NavHostController) {
    val context = LocalContext.current
    val toModifiy = (discountGlobal.endDate != "")
    val discountsViewModel: DiscountViewModel = viewModel()
    var description by remember { mutableStateOf(discountGlobal.description) }
    var discountVal by remember { mutableStateOf(discountGlobal.discountValue) }
    var discountType by remember { mutableStateOf(discountGlobal.discountType) }
    var startDate by remember { mutableStateOf(discountGlobal.startDate) }
    var endDate by remember { mutableStateOf(discountGlobal.endDate) }
    val discountTypes =
        listOf("All", "Electric Cars", "Hybrid Cars", "Diesel Cars", "Petrol Cars", "Car Brand")
    val idx = discountTypes.indexOf(discountGlobal.discountType)
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf(discountTypes[if (idx != -1) idx else 0]) }
    var carBrand by remember { mutableStateOf("") }
    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.White, // Background color of the TextField
        disabledTextColor = Color.Black,
        focusedIndicatorColor = Color.Transparent, // No color when focused
        unfocusedIndicatorColor = Color.Transparent, // No color when unfocused
        disabledIndicatorColor = Color.Transparent
    )
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.light_grey))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.light_blue))
                .height(100.dp)
                .padding(top = 24.dp, start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(
                text = "Add Discount",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .padding(vertical = 8.dp)
                .background(color = colorResource(id = R.color.light_grey))
        ) {
            InfoTitle(title = "Discount info", icon = R.drawable.discount)
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(

                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(start = 16.dp)// Background color of the dropdown box
            ) {
                TextField(
                    readOnly = true,
                    value = selectedType,
                    onValueChange = {},
                    label = { Text("Select Type", color = Color.Black) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = textFieldColors
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(colorResource(id = R.color.light_grey))
                        .fillMaxWidth() // Background color of the dropdown menu
                ) {
                    discountTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                val acutalType = if (type == "Electric Cars") "Electric" else
                                    if (type == "Hybrid Cars") "Hybrid" else
                                        if (type == "Diesel Cars") "Diesel" else
                                            if (type == "Petrol Cars") "Petrol" else type
                                selectedType = type
                                discountGlobal.discountType = acutalType
                                expanded = false
                            },
                            modifier = Modifier.background(Color.White) // Background color of dropdown items
                        )
                    }
                }
            }
            if (selectedType == "Car Brand") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Car Brand:")
                    TextField(
                        value = carBrand,
                        onValueChange = {
                            carBrand = it
                            discountGlobal.discountType += " $it"
                        },
                        placeholder = { Text("Car Brand", color = Color.LightGray) },
                        colors = textFieldColors
                    )
                }
            }
            Text(
                "Title",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 4.dp)
            )
            TextField(
                value = description,
                onValueChange = {
                    description = it
                    discountGlobal.description = it
                },
                placeholder = { Text("Title", color = Color.LightGray) },
                colors = textFieldColors,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Discount Value:")
                TextField(
                    value = discountVal,
                    onValueChange = {
                        discountVal = it
                        discountGlobal.discountValue = it
                    },
                    placeholder = { Text("example: 20%", color = Color.LightGray) },
                    colors = textFieldColors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )
            }
        }
        InfoTitle(title = "Discount Period", icon = R.drawable.calendar)
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                androidx.compose.material.Text(text = "First Day", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        val datePickerDialog = DatePickerDialog(
                            context,
                            R.style.CustomDatePickerDialogTheme,
                            { _, year, month, dayOfMonth ->
                                val dday = String.format("%02d", dayOfMonth)
                                val mmonth = String.format("%02d", month + 1)
                                startDate = "$dday/$mmonth/$year"
                                discountGlobal.startDate = startDate
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                        )
                        datePickerDialog.show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.l_gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(color = Color.Transparent)
                ) {
                    androidx.compose.material.Text(
                        text = if (startDate == "") "Pick day" else startDate,
                        color = colorResource(id = R.color.black)
                    )
                }
            }
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                androidx.compose.material.Text(text = "Last Day", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        val datePickerDialog = DatePickerDialog(
                            context,
                            R.style.CustomDatePickerDialogTheme,
                            { _, year, month, dayOfMonth ->
                                val dday = String.format("%02d", dayOfMonth)
                                val mmonth = String.format("%02d", month + 1)
                                endDate = "$dday/$mmonth/$year"
                                discountGlobal.endDate = endDate
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                        )
                        datePickerDialog.show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.l_gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(color = Color.Transparent)
                ) {
                    androidx.compose.material.Text(
                        text = if (endDate == "") "Pick day" else endDate,
                        color = colorResource(id = R.color.black)
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (toModifiy) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            discountsViewModel.deleteDiscount(discountGlobal)
                        }
                        Toast.makeText(context, "Discount deleted", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.Home.route)
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.light_blue)),
                    modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp, bottom = 48.dp)
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (!toModifiy)
                            discountsViewModel.addDiscount(discountGlobal)
                        else
                            discountsViewModel.updateDiscount(discountGlobal)
                    }
                    Toast.makeText(context, "Discount saved", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Home.route)
                },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.light_blue)),
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 16.dp, bottom = 48.dp)
            ) {
                Text(text = "Save", color = Color.White)
            }
        }
    }
}