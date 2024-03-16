package com.example.flavorsdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val registerHereString = stringResource(id = R.string.register_here_text)
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.login_car),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color
                    .Black
                    .copy(alpha = 0.7f)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        Text(stringResource(R.string.sign_in_text), fontSize = 32.sp, color = colorResource(R.color.brown_theme))
        Spacer(modifier = Modifier.weight(0.2f))
        TextField(
            value = email,
            shape = RoundedCornerShape(50.dp),
            onValueChange = { email = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.light_brown),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            label = { Text(stringResource(R.string.email_text)) },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.light_brown),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            label = { Text(stringResource(R.string.password_text)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Button(
            onClick = { /* Handle login */  navController.navigate(Screen.Register.route) },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_brown)),
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(stringResource(id = R.string.login_text))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomClickableTextLoginRegister(
            text = stringResource(id = R.string.don_t_have_an_account_text),
            onClickAction = {

                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
                navController.navigate(Screen.Register.route)

            }
        )
        Spacer(modifier = Modifier.padding(32.dp))
    }
}