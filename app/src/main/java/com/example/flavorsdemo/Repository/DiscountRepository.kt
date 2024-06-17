package com.example.flavorsdemo.Repository

import com.example.flavorsdemo.Model.Discount
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface DiscountRepository {
    suspend fun addDiscount(discount: Discount, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun updateDiscount(discount: Discount, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun deleteDiscount(discountId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}

class DiscountRepositoryImpl : DiscountRepository {
    private val db = FirebaseFirestore.getInstance()
    override suspend fun addDiscount(discount: Discount, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("discounts").document(discount.id).set(discount).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun updateDiscount(discount: Discount, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("discounts").document(discount.id).set(discount).await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)

        }
    }

    override suspend fun deleteDiscount(discountId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            db.collection("discounts").document(discountId).delete().await()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    fun getDiscounts() : Flow<List<Discount>> = callbackFlow {
        val listener = db.collection("discounts")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                val discountsList = snapshot?.documents?.mapNotNull {
                    it.toObject(Discount::class.java)
                } ?: listOf()

                trySend(discountsList)
            }

        awaitClose { listener.remove() }
    }

    suspend fun updateDiscountOfficeIds(discounts: List<Discount>, newId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        try {
            for (discount in discounts) {
                db.collection("discounts")
                    .document(discount.id)
                    .update("officeId", newId)
                    .await()
            }
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}