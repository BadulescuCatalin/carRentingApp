import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.R
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.car
import com.example.flavorsdemo.View.carImages

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
        Text(text = text, modifier = Modifier.padding(start = 4.dp))
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
        )
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
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Confirm Deletion") },
        text = { Text(text = "Are you sure you want to delete this car? This action cannot be undone.") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancel")
            }
        }
    )
}