package com.example.flavorsdemo.View.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.components.DownMenuBar
import com.example.flavorsdemo.View.components.InfoTitle
import com.example.flavorsdemo.View.components.MoreScreenComponent

@Composable
fun MoreScreen(navController: NavHostController) {
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
                    )
                    {
                        Column(
                            modifier = Modifier.padding(start = 8.dp, top = 48.dp)

                        ) {

                            Text(
                                text = "More",
                                fontSize = 24.sp,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = colorResource(id = R.color.white)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoTitle(title = "General Information", icon = R.drawable.info)
                    Spacer(modifier = Modifier.height(8.dp))
                    MoreScreenComponent(text = "About Vroom")
                    MoreScreenComponent(text = "Privacy Policy")
                    MoreScreenComponent(text = "Terms of Use")
                    MoreScreenComponent(text = "How Vroom points work")
                    MoreScreenComponent(text = "FAQ")
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoTitle(title = "Support", icon = R.drawable.support)
                    Spacer(modifier = Modifier.height(8.dp))
                    MoreScreenComponent(text = "Send Us Feedback")
                    MoreScreenComponent(text = "Rate Us")
                    MoreScreenComponent(text = "Share with Friends")
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoTitle(title = "Legal", icon = R.drawable.license)
                    MoreScreenComponent(text = "Licenses")
                    MoreScreenComponent(text = "Disclaimer")
                    MoreScreenComponent(text = "Cookies Policy")
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = "Version 1.0",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray
                    )
                    DownMenuBar(
                        "more",
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