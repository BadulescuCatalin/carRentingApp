package com.example.flavorsdemo.View.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    text: String,
    navController: NavHostController
) {
    Column(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.light_grey),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = colorResource(id = R.color.light_blue),
                unfocusedIndicatorColor = colorResource(id = R.color.light_blue),
                disabledIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.light_blue),
            ),
            placeholder = {
                Text("City, address, ZIP code...")
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet() {

    ModalBottomSheet(
        modifier = Modifier,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        ),
        onDismissRequest = {
        },
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp
        ),
    ) {
        CustomBottomSheetContainer()
    }
}

@Composable
fun CustomBottomSheetContainer() {
    Scaffold(topBar = {
        Column {
            Text(
                text = "Theme", modifier = Modifier
                    .height(75.dp)
                    .padding(start = 29.dp, top = 26.dp), fontSize = 23.sp
            )
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }) {
        Column(modifier = Modifier.padding(it)) {
            Text(
                text = "Select theme", modifier = Modifier
                    .padding(start = 29.dp, top = 20.dp, bottom = 10.dp)
                    .height(40.dp), fontSize = 20.sp
            )
            CustomItem("Light")
            CustomItem("Dark")
            CustomItem("System default")
        }
    }
}

@Composable
fun CustomItem(text: String) {
    Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.gas),
            modifier = Modifier.padding(start = 31.dp, top = 9.dp), contentDescription = ""
        )
        Text(
            text = text, modifier = Modifier
                .height(40.dp)
                .padding(start = 20.dp, top = 11.dp),
            fontSize = 18.sp
        )
    }
}