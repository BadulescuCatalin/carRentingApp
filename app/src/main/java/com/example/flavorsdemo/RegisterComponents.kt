package com.example.flavorsdemo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

val countriesMap = getAllCountriesMap()
val countriesList = countriesMap.keys.toList()
val countriesMapReversedKeyValue = getAllCountriesMapReversed();

@Composable
fun CustomClickableTextLoginRegister(
    text: String,
    onClickAction: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append(text)
        pushStringAnnotation(
            tag = "CLICK",
            annotation = "clickableText"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("here")
        }
        pop()
        append("!")
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "CLICK", start = offset, end = offset)
                .firstOrNull()?.let {
                    onClickAction()
                }
        },
        style = TextStyle(color = Color.Gray, fontSize = 14.sp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboard: KeyboardOptions,
    isEmailCorrect: Boolean,
    isEmailUsed: Boolean,
    isEmptyValue: Boolean
) {
    val context = LocalContext.current
    var showPopup by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Column {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showPopup = false
            },
            shape = RoundedCornerShape(50.dp),
            label = { Text(text = label) },
            keyboardOptions = keyboard,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(id = R.color.light_brown),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
                if ((keyboard.keyboardType == KeyboardType.Email && (!isEmailCorrect || isEmailUsed)) || isEmptyValue) {
                    val image = Icons.Rounded.Error
                    val description = stringResource(R.string.invalid_field)
                    IconButton(onClick = {
                        showPopup = true
                        coroutineScope.launch {
                            delay(1500)
                            showPopup = false
                        }
                    }) {
                        Icon(
                            imageVector = image, contentDescription = description,
                            tint = Color.Red
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (showPopup) {
            Box(
                modifier = Modifier
                    .padding(end = 48.dp)
                    .align(Alignment.End)
            ) {
                Popup(
                    alignment = Alignment.BottomEnd,
                    properties = PopupProperties()
                ) {
                    Text(
                        text = if (isEmptyValue) "$label should not be empty" else if (isEmailUsed) stringResource(
                            R.string.email_already_used
                        ) else stringResource(R.string.invalid_email_address),
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Red, RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),

                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldPhone(
    valuePhone: String,
    onValueChangePhone: (String) -> Unit,
    valueCountryCode: String,
    onValueChangeCountryCode: (String) -> Unit,
    isPhoneNumberCorrect: Boolean,
    isPhonenNumberUsed: Boolean
) {
    val context = LocalContext.current
    var showPopup by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    Log.d("Register", countriesList.toString())

    var selectedCountry by remember { mutableStateOf("RO") }
    Column {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.23f)
                    .heightIn(54.dp)
                    .background(
                        color = colorResource(id = R.color.light_brown),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .clickable { expanded = !expanded }
            ) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = "Expand country list",
                        tint = Color.Gray
                    )
                }
                if (expanded) {
                    Popup(
                        onDismissRequest = { expanded = false },
                        alignment = Alignment.TopStart
                    )
                    {
                        IconButton(
                            onClick = { expanded = !expanded },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(start = 38.dp, top = 3.dp)

                        ) {
                            Icon(
                                imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                                contentDescription = "Expand country list",
                                tint = Color.Gray
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 64.dp)
                                .fillMaxWidth(0.3f)
                                .height(240.dp)
                                .background(
                                    color = colorResource(id = R.color.light_brown),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { expanded = false }
                        ) {
                            LazyColumn {
                                itemsIndexed(countriesList) { index, country ->
                                    DropdownMenuItem(
                                        text = { Text(country, color = Color.Black) },
                                        onClick = {
                                            selectedCountry = countriesMap[country] ?: "Country"
                                            onValueChangeCountryCode(
                                                countriesMap[country] ?: "Country"
                                            )
                                            expanded = false
                                        }
                                    )
                                    if (index < countriesList.size - 1) {
                                        Divider(
                                            color = Color.LightGray,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(2.dp))
            TextField(
                value = valuePhone,
                onValueChange = {
                    onValueChangePhone(it)
                    showPopup = false
                },
                shape = RoundedCornerShape(50.dp),
                label = { Text(text = stringResource(id = R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.light_brown),
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    if (!isPhoneNumberCorrect || isPhonenNumberUsed) {
                        val image = Icons.Rounded.Error
                        val description =
                            if (isPhonenNumberUsed) stringResource(R.string.phone_number_already_used) else stringResource(
                                R.string.invalid_field
                            )
                        IconButton(onClick = {
                            showPopup = true
                            coroutineScope.launch {
                                delay(1500)
                                showPopup = false
                            }
                        }) {
                            Icon(
                                imageVector = image, contentDescription = description,
                                tint = Color.Red
                            )
                        }
                    }
                }
            )
        }
        if (showPopup) {
            Box(
                modifier = Modifier
                    .padding(end = 32.dp)
                    .align(Alignment.End)
            ) {
                Popup(
                    alignment = Alignment.BottomEnd,
                    properties = PopupProperties()
                ) {
                    Text(
                        text = if (isPhonenNumberUsed) stringResource(R.string.phone_number_already_used) else stringResource(
                            R.string.invalid_phone_number_or_country_code
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Red, RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),

                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPasswordInput(
    password: String,
    theOtherPassword: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    label: String,
    isPasswordCorrect: Boolean,
    arePasswordsMatching: Boolean
) {
    var showPopupPassword by remember { mutableStateOf(false) }
    var showPopupConfirmedPassword by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    TextField(

        value = password,
        onValueChange = onPasswordChange,
        shape = RoundedCornerShape(50.dp),
        label = { Text(text = label) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colorResource(R.color.light_brown),
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"
            Row {
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(imageVector = image, contentDescription = description)
                }
                if (label == stringResource(id = R.string.password_text) && !isPasswordCorrect) {
                    val image2 = Icons.Rounded.Error
                    val description2 = stringResource(R.string.invalid_field)
                    IconButton(onClick = {
                        showPopupPassword = true
                        coroutineScope.launch {
                            delay(2500)
                            showPopupPassword = false
                        }
                    }) {
                        Icon(
                            imageVector = image2,
                            contentDescription = description2,
                            tint = Color.Red
                        )
                    }
                }
                if (label == stringResource(id = R.string.confirm_password_text) && !arePasswordsMatching) {
                    val image2 = Icons.Rounded.Error
                    val description2 = stringResource(R.string.invalid_field)
                    IconButton(onClick = {
                        showPopupConfirmedPassword = true
                        coroutineScope.launch {
                            delay(1500)
                            showPopupConfirmedPassword = false
                        }
                    }) {
                        Icon(
                            imageVector = image2,
                            contentDescription = description2,
                            tint = Color.Red
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    if (showPopupPassword) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 48.dp)
        ) {
            Popup(
                alignment = Alignment.BottomEnd,
                properties = PopupProperties()
            ) {
                Column(modifier = Modifier.padding(top = 445.dp)) {
                    Text(
                        text = stringResource(R.string.the_password_must_contain_at_least_10_characters_one_uppercase_letter_one_digit_one_special_character_and_no_spaces),
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Red, RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
    if (showPopupConfirmedPassword) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 48.dp)
        ) {
            Popup(
                alignment = Alignment.BottomEnd,
                properties = PopupProperties()
            ) {
                Text(
                    text = stringResource(R.string.passwords_do_not_match),
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.Red, RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),

                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPasswordInputLogin(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    label: String,
    passwordEmpty: Boolean
) {
    var showPopupPassword by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    Column {
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            shape = RoundedCornerShape(50.dp),
            label = { Text(text = label) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.light_brown),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"
                Row {
                    IconButton(onClick = onPasswordVisibilityChange) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                    if (passwordEmpty) {
                        val image2 = Icons.Rounded.Error
                        val description2 = stringResource(R.string.invalid_field)
                        IconButton(onClick = {
                            showPopupPassword = true
                            coroutineScope.launch {
                                delay(1500)
                                showPopupPassword = false
                            }
                        }) {
                            Icon(
                                imageVector = image2,
                                contentDescription = description2,
                                tint = Color.Red
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (showPopupPassword) {
            Box(
                modifier = Modifier
                    .padding(end = 48.dp)
                    .align(Alignment.End)
            ) {
                Popup(
                    alignment = Alignment.BottomEnd,
                    properties = PopupProperties()
                ) {
                    Text(
                        text = "$label should not be empty",
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Red, RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp),

                        color = Color.White
                    )
                }
            }
        }
    }
}

fun isValidPhoneNumber(phoneNumber: String, countryCode: String): Boolean {
    val phoneUtil = PhoneNumberUtil.getInstance()
    return try {
        val numberProto: Phonenumber.PhoneNumber = phoneUtil.parse(phoneNumber, countryCode)
        phoneUtil.isValidNumber(numberProto)
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*+\\-/_])(?!.*\\s).{10,}$"
    val passwordMatcher = Regex(passwordPattern)
    return passwordMatcher.matches(password)
}

fun checkPasswords(password: String, confirmedPassword: String): Boolean {
    return password == confirmedPassword
}

fun checkEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun getAllCountriesMap(): Map<String, String> {
    return Locale.getISOCountries().associateBy { countryCode ->
        val locale = Locale("", countryCode)
        locale.displayCountry
    }.toSortedMap()
}

fun getAllCountriesMapReversed(): Map<String, String> {
    return Locale.getISOCountries().associateWith { countryCode ->
        val locale = Locale("", countryCode)
        locale.displayCountry
    }.toSortedMap()
}