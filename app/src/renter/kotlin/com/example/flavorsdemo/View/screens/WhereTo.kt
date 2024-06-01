import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.SearchBar
import com.example.flavorsdemo.View.components.office
import com.example.flavorsdemo.View.components.officesGlobal
import com.example.flavorsdemo.View.components.selectedOfficeGlobal
import com.example.flavorsdemo.ViewModel.OfficeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WhereTo(navController: NavHostController) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    var dividerColor by remember { mutableStateOf(R.color.white) }
    val context = LocalContext.current
    val officeViewModel: OfficeViewModel = viewModel()
    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
    officesGlobal = offices
    var showLazylist by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    var searchText by remember { mutableStateOf("") }
    var selectedOffice by remember { mutableStateOf<Office?>(null) } // To track the selected office
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
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

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation ?: LatLng(0.0, 0.0), 10f)
    }

    val filteredOffices = if (searchText.isEmpty()) {
        emptyList()
    } else {
        officesGlobal.filter {
            it.name.contains(searchText, ignoreCase = true) ||
                    it.address.contains(searchText, ignoreCase = true) ||
                    it.city.contains(searchText, ignoreCase = true) ||
                    it.zipcode.contains(searchText, ignoreCase = true) ||
                    it.country.contains(searchText, ignoreCase = true)
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(35.dp), // Rounded corners
        sheetElevation = 200.dp, // To provide Shadow
        sheetPeekHeight = 0.dp, // Initial height of sheet when collapsed
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(min = 245.dp, max = 800.dp) // Min and max height for resizing
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(4.dp)
                        .background(colorResource(id = dividerColor), RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = selectedOffice?.name ?: "Sheet Content",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.company),
                        contentDescription = "Company",
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "Office address:",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.Bottom),
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = (selectedOffice?.address + ", " + selectedOffice?.city +
                            ", " + selectedOffice?.zipcode + ", " + selectedOffice?.country)
                        ?: "Sheet Content",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    color = Color.Gray
                )
                // Button to hide the bottom sheet
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = (-6).dp, y = (-4).dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Button to open phone dialer
                    selectedOffice?.phone?.let { phoneNumber ->
//                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                Color.Transparent
                            ),
                            modifier = Modifier.border(
                                width = (0.5).dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            ),
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phoneNumber")
                                }
                                context.startActivity(intent)
                            }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Call,
                                    contentDescription = "Phone",
                                    tint = colorResource(id = R.color.light_blue),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .offset(x = (-2).dp)
                                )
                                Text(
                                    text = phoneNumber,
                                    color = colorResource(id = R.color.black),
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        modifier = Modifier.border(
                            width = (0.5).dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(20.dp)
                        ),
                        onClick = {
                            val gmmIntentUri =
                                Uri.parse("geo:0,0?q=${selectedOffice?.latitude},${selectedOffice?.longitude}(${selectedOffice?.address})")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                setPackage("com.google.android.apps.maps")
                            }
                            context.startActivity(mapIntent)
                        }) {
                        Row() {
                            Icon(
                                imageVector = Icons.Default.AddLocation,
                                contentDescription = "Directions",
                                tint = colorResource(id = R.color.light_blue),
                                modifier = Modifier
                                    .size(20.dp)
                                    .offset(x = (-2).dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            // Text for the button {Directions
                            Text(
                                text = "Directions",
                                color = colorResource(id = R.color.black),
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    onClick = {
                        navController.navigate(Screen.PickDateAndTime.route)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(5.dp))
//                        .border(
//                            width = (0.5).dp,
//                            color = Color.LightGray,
//                            shape = RoundedCornerShape(20.dp)
//                        )
                        .background(colorResource(id = R.color.light_blue))
                ) {
                    Text(text = "Select this location" , color = colorResource(id = R.color.white))
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
                    .padding(bottom = 48.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp)
                        .background(colorResource(id = R.color.light_blue))
                ) {
                    Column(
                        modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Where?",
                                fontSize = 24.sp,
                                color = colorResource(id = R.color.white)
                            )
                            Button(
                                onClick = {
                                    navController.navigate(Screen.Home.route)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    Color.Transparent
                                ),
                                modifier = Modifier
                                    .offset(x = 16.dp)
                            ) {
                               Text(
                                   text = "Home",
                                   color = colorResource(id = R.color.white)
                               )
                            }
                        }
                        SearchBar(
                            modifier = Modifier
                                .offset(y = (-8).dp),
                            text = searchText,
                            onValueChange = {
                                searchText = it
                                showLazylist = true
                            },
                            navController = navController
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                    ) {
                        currentLocation?.let {
                            Marker(
                                state = MarkerState(position = it),
                                title = "You are here"
                            )
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 7f)
                        }
                        officesGlobal.forEach { location ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        location.latitude.toDouble(),
                                        location.longitude.toDouble()
                                    )
                                ),
                                draggable = false,
                                title = location.name,
                                snippet = location.address,
                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                                onInfoWindowClick = {
                                    showLazylist = false
                                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                        LatLng(
                                            location.latitude.toDouble(),
                                            location.longitude.toDouble()
                                        ), 15f
                                    )
                                    selectedOffice = location
                                    selectedOfficeGlobal = location
                                    scope.launch {
                                        dividerColor = R.color.l_gray
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                },
                                onClick = {
                                    searchText = location.name
                                    showLazylist = false
                                    selectedOffice = location
                                    selectedOfficeGlobal = location
                                    false
                                }
                            )
                        }
                    }
                    if (searchText.isNotEmpty() && showLazylist) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                                .background(Color.White)
                                .height(200.dp)
                        ) {
                            itemsIndexed(filteredOffices) { _, suggestion ->
                                Column {
                                    Text(
                                        text = suggestion.name,
                                        modifier = Modifier
                                            .clickable {
                                                selectedOffice = suggestion
                                                selectedOfficeGlobal = suggestion
                                                scope.launch {
                                                    dividerColor = R.color.l_gray
                                                    scaffoldState.bottomSheetState.expand()
                                                }
                                                searchText = suggestion.name
                                                showLazylist = false
                                            }
                                            .padding(horizontal = 16.dp)
                                            .padding(vertical = 2.dp)
                                    )
                                    Text(
                                        text = suggestion.address + ", " + suggestion.city + ", " + suggestion.zipcode + ", " + suggestion.country,
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                                Divider(
                                    color = colorResource(R.color.light_grey),
                                    thickness = 2.dp
                                )

                                Spacer(modifier = Modifier.height(2.dp))
                            }
                        }
                    }
                }
            }
        }
    )
}

fun fetchLocation(context: Context, action: (LatLng) -> Unit) {
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            action(LatLng(location.latitude, location.longitude))
        } else {
            Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
        }
    }
}