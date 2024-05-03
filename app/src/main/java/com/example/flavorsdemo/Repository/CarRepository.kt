package com.example.flavorsdemo.Repository

import com.example.flavorsdemo.Model.Car
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface CarRepository {
    suspend fun addCar(car: Car, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun updateCar(car: Car, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun deleteCar(carId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)


}

class CarRepositoryImpl : CarRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun addCar(car: Car, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("cars").document(car.id).set(car).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun getCars(): Flow<List<Car>> = callbackFlow {
        val listener = db.collection("cars")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)  // Close the flow with an error if an exception occurs
                    return@addSnapshotListener
                }

                val carsList = snapshot?.documents?.mapNotNull {
                    it.toObject(Car::class.java)
                } ?: listOf()

                trySend(carsList)  // Send the list of cars to the Flow collector
            }

        awaitClose { listener.remove() }  // Detach the listener when the Flow collector is done
    }

     suspend fun getAllCarIds(): List<String> = withContext(Dispatchers.IO) {
        try {
            val snapshot = db.collection("cars").get().await()
            snapshot.documents.mapNotNull { it.id }  // Extracting the document IDs which are the car IDs
        } catch (e: Exception) {
            emptyList<String>()  // Return an empty list on failure
        }
    }
    override suspend fun updateCar(car: Car, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("cars").document(car.id).set(car).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
    override suspend fun deleteCar(carId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("cars").document(carId).delete().await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}