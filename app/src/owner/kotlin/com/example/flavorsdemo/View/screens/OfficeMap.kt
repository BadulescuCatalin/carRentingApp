package com.example.flavorsdemo.View.screens

import ConfirmationDialog
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.TopBarOffice
import com.example.flavorsdemo.View.components.fromWhere
import com.example.flavorsdemo.View.components.imageMapOffice
import com.example.flavorsdemo.View.components.office
import com.example.flavorsdemo.View.components.officeMainImage
import com.example.flavorsdemo.ViewModel.OfficeImageViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun OfficeMap(navController: NavHostController) {
    val markerState = rememberMarkerState(
        position = LatLng(office.latitude.toDouble(), office.longitude.toDouble())
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 18f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val officeViewModel: OfficeViewModel = viewModel()
    val officeImageViewModel: OfficeImageViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(colorResource(id = R.color.light_blue))
                .padding(top = 8.dp)
        ) {
            Column {
                TopBarOffice(navController = navController, title = "Map Location")
            }
        }
        Box(

            modifier = Modifier
                .background(colorResource(id = R.color.white))
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = markerState,
                    draggable = true,
                    title = office.name,
                    snippet = office.address,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                )
            }
            if (showConfirmationDialog) {
                ConfirmationDialog(onConfirm = {
                    officeImageViewModel.deleteImage("offices/${office.id}/main.jpg")
                    officeViewModel.deleteOffice(office.id)
                    officeMainImage = Uri.EMPTY
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
                            officeViewModel.addOffice(office)
                        }
                        coroutineScope.launch {
                            officeImageViewModel.uploadOfficeImages(officeMainImage, office.id)
                            imageMapOffice[office.id] = officeMainImage.toString()
                            officeMainImage = Uri.EMPTY
                        }
                        navController.navigate(Screen.Home.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        colorResource(id = R.color.light_blue)
                    )
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
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.light_blue)
                        ),
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
                        colors = ButtonDefaults.buttonColors(
                            colorResource(id = R.color.light_blue)
                        ),
                        onClick = {
                            officeViewModel.updateOffice(office)
                            coroutineScope.launch {
                                officeImageViewModel.deleteImage("offices/${office.id}/main.jpg")
                                officeImageViewModel.uploadOfficeImages(officeMainImage, office.id)
                                imageMapOffice[office.id] = officeMainImage.toString()
                                officeMainImage = Uri.EMPTY
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
    LaunchedEffect(markerState.position) {
        office.latitude = markerState.position.latitude.toString()
        office.longitude = markerState.position.longitude.toString()
    }
}