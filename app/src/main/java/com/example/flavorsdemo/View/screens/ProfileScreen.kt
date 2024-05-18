package com.example.flavorsdemo.View.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.flavorsdemo.currentUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column {
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
//                            Button(
//                                onClick = { /*TODO*/ },
//                                modifier = Modifier
//                                    .align(Alignment.TopEnd)
//                                    .padding(end = 16.dp)
//                                    .clip (shape = RoundedCornerShape(0.dp))
//                                    .offset(y = (-2).dp)
//                                    .background(color = colorResource(id = R.color.light_blue))
//                                    .scale(0.9f, 0.9f)
//                                    .shadow((0.5).dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    Color.Transparent
//                                )
//                            ) {
//                                Text(text = "Logout")
//                            }
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
//                                            Firebase.auth.signOut()
//                                            navController.navigate("login")
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