package com.example.flavorsdemo.Model

import java.util.UUID

data class Booking(
    val id: String,
    var userId: String,
    var carId: String,
    var officeId: String,
    val startDate: String,
    val startTime: String,
    var endDate: String,
    var endTime: String,
    var price: String,
    var hasGps: Boolean,
    var hasCargoCarrier: Boolean,
    var numberOfAdditionalDrivers: Int,
    var numberOfChildSeats: Int,
    var numberOfCameras: Int,

    ) {
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "", "", "", "", "", false, false, 0, 0, 0)
}