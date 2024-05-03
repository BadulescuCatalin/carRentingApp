package com.example.flavorsdemo.View

import ConfirmationDialog
import TopBar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.R
import com.example.flavorsdemo.ViewModel.CarImageViewModel
import com.example.flavorsdemo.ViewModel.CarViewModelOwner
import com.google.android.play.integrity.internal.i
import kotlinx.coroutines.launch

var carImages = CarImage()

@Composable
fun AddImages(navController: NavHostController) {
    // Changed to mutableStateListOf to handle multiple URIs
    val imageUris = remember { mutableStateListOf<Uri>().apply { addAll(carImages.imageList) } }
    val pickImagesLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            imageUris.clear()
            imageUris.addAll((uris))
            carImages.imageList = uris
        }
    )
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val mainImage = remember { mutableStateOf<Uri?>(carImages.mainImage) }
    val pickMainImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            mainImage.value = uri
            carImages.mainImage = uri ?: Uri.EMPTY
        }
    )
    val carViewModel: CarViewModelOwner = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val carImageViewModel: CarImageViewModel = viewModel()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(colorResource(id = R.color.dark_brown))
                    .padding(top = 8.dp)
            ) {
                Column {
                    TopBar(navController = navController, title = "Gallery")
                }
            }
            // Use LazyColumn to display multiple images
            LazyColumn(
                modifier = Modifier
                    .padding(top = 136.dp)
                    .fillMaxSize()
            ) {
                item {
                    Column()
                    {
                        Box(
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(top = 8.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorResource(id = R.color.add_car_title_background))
                                .border(
                                    border = BorderStroke(
                                        0.5.dp,
                                        Color.Black
                                    )
                                ),
                        ) {
                            Text(
                                text = "Main Image",
                                color = colorResource(id = R.color.white),
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .align(Alignment.Center)
//                                .padding(bottom = 16.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = mainImage.value,
                                    error = painterResource(R.drawable.image_placeholder),
                                    placeholder = painterResource(R.drawable.image_placeholder)
                                ),

                                contentDescription = "Selected Image",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                                    .fillMaxHeight(0.4F)
                                    .scale(1.4F, 1.4F)
                            )

                            Button(
                                onClick = {
                                    mainImage.value = null
                                    carImages.mainImage = Uri.EMPTY
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(Color.Transparent)
                                    .padding(end = 24.dp)
                                    .offset(x = 2.dp, y = (-24).dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = colorResource(id = R.color.black),
                                            shape = CircleShape
                                        )

                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Delete Main Image",
                                        tint = colorResource(id = R.color.white),
                                        modifier = Modifier.background(Color.Transparent)

                                    )
                                }
                            }
                        }
                        Button(
                            onClick = { pickMainImageLauncher.launch("image/*") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .padding(top = 24.dp)
                                .padding(end = 24.dp)
                                .padding(start = 24.dp)
                        ) {
                            Text(text = "Pick Main Image from Gallery")
                        }
                    }
                }
                item {
                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(top = 8.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(colorResource(id = R.color.add_car_title_background))
                            .border(
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ),
                    ) {
                        Text(
                            text =
                            "Additional Images",
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
                items(imageUris.size) { index ->
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                    Box(
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = imageUris[index],
                                error = painterResource(R.drawable.image_placeholder),
                                placeholder = painterResource(R.drawable.image_placeholder)
                            ),
                            contentDescription = "Selected Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .fillMaxWidth()
                                .scale(1.4F, 1.4F)
                        )
                        Button(
                            onClick = {
                                imageUris.removeAt(index)
                                carImages.imageList =
                                    carImages.imageList.toMutableStateList()
                                        .apply { removeAt(index) }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .background(Color.Transparent)
                                .padding(end = 24.dp)
                                .offset(x = 2.dp, y = (-16).dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = colorResource(id = R.color.black),
                                        shape = CircleShape
                                    )

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Delete Main Image",
                                    tint = colorResource(id = R.color.white),
                                    modifier = Modifier.background(Color.Transparent)

                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(start = 24.dp, end = 24.dp),
                        onClick = { pickImagesLauncher.launch("image/*") } // Updated to pick multiple images
                    ) {
                        Text(text = "Pick Additional Images from Gallery")
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
            if (showConfirmationDialog) {
                ConfirmationDialog(onConfirm = {
                    carViewModel.deleteCar(car.id)
                    carImageViewModel.deleteImage("cars/${car.id}/main.jpg")
                    carImages.imageList.forEachIndexed { index, uri ->
                        carImageViewModel.deleteImage("cars/${car.id}/${index}.jpg")
                    }
                    navController.navigate(Screen.Home.route)
                }, onDismiss = {
                    showConfirmationDialog = false
                })
            }
            if (fromWhere == "") {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(start = 24.dp, end = 12.dp, bottom = 24.dp)
                        .align(Alignment.BottomEnd),
                    onClick = {
                        coroutineScope.launch {
                            carViewModel.addCar(car)
                        }
                        coroutineScope.launch {
                            carImageViewModel.uploadCarImages(carImages, car.id)
                            imageMap[car.id] = carImages.mainImage.toString()
                            imageMaps[car.id] = carImages.imageList.map { it.toString() }
                        }
                        navController.navigate(Screen.Home.route)
                    }
                ) {
                    Text(text = "Save")
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .padding(start = 12.dp, end = 12.dp, bottom = 24.dp),
                        onClick = {
                            showConfirmationDialog = true
                        }
                    ) {
                        Text(text = "Delete")
                    }
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .padding(start = 24.dp, end = 12.dp, bottom = 24.dp),
                        onClick = {
                            coroutineScope.launch {
                                carViewModel.updateCar(car)
                            }
                            coroutineScope.launch {
                                //
                                carImageViewModel.deleteImage("cars/${car.id}/main.jpg")
                                carImages.imageList.forEachIndexed { index, uri ->
                                    carImageViewModel.deleteImage("cars/${car.id}/${index}.jpg")
                                }
                                carImageViewModel.uploadCarImages(carImages, car.id)
                                imageMap[car.id] = carImages.mainImage.toString()
                                imageMaps[car.id] = carImages.imageList.map { it.toString() }
                            }
                            navController.navigate(Screen.Home.route)
                        }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}
