package com.example.flavorsdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailCorrect by remember { mutableStateOf(true) }
    var isEmailEmpty by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var isPasswordEmpty by remember { mutableStateOf(false) }
    var rememberMeChecked by remember { mutableStateOf(false) }

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
        Spacer(modifier = Modifier.weight(0.55f))
        Text(
            stringResource(R.string.sign_in_text),
            style = TextStyle(
                color = Color.White,
                fontSize = 48.sp
            )
        )
        Spacer(modifier = Modifier.weight(0.2f))
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.email_text),
            keyboard = KeyboardOptions(keyboardType = KeyboardType.Email),
            isEmailCorrect = isEmailCorrect,
            isEmailUsed = false,
            isEmptyValue = isEmailEmpty
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomPasswordInputLogin(
            password = password,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
            label = stringResource(id = R.string.password_text),
            passwordEmpty = isPasswordEmpty,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.Start)
        ) {
            Checkbox(
                checked = rememberMeChecked,
                onCheckedChange = { rememberMeChecked = !rememberMeChecked },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = colorResource(R.color.light_brown),
                    checkmarkColor = colorResource(R.color.light_brown)
                )
            )
            Text(
                stringResource(id = R.string.remember_me_text),
                color = colorResource(R.color.light_brown),
                fontSize = 17.sp
            )

        }
        // asta de pus daca pica loginul
        if (rememberMeChecked) {
            // si de pus daca checked sa salvez datele sa faca login automat data viitoare
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(id = R.string.incorrect_email_or_password_text),
                color = colorResource(R.color.light_brown),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
                    .padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.weight(0.25f))
        Button(
            onClick = {
                isEmailEmpty = email.isEmpty()
                isEmailCorrect = checkEmail(email)
                isPasswordEmpty = password.isEmpty()
            },
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