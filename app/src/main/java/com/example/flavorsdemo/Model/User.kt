package com.example.flavorsdemo.Model

import java.util.UUID

data class User(
    var id: String,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var country: String,
    var emailAddress: String,
    var userType: String,
    var points : Int = 0
) {
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "", "", "")
}
