package com.example.flavorsdemo.Utils

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.util.Locale

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

fun checkEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun checkPasswords(password: String, confirmedPassword: String): Boolean {
    return password == confirmedPassword
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