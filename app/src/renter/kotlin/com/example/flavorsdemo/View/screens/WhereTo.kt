import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.components.SearchBar
import com.example.flavorsdemo.View.components.officesGlobal
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
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.flavorsdemo.Model.Office

lateinit var fusedLocationProviderClient: FusedLocationProviderClient

//@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
//@Composable
//fun WhereTo(navController: NavHostController) {
//    val context = LocalContext.current
//    val officeViewModel: OfficeViewModel = viewModel()
//    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
//    officesGlobal = offices
//    var showLazylist by remember { mutableStateOf(false) }
//    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
//    var searchText by remember { mutableStateOf("") }
//    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            fetchLocation(context) { location ->
//                currentLocation = location
//            }
//        } else {
//            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//    val scope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        if (ContextCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fetchLocation(context) { location ->
//                currentLocation = location
//            }
//        } else {
//            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }
//
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(currentLocation ?: LatLng(0.0, 0.0), 10f)
//    }
//
//    val filteredOffices = if (searchText.isEmpty()) {
//        emptyList()
//    } else {
//        officesGlobal.filter {
//            it.name.contains(searchText, ignoreCase = true) ||
//                    it.address.contains(searchText, ignoreCase = true) ||
//                    it.city.contains(searchText, ignoreCase = true) ||
//                    it.zipcode.contains(searchText, ignoreCase = true) ||
//                    it.country.contains(searchText, ignoreCase = true)
//        }
//    }
//
//    ModalBottomSheetLayout(
//        sheetGesturesEnabled = false, // poate
//        sheetState = sheetState,
//
//        sheetContent = {
//            // Sheet content
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
//            ) {
//
//                Button(onClick = {
//                    scope.launch { sheetState.hide() }
//                }) {
//                    Icon(Icons.Filled.Cancel, contentDescription = null)
//                }
//                Text("Content of the bottom sheet")
//            }
//        },
//        sheetBackgroundColor = Color.White,
//        sheetShape = MaterialTheme.shapes.large.copy(
//            bottomStart = CornerSize(16.dp),
//            bottomEnd = CornerSize(16.dp)
//        ),
//        scrimColor = Color.Transparent
//    ) {
//        Scaffold(
//        ) { contentPadding ->
//            contentPadding
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(colorResource(id = R.color.white))
//                    .padding(bottom = 48.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(135.dp)
//                        .background(colorResource(id = R.color.light_blue))
//                ) {
//                    Column(
//                        modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
//                    ) {
//                        Text(
//                            text = "Where?",
//                            fontSize = 24.sp,
//                            color = colorResource(id = R.color.white)
//                        )
//                        SearchBar(
//                            text = searchText,
//                            onValueChange = {
//                                searchText = it
//                                showLazylist = true
//                            }
//                        )
//                    }
//                }
//
//                Box(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    GoogleMap(
//                        modifier = Modifier.fillMaxSize(),
//                        cameraPositionState = cameraPositionState,
//                    ) {
//                        currentLocation?.let {
//                            Marker(
//                                state = MarkerState(position = it),
//                                title = "You are here"
//                            )
//                            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 7f)
//                        }
//                        officesGlobal.forEach { location ->
//                            Marker(
//                                state = MarkerState(
//                                    position = LatLng(
//                                        location.latitude.toDouble(),
//                                        location.longitude.toDouble()
//                                    )
//                                ),
//                                draggable = false,
//                                title = location.name,
//                                snippet = location.address,
//                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
//                                onInfoWindowClick = {
//                                    showLazylist = false
//                                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
//                                        LatLng(
//                                            location.latitude.toDouble(),
//                                            location.longitude.toDouble()
//                                        ), 15f
//                                    )
//                                    scope.launch { sheetState.show() }
//                                },
//                                onClick = {
//                                    searchText = location.name
//                                    showLazylist = false
//                                    false
//                                }
//                            )
//                        }
//                    }
//
//                    if (searchText.isNotEmpty() && showLazylist) {
//                        LazyColumn(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 16.dp, end = 16.dp)
//                                .background(Color.White)
//                                .height(200.dp)
//                        ) {
//                            itemsIndexed(filteredOffices) { _, suggestion ->
//                                Column {
//                                    Text(
//                                        text = suggestion.name,
//                                        modifier = Modifier
//                                            .clickable {
//                                                searchText = suggestion.name
//                                                showLazylist = false
//                                            }
//                                            .padding(horizontal = 16.dp)
//                                            .padding(vertical = 2.dp)
//                                    )
//                                    Text(
//                                        text = suggestion.address + ", " + suggestion.city + ", " + suggestion.zipcode + ", " + suggestion.country,
//                                        color = Color.Gray,
//                                        fontSize = 12.sp,
//                                        modifier = Modifier.padding(horizontal = 16.dp)
//                                    )
//                                }
//                                Divider(
//                                    color = colorResource(id = R.color.light_grey),
//                                    thickness = 2.dp
//                                )
//                                Spacer(modifier = Modifier.height(2.dp))
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//fun fetchLocation(context: Context, action: (LatLng) -> Unit) {
//    if (ActivityCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//        && ActivityCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.ACCESS_COARSE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//        return
//    }
//
//    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
//        if (location != null) {
//            action(LatLng(location.latitude, location.longitude))
//        } else {
//            Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun WhereTo(navController: NavHostController) {
//    val scaffoldState = rememberBottomSheetScaffoldState(
//        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
//    )
//    val context = LocalContext.current
//    val officeViewModel: OfficeViewModel = viewModel()
//    val offices by officeViewModel.offices.observeAsState(initial = emptyList())
//    officesGlobal = offices
//    var showLazylist by remember { mutableStateOf(false) }
//    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
//    var searchText by remember { mutableStateOf("") }
//    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            fetchLocation(context) { location ->
//                currentLocation = location
//            }
//        } else {
//            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
//    val scope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        if (ContextCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fetchLocation(context) { location ->
//                currentLocation = location
//            }
//        } else {
//            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }
//
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(currentLocation ?: LatLng(0.0, 0.0), 10f)
//    }
//
//    val filteredOffices = if (searchText.isEmpty()) {
//        emptyList()
//    } else {
//        officesGlobal.filter {
//            it.name.contains(searchText, ignoreCase = true) ||
//                    it.address.contains(searchText, ignoreCase = true) ||
//                    it.city.contains(searchText, ignoreCase = true) ||
//                    it.zipcode.contains(searchText, ignoreCase = true) ||
//                    it.country.contains(searchText, ignoreCase = true)
//        }
//    }
//    BottomSheetScaffold(
//        sheetShape = RoundedCornerShape(20.dp), // Rounded corners
//        sheetElevation = 200.dp, // To provide Shadow
//        sheetPeekHeight = 400.dp, // Initial height of sheet
//        sheetContent = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .heightIn(min = 400.dp, max = 600.dp)
//            ) {
//                Divider(
//                    color = Color.LightGray,
//                    thickness = 2.dp,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .fillMaxWidth(0.08f)
//                        .clip(RoundedCornerShape(10.dp)),
//                )
//                Text(
//                    text = "Sheet Content",
//                    fontSize = 24.sp,
//                    modifier = Modifier.padding(16.dp)
//                )
//                // Additional sheet content goes here
//            }
//        },
//        scaffoldState = scaffoldState,
//        content = { innerPadding ->
//            // Main content goes here
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(135.dp)
//                    .background(colorResource(id = R.color.light_blue))
//            ) {
//                Column(
//                    modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
//                ) {
//                    Text(
//                        text = "Where?",
//                        fontSize = 24.sp,
//                        color = colorResource(id = R.color.white)
//                    )
//                    SearchBar(
//                        text = searchText,
//                        onValueChange = {
//                            searchText = it
//                            showLazylist = true
//                        }
//                    )
//                }
//            }
//
//            Box(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                GoogleMap(
//                    modifier = Modifier.fillMaxSize(),
//                    cameraPositionState = cameraPositionState,
//                ) {
//                    currentLocation?.let {
//                        Marker(
//                            state = MarkerState(position = it),
//                            title = "You are here"
//                        )
//                        cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 7f)
//                    }
//                    officesGlobal.forEach { location ->
//                        Marker(
//                            state = MarkerState(
//                                position = LatLng(
//                                    location.latitude.toDouble(),
//                                    location.longitude.toDouble()
//                                )
//                            ),
//                            draggable = false,
//                            title = location.name,
//                            snippet = location.address,
//                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
//                            onInfoWindowClick = {
//                                showLazylist = false
//                                cameraPositionState.position = CameraPosition.fromLatLngZoom(
//                                    LatLng(
//                                        location.latitude.toDouble(),
//                                        location.longitude.toDouble()
//                                    ), 15f
//                                )
//                                scope.launch { sheetState.show() }
//                            },
//                            onClick = {
//                                searchText = location.name
//                                showLazylist = false
//                                false
//                            }
//                        )
//                    }
//                }
//
//                if (searchText.isNotEmpty() && showLazylist) {
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 16.dp, end = 16.dp)
//                            .background(Color.White)
//                            .height(200.dp)
//                    ) {
//                        itemsIndexed(filteredOffices) { _, suggestion ->
//                            Column {
//                                Text(
//                                    text = suggestion.name,
//                                    modifier = Modifier
//                                        .clickable {
//                                            searchText = suggestion.name
//                                            showLazylist = false
//                                        }
//                                        .padding(horizontal = 16.dp)
//                                        .padding(vertical = 2.dp)
//                                )
//                                Text(
//                                    text = suggestion.address + ", " + suggestion.city + ", " + suggestion.zipcode + ", " + suggestion.country,
//                                    color = Color.Gray,
//                                    fontSize = 12.sp,
//                                    modifier = Modifier.padding(horizontal = 16.dp)
//                                )
//                            }
//                            Divider(
//                                color = colorResource(id = R.color.light_grey),
//                                thickness = 2.dp
//                            )
//                            Spacer(modifier = Modifier.height(2.dp))
//                        }
//                    }
//                }
//            }
//        }
//    )
//}

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
        sheetShape = RoundedCornerShape(20.dp), // Rounded corners
        sheetElevation = 200.dp, // To provide Shadow
        sheetPeekHeight = 30.dp, // Initial height of sheet when collapsed
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(min = 200.dp, max = 800.dp) // Min and max height for resizing
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(4.dp)
                        .background(Color.Gray, RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = selectedOffice?.name ?: "Sheet Content",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = selectedOffice?.address ?: "",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
                // Button to hide the bottom sheet
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    scope.launch {
                        dividerColor = R.color.white
                        scaffoldState.bottomSheetState.collapse()
                    }
                }) {
                    Text("Hide Bottom Sheet")
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
                    .padding(bottom = 16.dp)
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
                        Text(
                            text = "Where?",
                            fontSize = 24.sp,
                            color = colorResource(id = R.color.white)
                        )
                        SearchBar(
                            text = searchText,
                            onValueChange = {
                                searchText = it
                                showLazylist = true
                            }
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
                                    scope.launch {
                                        dividerColor = R.color.light_grey
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                },
                                onClick = {
                                    searchText = location.name
                                    showLazylist = false
                                    selectedOffice = location
                                    scope.launch {
                                        dividerColor = R.color.light_grey
                                        scaffoldState.bottomSheetState.expand()
                                    }
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