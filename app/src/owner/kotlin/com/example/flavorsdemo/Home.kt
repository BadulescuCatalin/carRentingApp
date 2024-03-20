package com.example.flavorsdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
    val textList = listOf<String>("asfsafasfas", "safasff", "1111",
        "asfsafasfas", "safasff", "1111", "asfsafasfas", "safasff", "1111", "asfsafasfas", "safasff", "1111",
        "sfsfsf","1111", "asfsafasfas", "safasff", "1111", "asfsafasfas", "safasff", "1111",
        "1111", "asfsafasfas", "safasff", "1111", "asfsafasfas", "safasff", "1111",
        "1111", "asfsafasfas", "safasff", "1111", "asfsafasfas", "safasff", "1111"
    )
    Column(
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(colorResource(id = R.color.dark_brown))
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            )
            {

                TextField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    placeholder = { Text(text = "Search", fontSize = 14.sp) },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = colorResource(id = R.color.light_brown),
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .scale(scaleY = 0.8F, scaleX = 0.8F)
                        .padding(bottom = 16.dp),
//                        .align(Alignment.BottomCenter),
                    singleLine = true,
                    maxLines = 1
                )
                IconButton(
                    onClick = { showFilters = !showFilters},
                    modifier = Modifier

                        .padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Filter,
                        contentDescription = "Expand Filter List",
                        tint = Color.Gray
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            items(textList.size) { index ->
                Text(text = textList[index])
            }
        }
    }
}