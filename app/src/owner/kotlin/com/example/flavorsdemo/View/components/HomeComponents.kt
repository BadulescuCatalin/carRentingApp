package com.example.flavorsdemo.View.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen


@Composable
fun AddOfficeComp(navController: NavHostController) {
    Image(painter = painterResource(id = R.drawable.add_image),
        contentDescription = "Add Image",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(100.dp)
            .clickable {
                office = Office()
                officeMainImage = Uri.EMPTY
                navController.navigate(Screen.AddOffice.route)
            }
    )
}

@Composable
fun OfficeComponent(navController: NavHostController, thisOffice : Office) {
//    val painterRes = rememberAsyncImagePainter(
//        model = if (imageMapOffice[thisOffice.id] != Uri.EMPTY.toString()) {
//            imageMapOffice[thisOffice.id]
//        } else {
//            R.drawable.company
//        }
//    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp)
                .align(Alignment.Center)
        ) {
            Image(painter = painterResource(id = R.drawable.company),
                contentDescription = "Add Image",
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        office = thisOffice
                        officeMainImage = if (imageMapOffice.contains(thisOffice.id))
                            Uri.parse(imageMapOffice[thisOffice.id])
                        else
                            Uri.EMPTY
                        fromWhere = "OfficeComponent"
                        navController.navigate(Screen.AddOffice.route)
                    }
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = thisOffice.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally))
        }
    }
}