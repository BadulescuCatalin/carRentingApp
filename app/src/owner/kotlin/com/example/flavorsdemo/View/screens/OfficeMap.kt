package com.example.flavorsdemo.View.screens

import ConfirmationDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.MaterialTheme.colors
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.example.flavorsdemo.currentUser
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    val context = LocalContext.current
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchLocation(context) { location ->
                currentLocation = location
            }
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val scope = rememberCoroutineScope()
    Log.d("OfficeMap", "office: $office")
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocation(context) { location ->
                currentLocation = location
            }
        } else {
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    if (office.latitude == "" && office.longitude == "" && currentLocation != null) {
        office.latitude = currentLocation!!.latitude.toString()
        office.longitude = currentLocation!!.longitude.toString()
    }
    val markerState = rememberMarkerState(

        position =
        if (office.latitude == "" || office.longitude == "")
            LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        else
            LatLng(office.latitude.toDouble(), office.longitude.toDouble())
//        LatLng(office.latitude.toDouble(), office.longitude.toDouble())
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 18f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

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
                uiSettings = uiSettings,
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
                        if ( office.city == "" || office.country == "" || office.address == "" || office.zipcode == "" ||
                            office.name == "" || office.description == "" || office.phone == "" || office.email == "" ||
                            office.numberOfGps == "" || office.numberOfCameras == "" ||
                            office.numberOfAdditionalCarTrunks == "" || office.numberOfChildSeats == ""
                        ) {
                            Toast.makeText(
                                context,
                                "Please complete all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            office.userId = currentUser.id
                            coroutineScope.launch {
                                officeViewModel.addOffice(office)
                            }
                            coroutineScope.launch {
                                officeImageViewModel.uploadOfficeImages(officeMainImage, office.id)
                                imageMapOffice[office.id] = officeMainImage.toString()
                                officeMainImage = Uri.EMPTY
                            }
                            navController.navigate(Screen.Home.route)
                        }
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
                            if ( office.city == "" || office.country == "" || office.address == "" || office.zipcode == "" ||
                                office.name == "" || office.description == "" || office.phone == "" || office.email == "" ||
                                office.numberOfGps == "" || office.numberOfCameras == "" ||
                                office.numberOfAdditionalCarTrunks == "" || office.numberOfChildSeats == ""
                            ) {
                                Toast.makeText(
                                    context,
                                    "Please complete all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                officeViewModel.updateOffice(office)
                                coroutineScope.launch {
                                    officeImageViewModel.deleteImage("offices/${office.id}/main.jpg")
                                    officeImageViewModel.uploadOfficeImages(
                                        officeMainImage,
                                        office.id
                                    )
                                    imageMapOffice[office.id] = officeMainImage.toString()
                                    officeMainImage = Uri.EMPTY
                                }
                                navController.navigate(Screen.Home.route)
                            }
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
