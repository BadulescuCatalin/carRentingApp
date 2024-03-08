package com.example.flavorsdemo

//import CountryDropdown
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(navController: NavHostController) {
    val userType = FlavorConfig.userType
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmedPassword by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("RO") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isPhoneNumberCorrect by remember { mutableStateOf(true) }
    var isPasswordCorrect by remember { mutableStateOf(true) }
    var arePasswotdsMatching by remember { mutableStateOf(true) }
    var expandedCountriesList by remember { mutableStateOf(false) }
    var countrySelected by remember { mutableStateOf("Country") }

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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.register),
            style = TextStyle(
                color = Color.White,
                fontSize = 48.sp
            ),
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(56.dp))
//        CountryDropdown(
//            selectedCountry = countrySelected,
//            onCountrySelected = { countrySelected = it },
//            expanded = expandedCountriesList,
//            onExpandedChange = { expandedCountriesList = !expandedCountriesList } // Toggle expansion
//        )

        CustomTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = stringResource(id = R.string.first_name),
            keyboard = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = stringResource(id = R.string.last_name),
            keyboard = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextFieldPhone(
            valuePhone = phoneNumber,
            onValueChangePhone = { phoneNumber = it },
            valueCountryCode = countryCode,
            onValueChangeCountryCode = { countryCode = it },
            isPhoneNumberCorrect = isPhoneNumberCorrect)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.email_text),
            keyboard = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomPasswordInput(
            password = password,
            theOtherPassword = confirmedPassword,
            onPasswordChange = { password = it },
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
            label = stringResource(id = R.string.password_text),
            isPasswordCorrect = isPasswordCorrect,
            arePasswordsMatching = arePasswotdsMatching
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomPasswordInput(
            password = confirmedPassword,
            theOtherPassword = password,
            onPasswordChange = { confirmedPassword = it },
            passwordVisible = confirmPasswordVisible,
            onPasswordVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible },
            label = stringResource(id = R.string.confirm_password_text),
            isPasswordCorrect = isPasswordCorrect,
            arePasswordsMatching = arePasswotdsMatching
        )
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = {
                isPhoneNumberCorrect = isValidPhoneNumber(phoneNumber, countryCode)
                isPasswordCorrect = isValidPassword(password)
                arePasswotdsMatching = checkPasswords(password, confirmedPassword)
                if (password == confirmedPassword) {
                    Toast
                        .makeText(
                            context,
                            "User type: $userType, Email: $email, Password: $password",
                            Toast.LENGTH_LONG
                        )
                        .show()
                } else {
                    Toast
                        .makeText(
                            context,
                            "Passwords do not match",
                            Toast.LENGTH_LONG
                        )
                        .show()
                }
            },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_brown)),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(stringResource(id = R.string.register))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomClickableTextLoginRegister(
            text = stringResource(R.string.already_have_an_account_log_in),
            onClickAction = {
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(Screen.Register.route)
                navController.navigate(Screen.Login.route)
            }
        )
    }
}

