package com.example.flavorsdemo.Repository

import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.RemovedBookings
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface RemovedBookingsRepository {
    suspend fun addRemovedBooking(booking: RemovedBookings)
    suspend fun deleteRemovedBooking(booking: String)

}

class RemovedBookingsRepositoryImpl : RemovedBookingsRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun addRemovedBooking(booking: RemovedBookings) {
        db.collection("removedBookings").document(booking.id).set(booking).await()
    }

    override suspend fun deleteRemovedBooking(booking: String) {
        db.collection("removedBookings").document(booking).delete().await()
    }

    fun getBookings(): Flow<List<RemovedBookings>> = callbackFlow {
        val listener = db.collection("removedBookings")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)  // Close the flow with an error if an exception occurs
                    return@addSnapshotListener
                }

                val bookingList = snapshot?.documents?.mapNotNull {
                    it.toObject(RemovedBookings::class.java)
                } ?: listOf()

                trySend(bookingList)  // Send the list of cars to the Flow collector
            }

        awaitClose { listener.remove() }  // Detach the listener when the Flow collector is done
    }
}