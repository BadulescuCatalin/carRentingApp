package com.example.flavorsdemo.Model

import java.util.UUID

data class RemovedBookings (
    var id : String,
    var userId : String,
    var startDate : String,
    var endDate : String,
    var car : String,
    var description : String,
    var price : String,
    var hasGps: Boolean,
    var hasCargoCarrier: Boolean,
    var numberOfAdditionalDrivers: Int,
    var numberOfChildSeats: Int,
    var numberOfCameras: Int,
    var status : String
)
{
    constructor() : this(UUID.randomUUID().toString(),"","","","","","", false, false, 0, 0, 0,"")
}