package com.example.flavorsdemo.Repository

import com.example.flavorsdemo.Model.Booking
import com.example.flavorsdemo.Model.Feedback
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface FeedbackRepository {
    suspend fun addFeedback(feedback: Feedback)
}

class FeedbackRepositoryImpl : FeedbackRepository {
    private val db = FirebaseFirestore.getInstance()
    override suspend fun addFeedback(feedback: Feedback) {
        db.collection("feedback").document(feedback.id).set(feedback).await()
    }

    fun getFeedbacks(): Flow<List<Feedback>> = callbackFlow {
        val listener = db.collection("feedback")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)  // Close the flow with an error if an exception occurs
                    return@addSnapshotListener
                }

                val bookingList = snapshot?.documents?.mapNotNull {
                    it.toObject(Feedback::class.java)
                } ?: listOf()

                trySend(bookingList)  // Send the list of cars to the Flow collector
            }

        awaitClose { listener.remove() }  // Detach the listener when the Flow collector is done
    }
}