package com.example.flavorsdemo.View.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.ViewModel.CarViewModel
import com.example.flavorsdemo.ViewModel.RemovedBookingsViewModel

@Composable
fun DisplayBooking(booking: Booking, onClickAction : () -> Unit) {
    val carViewModel: CarViewModel = viewModel()
    val cars by carViewModel.cars.observeAsState(initial = emptyList())
    val myCar = cars.find { it.id == booking.carId }
    val painterRes = rememberAsyncImagePainter(
        model = if (imageMap[booking.carId] != Uri.EMPTY.toString()) {
            imageMap[booking.carId]
        } else {
            R.drawable.hyundai_sonata
        }
    )

    Row(
        modifier = Modifier.fillMaxSize().padding(8.dp).clickable {
            onClickAction()
        },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterRes,
            contentDescription = "Car Image",
            modifier = Modifier
                .height(200.dp)
                .weight(1f)
        )
        Column(
            modifier = Modifier.weight(1f).padding(start = 16.dp)
        ) {
            Text(
                text = myCar?.brand ?: "Unknown",
                fontSize = 16.sp
            )
            Text(
                text = myCar?.model ?: "Unknown",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "Total price:",
                fontSize = 14.sp,
                color = colorResource(id = R.color.black)
            )
            Text(
                text = "${booking.price.split(",")[0]}€",
                fontSize = 14.sp,
                color = colorResource(id = R.color.light_blue)
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Booking Dates",
                fontSize = 16.sp
            )
            Row {
                Column {
                    Text(
                        text = "From",
                        fontSize = 16.sp
                    )
                    Text(
                        text = booking.startDate.split("/").getOrNull(1)
                            ?.let { numberToMonth(it.toInt()) }.toString(),
                        fontSize = 16.sp
                    )
                    Text(
                        text = (booking.startDate.split("/").getOrNull(0)
                            ?.let { String.format("%02d", it.toInt()) }
                            + (" " + booking.startDate.split("/")
                                .getOrNull(2))) ?: "Unknown",
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                Column {
                    Text(
                        text = "To",
                        fontSize = 16.sp
                    )
                    Text(
                        text = booking.endDate.split("/").getOrNull(1)
                            ?.let { numberToMonth(it.toInt()) }.toString(),
                        fontSize = 16.sp
                    )
                    Text(
                        text = (booking.endDate.split("/").getOrNull(0)
                            ?.let { String.format("%02d", it.toInt()) }
                                + (" " + booking.endDate.split("/")
                            .getOrNull(2))) ?: "Unknown",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

fun numberToMonth(number: Int): String {
    val months = arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    return if (number in 1..12) {
        months[number - 1]
    } else {
        "Invalid month"
    }
}