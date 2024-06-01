package com.example.flavorsdemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Repository.BookingRepository
import com.example.flavorsdemo.Repository.BookingRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel() : ViewModel() {
    private val bookingsRepository = BookingRepositoryImpl()
    val bookings = bookingsRepository.getBookings().asLiveData()

    suspend fun addBooking(booking: Booking) {
        bookingsRepository.addBooking(booking)
    }

    suspend fun updateBooking(booking: Booking) {
        bookingsRepository.updateBooking(booking)
    }

    suspend fun deleteBooking(bookingId: String) {
        bookingsRepository.deleteBooking(bookingId)
    }
}