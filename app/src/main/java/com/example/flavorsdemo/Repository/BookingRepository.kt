package com.example.flavorsdemo.Repository

import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.View.components.bookingToUpdate
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface BookingRepository {
    suspend fun addBooking(booking: Booking)
    suspend fun updateBooking(booking: Booking)
    suspend fun deleteBooking(bookingId: String)
}
class BookingRepositoryImpl : BookingRepository{
    private val db = FirebaseFirestore.getInstance()

    override suspend fun addBooking(booking: Booking) {
        db.collection("bookings").document(booking.id).set(booking).await()
    }

    override suspend fun updateBooking(booking: Booking) {
        db.collection("bookings").document(booking.id).set(booking).await()
        bookingToUpdate = Booking()
    }

    override suspend fun deleteBooking(bookingId: String) {
        db.collection("bookings").document(bookingId).delete().await()
    }

    fun getBookings(): Flow<List<Booking>> = callbackFlow {
        val listener = db.collection("bookings")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)  // Close the flow with an error if an exception occurs
                    return@addSnapshotListener
                }

                val bookingList = snapshot?.documents?.mapNotNull {
                    it.toObject(Booking::class.java)
                } ?: listOf()

                trySend(bookingList)  // Send the list of cars to the Flow collector
            }

        awaitClose { listener.remove() }  // Detach the listener when the Flow collector is done
    }
}