package com.example.flavorsdemo.View.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoreScreenComponent(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
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