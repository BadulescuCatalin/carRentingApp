package com.example.flavorsdemo.Model

import java.util.UUID

data class Booking(
    val id: String,
    var userId: String,
    var carId: String,
    var officeId: String,
    var startDate: String,
    var startTime: String,
    var endDate: String,
    var endTime: String,
    var price: String,
    var hasGps: Boolean,
    var hasCargoCarrier: Boolean,
    var numberOfAdditionalDrivers: Int,
    var numberOfChildSeats: Int,
    var numberOfCameras: Int,
    var status: String

    ) {
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "", "", "", "", "", false, false, 0, 0, 0, "")
}