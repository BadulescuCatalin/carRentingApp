package com.example.flavorsdemo.View.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Discount
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen

@Composable
fun DiscountComp(discount: Discount, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(horizontal = 8.dp)
            .height(100.dp)
            .clickable{
                      discountGlobal = discount
                        navController.navigate(Screen.AddDiscount.route)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Image(
                painter = if (discount.discountType == "Electric Cars") painterResource(id = R.drawable.electric_car)
                else if(discount.discountType == "Hybrid Cars") painterResource(id = R.drawable.hybrid_car) else painterResource(id = R.drawable.car),
                contentDescription = "Car Image",
                modifier = Modifier.height(70.dp).padding(start = 16.dp)
            )
            Column() {
                Text(text = discount.description, modifier = Modifier.padding(start = 16.dp),
                    fontSize = 20.sp, color = androidx.compose.ui.graphics.Color.Black, )
                Text(text = discount.discountValue.toString() + " off", modifier = Modifier.padding(start = 16.dp),
                    fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Black)
            }
        }

    }
}