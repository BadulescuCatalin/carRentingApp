package com.example.flavorsdemo.Model

import java.util.UUID

data class Feedback (
    val id: String,
    var userId: String,
    var carId : String,
    var stars: String,
    var clientName : String,
    var feedback: String
)
{
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "", "")
}