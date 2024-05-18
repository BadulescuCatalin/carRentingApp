package com.example.flavorsdemo.Util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale

fun getLatLngFromAddress(context: Context, addressStr: String): LatLng? {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses: List<Address> = geocoder.getFromLocationName(addressStr, 1) as List<Address>
        if (addresses.isNotEmpty()) {
            val address = addresses[0]
            return LatLng(address.latitude, address.longitude)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}