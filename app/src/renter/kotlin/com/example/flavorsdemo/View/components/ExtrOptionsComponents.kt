package com.example.flavorsdemo.View.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavorsdemo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun extraDriver(searchValue: String, onValueChange: (String) -> Unit) {
    var value by remember { mutableStateOf(searchValue) }
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(30.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.additional_driver),
                contentDescription = "Driver Image",
                modifier = Modifier
                    .size(120.dp)
                    .offset(y = (-4).dp)
            )
            Column() {
                Text(
                    text = "Add Additional Driver(s)"
                )
                TextField(
                    value = value,
                    onValueChange = { value = it },
                    placeholder = { Text(text = "No. of additional drivers", fontSize = 14.sp) },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = colorResource(id = R.color.light_grey),
                    ),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth(0.9f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "20 euro / person"
                    )
                    Button(
                        onClick = { onValueChange(searchValue) },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .scale(0.8f, 0.8f)
                            .offset(y = (-8).dp, x = 26.dp),
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.light_blue)
                        )
                    ) {
                        Text(
                            text = "Add",
                            modifier = Modifier
                                .padding(4.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddGps(onValueChange: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(30.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.gps),
                contentDescription = "GPS Image",
                modifier = Modifier
                    .size(120.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Add GPS",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "It's recommended",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.offset(y = (-4).dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "15 euro"
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { onValueChange() },
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .scale(0.8f, 0.8f)
                                .offset(y = (-8).dp)
                                .align(Alignment.CenterEnd),
                            colors = ButtonDefaults.buttonColors(
                                colorResource(id = R.color.light_blue)
                            )
                        ) {
                            Text(
                                text = "Add",
                                modifier = Modifier
                                    .padding(4.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}