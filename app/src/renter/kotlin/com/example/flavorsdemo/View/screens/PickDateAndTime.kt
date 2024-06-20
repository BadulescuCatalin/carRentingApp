package com.example.flavorsdemo.View.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.example.flavorsdemo.View.components.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.lightColors
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.SearchBarDateAndTime
import com.example.flavorsdemo.View.components.bookingToUpdate
import com.example.flavorsdemo.View.components.car
import com.example.flavorsdemo.View.components.dateEnd
import com.example.flavorsdemo.View.components.dateStart
import com.example.flavorsdemo.View.components.discountGlobal
import com.example.flavorsdemo.View.components.office
import com.example.flavorsdemo.View.components.officesGlobal
import com.example.flavorsdemo.View.components.selectedOfficeGlobal
import com.example.flavorsdemo.View.components.timeEnd
import com.example.flavorsdemo.View.components.timeStart
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.google.android.play.integrity.internal.al
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun PickDateAndTime(navController: NavHostController) {
    val officeViewModel : OfficeViewModel = viewModel()
    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
    var pickUpDate by remember { mutableStateOf(if(bookingToUpdate.officeId != "") bookingToUpdate.startDate else dateStart) }
    var pickUpTime by remember { mutableStateOf(if(bookingToUpdate.officeId != "") bookingToUpdate.startTime else if (timeStart == "")  "10:00" else timeStart) }
    var dropOffDate by remember { mutableStateOf(if(bookingToUpdate.officeId != "") bookingToUpdate.endDate else dateEnd) }
    var dropOffTime by remember { mutableStateOf(if(bookingToUpdate.officeId != "") bookingToUpdate.endTime else if (timeEnd == "")  "10:00" else timeEnd) }
    var showDialogPickUp by remember { mutableStateOf(false) }
    var showDialogDropOff by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val ss = officesGlobal.find { it.id == bookingToUpdate.officeId }
    Log.d("SS", "SS: $ss")
    var selectedOffice by remember { mutableStateOf<Office?>(
        if(bookingToUpdate.officeId != "") ss else null
    ) }
    var searchText by remember { mutableStateOf(if(bookingToUpdate.officeId != "") (
            ss?.name ?: selectedOfficeGlobal.name
    ) else selectedOfficeGlobal.name) }
    Log.d("PickDateAndTime", "T: $searchText")
    Log.d("PickDateAndTime", "O: $offices")
    Log.d("PickDateAndTime", "S: $selectedOffice")
    Log.d("PickDateAndTime", "G: $selectedOfficeGlobal")
    Log.d("PickDateAndTime", "B: ${bookingToUpdate.officeId}")
    Log.d("PickDateAndTime", "N: ${bookingToUpdate.officeId}")
    var showLazylist by remember { mutableStateOf(false) }
    val filteredOffices = if (searchText.isEmpty()) {
        emptyList()
    } else {
        officesGlobal.filter {
            it.name.contains(searchText, ignoreCase = true) ||
                    it.address.contains(searchText, ignoreCase = true) ||
                    it.city.contains(searchText, ignoreCase = true) ||
                    it.zipcode.contains(searchText, ignoreCase = true) ||
                    it.country.contains(searchText, ignoreCase = true)
        }
    }
    if (showDialogPickUp) {
        TimePickerDialog(
            onDismissRequest = { showDialogPickUp = false },
            onTimeSelected = { selectedTime ->
                pickUpTime = selectedTime
                showDialogPickUp = false
            }
        )
    } else if (showDialogDropOff) {
        TimePickerDialog(
            onDismissRequest = { showDialogDropOff = false },
            onTimeSelected = { selectedTime ->
                dropOffTime = selectedTime
                showDialogDropOff = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 48.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .background(colorResource(id = R.color.light_blue))
        ) {
            Column(
                modifier = Modifier.padding(top = 48.dp, start = 16.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back Icon",
                    tint = colorResource(id = R.color.white),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(24.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pick Date and Time",
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.white)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(text = "Location", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            SearchBarDateAndTime(
                text = searchText,
                onValueChange = {
                    searchText = it
                    showLazylist = true
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (searchText.isNotEmpty() && showLazylist) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp).padding(end = 8.dp)
                        .background(Color.White)
                        .height(200.dp)
                ) {
                    itemsIndexed(filteredOffices) { _, suggestion ->
                        Column {
                            androidx.compose.material3.Text(
                                text = suggestion.name,
                                modifier = Modifier
                                    .clickable {
                                        selectedOffice = suggestion
                                        selectedOfficeGlobal = suggestion
                                        searchText = suggestion.name
                                        showLazylist = false
                                    }
                                    .padding(horizontal = 16.dp)
                                    .padding(vertical = 2.dp)
                            )
                            androidx.compose.material3.Text(
                                text = suggestion.address + ", " + suggestion.city + ", " + suggestion.zipcode + ", " + suggestion.country,
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        Divider(
                            color = colorResource(R.color.light_grey),
                            thickness = 2.dp
                        )

                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Select Booking Date and Time",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Pick Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(text = "Pick Up Date")
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
                                    pickUpDate = "$dday/$mmonth/$year"
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                            )
                            datePickerDialog.datePicker.minDate = calendar.timeInMillis
                            if (dropOffDate != "") {
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val maxDate: Date = dateFormat.parse(dropOffDate) ?: Date()
                                datePickerDialog.datePicker.maxDate = maxDate.time
                            } else {
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val maxDate: Date = dateFormat.parse("31/12/2030") ?: Date()
                                datePickerDialog.datePicker.maxDate = maxDate.time
                            }
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
                        val date = "" +Calendar.getInstance().get(Calendar.DAY_OF_MONTH)  + "/" +
                                ((Calendar.getInstance().get(Calendar.MONTH)) + 1) + "/" +
                                Calendar.getInstance().get(Calendar.YEAR)
                        Text(
                            text = "Date: " + pickUpDate.ifEmpty { date },
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
                Column {
                    Text(text = "Pick Up Time")
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            showDialogPickUp = true
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
                        val time = "" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" +
                                String.format("%02d" ,Calendar.getInstance().get(Calendar.MINUTE))
                        Text(
                            text = "Time: " + pickUpTime.ifEmpty { time },
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Drop Off", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(text = "Drop Off Date")
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            if (pickUpDate == "") {
                                Toast.makeText(context, "Please select the first day", Toast.LENGTH_SHORT).show()
                            } else {
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val minDate: Date = dateFormat.parse(pickUpDate) ?: Date()
                                val calendar = Calendar.getInstance()
                                val datePickerDialog = DatePickerDialog(
                                    context,
                                    R.style.CustomDatePickerDialogTheme,
                                    { _, year, month, dayOfMonth ->
                                        val dday = String.format("%02d", dayOfMonth)
                                        val mmonth = String.format("%02d", month + 1)
                                        dropOffDate = "$dday/$mmonth/$year"
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH),
                                )
                                datePickerDialog.datePicker.minDate = minDate.time
                                datePickerDialog.show()
                            }
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
                        val date = "" +Calendar.getInstance().get(Calendar.DAY_OF_MONTH)  + "/" +
                                ((Calendar.getInstance().get(Calendar.MONTH)) + 1) + "/" +
                                Calendar.getInstance().get(Calendar.YEAR)
                        Text(
                            text = "Date: " + dropOffDate.ifEmpty { date },
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
                Column {
                    Text(text = "Drop Off Time")
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            showDialogDropOff = true
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
                        val time = "" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" +
                                String.format("%02d" ,Calendar.getInstance().get(Calendar.MINUTE))
                        Text(
                            text = "Time: " + dropOffTime.ifEmpty { time },
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                androidx.compose.material3.Button(
                    onClick = {
                        if (car.brand == "")
                            navController.navigate(Screen.Home.route)
                        else navController.navigate(Screen.Home.route)
                        dateStart = pickUpDate
                        dateEnd = dropOffDate
                        timeStart = pickUpTime
                        timeEnd = dropOffTime
                        if (selectedOfficeGlobal.name == "")
                            selectedOfficeGlobal = selectedOffice!!
                        Log.d("PickDateAndTime", "PickDateAndTime: $selectedOfficeGlobal")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = 24.dp)
                        .background(colorResource(id = R.color.light_blue))
                        .clip(RoundedCornerShape(5.dp)),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        colorResource(id = R.color.light_blue)
                    )
                ) {
                    Text(
                        text = "Find Cars",
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}

