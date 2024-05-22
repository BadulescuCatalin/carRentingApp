package com.example.flavorsdemo.Repository

import com.example.flavorsdemo.Model.Office
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class OfficeRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun addOffice(thisOffice: Office, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("offices").document(thisOffice.id).set(thisOffice).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun getOffices(): Flow<List<Office>> = callbackFlow {
        val listener = db.collection("offices")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)  // Close the flow with an error if an exception occurs
                    return@addSnapshotListener
                }

                val officeList = snapshot?.documents?.mapNotNull {
                    it.toObject(Office::class.java)
                } ?: listOf()

                trySend(officeList)  // Send the list of cars to the Flow collector
            }

        awaitClose { listener.remove() }  // Detach the listener when the Flow collector is done
    }

    suspend fun getAllOfficesIds(): List<String> = withContext(Dispatchers.IO) {
        try {
            val snapshot = db.collection("offices").get().await()
            snapshot.documents.mapNotNull { it.id }  // Extracting the document IDs which are the car IDs
        } catch (e: Exception) {
            emptyList<String>()  // Return an empty list on failure
        }
    }
    suspend fun updateOffice(thisOffice: Office, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("offices").document(thisOffice.id).set(thisOffice).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
    suspend fun deleteOffice(officeId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("offices").document(officeId).delete().await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}