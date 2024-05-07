package com.example.flavorsdemo.Model

import com.example.flavorsdemo.View.screens.user
import java.util.UUID

data class Car(
    val id : String,
    var brand: String,
    var model: String,
    var year: String,
    var price: String,
    var mileage: String,
    var color: String,
    var fuelType: String,
    var transmission: String,
    var description: String,
    var numberOfSeats: String,
    var type: String,
    var numberOfDoors: String,
    var urbanFuelConsumption: String,
    var extraUrbanFuelConsumption: String,
    var officeId: String // ramane de vazut daca nu e company id si bag si un company
) {
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
}