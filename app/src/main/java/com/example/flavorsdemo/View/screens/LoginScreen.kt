package com.example.flavorsdemo.View.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flavorsdemo.FlavorConfig
import com.example.flavorsdemo.Model.SharedViewModel
import com.example.flavorsdemo.Model.User
import com.example.flavorsdemo.R
import com.example.flavorsdemo.Utils.checkEmail
import com.example.flavorsdemo.View.Screen
import com.example.flavorsdemo.View.components.CustomClickableTextLoginRegister
import com.example.flavorsdemo.View.components.CustomPasswordInputLogin
import com.example.flavorsdemo.View.components.CustomTextField
import com.example.flavorsdemo.currentUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


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
    var wrongCredentials by remember { mutableStateOf(false) }
    val sharedViewModel: SharedViewModel = viewModel()
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
        Spacer(modifier = Modifier.weight(0.20f))
        Text(
            stringResource(R.string.sign_in_text),
            style = TextStyle(
                color = Color.White,
                fontSize = 48.sp
            )
        )
        Spacer(modifier = Modifier.weight(0.18f))
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
        Row(
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
                    checkmarkColor = colorResource(R.color.light_brown),
                    checkedColor = colorResource(R.color.dark_brown)

                )
            )
            Text(
                stringResource(id = R.string.remember_me_text),
                color = colorResource(R.color.light_brown),
                fontSize = 17.sp
            )

        }
        if (wrongCredentials) {
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
                wrongCredentials = false
                if (!isEmailEmpty && isEmailCorrect && !isPasswordEmpty) {
                    sharedViewModel.fetchUserData(email ?: "")
                    val auth = Firebase.auth
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                Log.d("Login", "User: $user")
                                if (rememberMeChecked) {
                                    val sharedPreferences =
                                        context.getSharedPreferences(
                                            "AppUser",
                                            Context.MODE_PRIVATE
                                        )
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("autologin", true)
                                    editor.apply()
                                } else {
                                    val sharedPreferences =
                                        context.getSharedPreferences(
                                            "AppUser",
                                            Context.MODE_PRIVATE
                                        )
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("autologin", false)
                                    editor.apply()
                                }
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.popBackStack()
                                val db = FirebaseFirestore.getInstance()
                                db.collection("users")
                                    .whereEqualTo("emailAddress", email)
                                    .get()
                                    .addOnSuccessListener {
                                        for (document in it) {
                                            currentUser = document.toObject(User::class.java)
                                        }
                                    }
                                if (FlavorConfig.userType == "Owner") {
                                    navController.navigate(Screen.Home.route)
                                } else {
                                    navController.navigate(Screen.WhereTo.route)
                                }
                            } else {
                                wrongCredentials = true
                            }
                        }
                        .addOnFailureListener {
                            Log.d("Login", "Failed to login: ${it.message}")
                            wrongCredentials = true
                        }

                }
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
        Spacer(modifier = Modifier.padding(48.dp))
    }
}