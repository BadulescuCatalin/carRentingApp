package com.example.flavorsdemo.Model

import java.util.UUID

data class Discount(
    val id: String,
    var description: String,
    var discountType: String,
    var discountValue: String,
    var startDate: String,
    var endDate: String
) {
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "", "")
}