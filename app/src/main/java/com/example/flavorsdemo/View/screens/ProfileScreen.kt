package com.example.flavorsdemo.View.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.components.CircularImage
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.PopupEditField
import com.example.flavorsdemo.View.components.ProfileScreenComponent
import com.example.flavorsdemo.currentUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(navController: NavHostController) {
    val country by remember { mutableStateOf(currentUser.country) }
    val firstName by remember { mutableStateOf(currentUser.firstName) }
    val lastName by remember { mutableStateOf(currentUser.lastName) }
    val email by remember { mutableStateOf(currentUser.emailAddress) }
    val phoneNumber by remember { mutableStateOf(currentUser.phoneNumber) }
    var showPopup by remember { mutableStateOf(false) }
    var buttonPressed by remember { mutableStateOf("") }
    var btnValue by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(135.dp)
                            .background(color = colorResource(id = R.color.light_blue))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp, start = 16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularImage(icon = R.drawable.eu)
                                Column() {
                                    Text(
                                        text = currentUser.firstName + " " + currentUser.lastName,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .offset(y = 8.dp),
                                        color = colorResource(id = R.color.white)
                                    )
                                    Button(
                                        onClick = {
                                            Firebase.auth.signOut()
                                            navController.navigate("login")
                                        },
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .clip(shape = RoundedCornerShape(0.dp))
                                            .offset(y = 4.dp)
                                            .background(color = colorResource(id = R.color.light_blue))
                                            .scale(0.9f, 0.9f),
                                        colors = ButtonDefaults.buttonColors(
                                            colorResource(id = R.color.light_blue_darker)
                                        )
                                    ) {
                                        Text(text = "Logout")
                                    }
                                }
                            }
                        }
                    }
                    ProfileScreenComponent("First Name", firstName, onClick = {
                        showPopup = true
                        buttonPressed = "First Name"
                        btnValue = firstName
                    })
                    Divider(
                        color = Color.LightGray
                    )
                    ProfileScreenComponent("Last Name", lastName, onClick = {
                        showPopup = true
                        buttonPressed = "Last Name"
                        btnValue = lastName
                    })
                    Divider(
                        color = Color.LightGray
                    )
                    ProfileScreenComponent("Country", country, onClick = {
                        showPopup = true
                        buttonPressed = "Country"
                        btnValue = country
                    })
                    Divider(
                        color = Color.LightGray
                    )
                    ProfileScreenComponent("Email address", email, onClick = {
                        showPopup = true
                        buttonPressed = "Email address"
                        btnValue = email
                    })
                    Divider(
                        color = Color.LightGray
                    )
                    ProfileScreenComponent("Phone number", phoneNumber, onClick = {
                        showPopup = true
                        buttonPressed = "Phone number"
                        btnValue = phoneNumber
                    })
                    Divider(
                        color = Color.LightGray
                    )
                    ProfileScreenComponent("Driver details", "Edit driver details", onClick = {
                    })
                    if (showPopup) {
                        PopupEditField(
                            buttonPressed,
                            btnValue,
                            onConfirm = {
                            }, onDismiss = {
                                showPopup = false
                            })
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    DownMenuBar(
                        "profile",
                        modifier = Modifier
//                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .shadow(2.dp, spotColor = colorResource(id = R.color.light_blue)),
                        navController = navController
                    )
                }
            }

        }
    }
}