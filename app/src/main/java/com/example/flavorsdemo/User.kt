package com.example.flavorsdemo

data class User(
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var country: String,
    var emailAddress: String,
    var userType: String
) {
    constructor() : this("", "", "", "", "", "")
}
