package com.example.flavorsdemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.RemovedBookings
import com.example.flavorsdemo.Repository.RemovedBookingsRepositoryImpl

class RemovedBookingsViewModel() : ViewModel() {
    private val bookingsRepository = RemovedBookingsRepositoryImpl()
    val removedBookings = bookingsRepository.getBookings().asLiveData()

    suspend fun addBooking(booking: RemovedBookings) {
        bookingsRepository.addRemovedBooking(booking)
    }


    suspend fun deleteBooking(bookingId: String) {
        bookingsRepository.deleteRemovedBooking(bookingId)
    }
}