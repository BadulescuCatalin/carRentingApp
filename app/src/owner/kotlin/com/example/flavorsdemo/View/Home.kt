package com.example.flavorsdemo.View

import androidx.annotation.ColorRes
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.Model.User
import com.example.flavorsdemo.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, sharedViewModel: SharedViewModel = viewModel()) {

    val db = FirebaseFirestore.getInstance()
    val user = Firebase.auth.currentUser
    var email = user?.email
    var currentUser by remember { mutableStateOf(User()) }
    var searchValue by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    val userData by sharedViewModel.userData.collectAsState()
    val textList = listOf<String>(
        "asfsafasfas",
        "safasff",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "sfsfsf",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111",
        "asfsafasfas",
        "safasff",
        "1111"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    )
    {
        InfoBar(firstName = userData?.firstName ?: "",
            lastName = userData?.lastName ?: "",
            searchValue = searchValue,
            onValueChange = { searchValue = it },
            showFilters = showFilters,
            setShowFilters = { showFilters = !showFilters }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = colorResource(id = R.color.light_grey))
        )
        {
            LazyColumn(
                modifier = Modifier
                    .background(color = colorResource(id = R.color.light_grey))
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Available cars",
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                            .background(color = Color.Transparent)
                    )
                }
                items(textList.size) { index ->
                    CarCard()
                }
            }
        }
    }
}