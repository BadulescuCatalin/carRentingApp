package com.example.flavorsdemo.View.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavorsdemo.R

@Composable
fun MoreScreenComponent(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column (
            modifier = Modifier.padding(horizontal = 16.dp)
        ){
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Go Icon",
                    tint = Color.LightGray,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun InfoTitle(title: String, icon: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(32.dp)
                    .padding(start = 8.dp, top = 6.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray
        )
    }
}

@Composable
fun InfoTitle2(title: String, buttonStr : String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 24.dp, end = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.scale(0.85f).offset(y = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.light_blue)
                )
            ) {
                Text(text = buttonStr)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = Color.LightGray
        )
    }
}