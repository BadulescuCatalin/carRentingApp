package com.example.flavorsdemo.View.screens

import ConfirmationDialog
import TableRow
import TableRows
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.flavorsdemo.R
import com.example.flavorsdemo.Util.getLatLngFromAddress
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.InfoTitle
import com.example.flavorsdemo.View.components.TopBarOffice
import com.example.flavorsdemo.View.components.fromWhere
import com.example.flavorsdemo.View.components.imageMapOffice
import com.example.flavorsdemo.View.components.office
import com.example.flavorsdemo.View.components.officeMainImage
import com.example.flavorsdemo.ViewModel.OfficeImageViewModel
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.example.flavorsdemo.currentUser
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOffice(navController: NavHostController) {
    var zipcode by remember { mutableStateOf(office.zipcode) }
    var address by remember { mutableStateOf(office.address) }
    var city by remember { mutableStateOf(office.city) }
    var country by remember { mutableStateOf(office.country) }
    var name by remember { mutableStateOf(office.name) }
    var description by remember { mutableStateOf(office.description) }
    var phone by remember { mutableStateOf(office.phone) }
    var email by remember { mutableStateOf(office.email) }
    var numberOfGps by remember { mutableStateOf(office.numberOfGps) }
    var numberOfCameras by remember { mutableStateOf(office.numberOfCameras) }
    var numberOfAdditionalCarTrunks by remember { mutableStateOf(office.numberOfAdditionalCarTrunks) }
    var numberOfChildSeats by remember { mutableStateOf(office.numberOfChildSeats) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val officeViewModel: OfficeViewModel = viewModel()
    val officeImageViewModel: OfficeImageViewModel = viewModel()
    val mainImage = remember { mutableStateOf<Uri?>(officeMainImage) }
    val pickMainImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            mainImage.value = uri
            officeMainImage = uri ?: Uri.EMPTY
        }
    )
    if (office.address != "" || office.city != "" || office.zipcode != "" || office.country != "") {
        val fullAddress = "${office.address}, ${office.city}, ${office.zipcode}, ${office.country}"
        val latLng = getLatLngFromAddress(context, fullAddress)
        latLng?.let {
            // Use latLng.latitude and latLng.longitude
            office.latitude = latLng.latitude.toString()
            office.longitude = latLng.longitude.toString()
        }
    }
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
                TopBarOffice(navController = navController, title = "Office Details")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            LazyColumn(
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoTitle(title = "Office Address", icon = R.drawable.company)
                    Spacer(modifier = Modifier.height(8.dp))
                    TableRows(
                        text = "City",
                        placeholder = "City",
                        value = city,
                        onValueChange = {
                            city = it
                            office.city = it
                            val fullAddress = "$address, $city, $zipcode, $country"
                            val latLng = getLatLngFromAddress(context, fullAddress)
                            latLng?.let {
                                // Use latLng.latitude and latLng.longitude
                                office.latitude = latLng.latitude.toString()
                                office.longitude = latLng.longitude.toString()
                            }
                        },
                        text2 = "Country",
                        placeholder2 = "Country",
                        value2 = country,
                        onValueChange2 = {
                            country = it
                            office.country = it
                            val fullAddress = "$address, $city, $zipcode, $country"
                            val latLng = getLatLngFromAddress(context, fullAddress)
                            latLng?.let {
                                // Use latLng.latitude and latLng.longitude
                                office.latitude = latLng.latitude.toString()
                                office.longitude = latLng.longitude.toString()
                            }
                        }
                    )
                    TableRow(
                        text = "Address",
                        placeholder = "Address",
                        value = address,
                        onValueChange = {
                            address = it
                            office.address = it
                            val fullAddress = "$address, $city, $zipcode, $country"
                            val latLng = getLatLngFromAddress(context, fullAddress)
                            latLng?.let {
                                // Use latLng.latitude and latLng.longitude
                                office.latitude = latLng.latitude.toString()
                                office.longitude = latLng.longitude.toString()
                            }
                        }
                    )
                    TableRow(
                        text = "Zipcode",
                        placeholder = "Zipcode",
                        value = zipcode,
                        onValueChange = {
                            zipcode = it
                            office.zipcode = it
                            val fullAddress = "$address, $city, $zipcode, $country"
                            val latLng = getLatLngFromAddress(context, fullAddress)
                            latLng?.let {
                                // Use latLng.latitude and latLng.longitude
                                office.latitude = latLng.latitude.toString()
                                office.longitude = latLng.longitude.toString()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoTitle(title = "Inventory", icon = R.drawable.inventory)
                    Spacer(modifier = Modifier.height(8.dp))
                    TableRow(
                        text = "Number of GPS",
                        placeholder = "GPS",
                        value = numberOfGps,
                        onValueChange = {
                            numberOfGps = it
                            office.numberOfGps = it
                        }
                    )
                    TableRow(
                        text = "Number of Cameras",
                        placeholder = "Cameras",
                        value = numberOfCameras,
                        onValueChange = {
                            numberOfCameras = it
                            office.numberOfCameras = it
                        }
                    )
                    TableRow(
                        text = "Number of Car Trunks",
                        placeholder = "Car Trunks",
                        value = numberOfAdditionalCarTrunks,
                        onValueChange = {
                            numberOfAdditionalCarTrunks = it
                            office.numberOfAdditionalCarTrunks = it
                        }
                    )
                    TableRow(
                        text = "Number of Child Seats",
                        placeholder = "Seats",
                        value = numberOfChildSeats,
                        onValueChange = {
                            numberOfChildSeats = it
                            office.numberOfChildSeats = it
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    InfoTitle(title = "About Office", icon = R.drawable.info)
                    Spacer(modifier = Modifier.height(8.dp))
                    TableRow(
                        text = "Name",
                        placeholder = "Name",
                        value = name,
                        onValueChange = {
                            name = it
                            office.name = it
                        }
                    )
                    TableRow(
                        text = "Phone",
                        placeholder = "Phone",
                        value = phone,
                        onValueChange = {
                            phone = it
                            office.phone = it
                        },
                    )
                    TableRow(
                        text = "Email",
                        placeholder = "Email",
                        value = email,
                        onValueChange = {
                            email = it
                            office.email = it
                        }
                    )
                    var offset by remember { mutableStateOf(0f) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .border(
                                border = BorderStroke(
                                    0.5.dp,
                                    Color.Black
                                )
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    )
                    {
                        TextField(
                            value = description,
                            onValueChange = {
                                description = it
                                office.description = it
                            },
                            label = { Text("Enter a description which will be displayed for the clients") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = colorResource(id = R.color.white),
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .scrollable(
                                    orientation = Orientation.Vertical,
                                    state = rememberScrollableState { delta ->
                                        offset += delta
                                        delta
                                    }
                                )
                        )
                    }
                    Spacer(modifier = Modifier.height(124.dp))
                }
//                item {
//                    Spacer(modifier = Modifier.height(4.dp))
//                    InfoTitle(title = "Office Photo", icon = R.drawable.camera)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 16.dp)
//                    ) {
//                        Image(
//                            painter = rememberAsyncImagePainter(
//                                model = mainImage.value,
//                                error = painterResource(R.drawable.image_placeholder),
//                                placeholder = painterResource(R.drawable.image_placeholder)
//                            ),
//
//                            contentDescription = "Selected Image",
//                            contentScale = ContentScale.Fit,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 16.dp)
//                                .fillMaxHeight(0.4F)
//                                .scale(1.4F, 1.4F)
//                        )
//
//                        Button(
//                            onClick = {
//                                mainImage.value = null
//                                officeMainImage = Uri.EMPTY
//                            },
//                            modifier = Modifier
//                                .align(Alignment.TopEnd)
//                                .background(Color.Transparent)
//                                .padding(end = 24.dp)
//                                .offset(x = 2.dp, y = (-24).dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.Transparent
//                            )
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .background(
//                                        color = colorResource(id = R.color.black),
//                                        shape = CircleShape
//                                    )
//
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Cancel,
//                                    contentDescription = "Delete Main Image",
//                                    tint = colorResource(id = R.color.white),
//                                    modifier = Modifier.background(Color.Transparent)
//
//                                )
//                            }
//                        }
//                    }
//                    Button(
//                        onClick = { pickMainImageLauncher.launch("image/*") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                            .padding(top = 24.dp)
//                            .padding(end = 24.dp)
//                            .padding(start = 24.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            colorResource(id = R.color.light_blue)
//                        )
//                    ) {
//                        Text(text = "Pick Office Photo from Gallery")
//                    }
//                    Spacer(modifier = Modifier.height(96.dp))
//                }
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
                        // check all fields are completed
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
//                        if (office)
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
                            if (office.city == "" || office.country == "" || office.address == "" || office.zipcode == "" ||
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
}
