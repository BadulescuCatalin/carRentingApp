import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.car

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableRow(
    text: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    )
    {
        Text(text = text, modifier = Modifier.padding(start = 4.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 13.dp)

        ) {
            TextField(
                value = value,
                onValueChange = { onValueChange(it) },
                placeholder = { Text(placeholder) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.white),
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .offset(x = (-16).dp)

            )
            Divider(
                color = colorResource(id = R.color.black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .offset(y = (-12).dp)
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableRows(
    text: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    text2: String,
    placeholder2: String,
    value2: String,
    onValueChange2: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.47f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Text(text = text, modifier = Modifier.padding(start = 4.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)

            ) {
                TextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    placeholder = { Text(placeholder) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = colorResource(id = R.color.white),
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .offset(x = (-16).dp)

                )
                Divider(
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .offset(y = (-12).dp)
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Text(text = text2, modifier = Modifier.padding(start = 4.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)

            ) {
                TextField(
                    value = value2,
                    onValueChange = { onValueChange2(it) },
                    placeholder = { Text(placeholder2) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = colorResource(id = R.color.white),
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .offset(x = (-16).dp)

                )
                Divider(
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .offset(y = (-12).dp)
                )
            }
        }
    }
}

@Composable
fun TopBar(
    navController: androidx.navigation.NavHostController,
    title: String
) {
    Row(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Arrow Back Icon",
            tint = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(start = 4.dp, top = 22.dp, end = 50.dp)
                .size(24.dp)
                .clickable {
                    if (title == "Car Details") {
//                        car = Car()
//                        carImages = CarImage()
                    }
                    navController.popBackStack()
                }

        )
        Text(
            text = title,
            color = colorResource(id = R.color.white),
            modifier = androidx.compose.ui.Modifier
                .padding(top = 16.dp, end = 68.dp),
            fontSize = 22.sp
        )
        Text("")
    }
    Divider(
        color = colorResource(id = R.color.light_brown),
        modifier = Modifier
            .padding(top = 4.dp)
    )
    Row(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Button(
            onClick = {
                if (title != "Car Details") {
                    navController.popBackStack()
                    navController.navigate(Screen.AddCar.route)
                }
            },
            modifier = Modifier
                .padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        )
        {
            Text(
                text = "Car Details",
                color = colorResource(id = R.color.white),
            )

        }
        Divider(
            color = colorResource(id = R.color.light_grey),
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Button(
            onClick = {
                if (title != "Gallery") {
                    navController.navigate(Screen.AddImages.route)
                }
            },
            modifier = Modifier
                .padding(end = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text(
                text = "Gallery",
                color = colorResource(id = R.color.white)
            )
        }
    }
}

@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        containerColor = colorResource(id = R.color.white),
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Confirm Deletion") },
        text = { Text(text = "Are you sure you want to delete this car? This action cannot be undone.") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                },
                colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.light_blue)
                )
            ) {
                Text("Delete" )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancel", color = colorResource(id = R.color.light_blue))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(officeList: List<Office>, officeSelected: String){
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(officeSelected) }

    // Default text field and menu item colors
    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.White, // Background color of the TextField
        disabledTextColor = Color.Black,
        focusedIndicatorColor = Color.Transparent, // No color when focused
        unfocusedIndicatorColor = Color.Transparent, // No color when unfocused
        disabledIndicatorColor = Color.Transparent
    )
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                ), // Background color of the Box containing everything
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()// Background color of the dropdown box
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    colors = textFieldColors, // Apply the custom TextField colors
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(colorResource(id = R.color.light_grey))
                        .fillMaxWidth() // Background color of the dropdown menu
                ) {
                    officeList.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.name) }, // Text color of dropdown items
                            onClick = {
                                selectedText = "Office:" + item.name
                                expanded = false
                                car.officeId = item.id
//                                onOfficeSelected(item)
                            },
                            modifier = Modifier.background(Color.White) // Background color of dropdown items
                        )
                    }
                }
            }
        }
    }
}