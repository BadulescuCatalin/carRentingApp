package com.example.flavorsdemo.Model

import com.example.flavorsdemo.View.screens.currentUser
import java.util.UUID

data class Office (
    val id : String,
    var zipcode: String,
    var address: String,
    var city: String,
    var country : String,
    var name : String,
    var description : String,
    var phone : String,
    var email : String,
    var numberOfGps : String,
    var numberOfCameras : String,
    var numberOfAdditionalCarTrunks : String,
    var numberOfChildSeats : String,

) {
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "", "", "", currentUser.phoneNumber, currentUser.emailAddress, "", "", "", "")
}