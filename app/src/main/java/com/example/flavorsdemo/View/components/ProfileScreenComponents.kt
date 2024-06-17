package com.example.flavorsdemo.View.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.flavorsdemo.R
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import com.example.flavorsdemo.currentUser

@Composable
fun CircularImage(icon: Int) {
    val context = LocalContext.current
    val carImageViewModel: CarImageViewModel = viewModel()
    val imageUri = remember { mutableStateOf(userProfileImage) }

//    carImageViewModel.fetchUserImage(currentUser.id)
    val pickMainImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            if (it != null) {
                carImageViewModel.addUserImage(it, currentUser.id)
                userProfileImage = it.toString()
                imageUri.value = it.toString()

            }
        }
    )

//    val painterRes = rememberAsyncImagePainter(
////        model = if (userProfileImage != Uri.EMPTY.toString()) {
////            carImageViewModel.userImage.value
////        } else {
////            icon
////        }
//        model = ImageRequest.Builder(LocalContext.current).data(
//            if(userProfileImage != Uri.EMPTY.toString()) userProfileImage.toString() else
//            "https://firebasestorage.googleapis.com/v0/b/carrentingapp-5537e.appspot.com/o/default%2Fprofile_image.jpg?alt=media&token=d9ff6858-0f12-48af-a721-d4bcb4ead832"
//        )
//            .placeholder(icon).build(),
//
//    )
//    Log.d("Image", "Image: ${carImageViewModel.userImage}")
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
    ) {
        LaunchedEffect(imageUri.value) {
            // This block will be reinvoked whenever imageUri.value changes
        }
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userProfileImage)
//                    .data(imageUri.value)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .build()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center)
//                .let { if(carImageViewModel.userImage.value == null) it.scale(1.45f) else it }
                .clickable { pickMainImageLauncher.launch("image/*") },


            )
    }
}

@Composable
fun ProfileScreenComponent(text: String, textValue: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = text,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        text = textValue,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
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
fun PopupEditField(title: String, value: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(16.dp)),
        containerColor = colorResource(id = R.color.white),
        title = { Text(text = "Edit $title") },
        text = { Text(text = "Are you sure you want to edit $title") },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.light_blue)
                ),
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Edit")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancel")
            }
        }
    )
}