package com.example.flavorsdemo.Model

import java.util.UUID

data class RemovedBookings (
    val id : String,
    val userId : String,
    val startDate : String,
    val endDate : String,
    val car : String,
    val description : String,
    val status : String
)
{
    constructor() : this(UUID.randomUUID().toString(),"","","","","","")
}