package com.example.flavorsdemo.Repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flavorsdemo.Model.CarImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CarImageRepository {

    private val storageRef = Firebase.storage.reference

    suspend fun uploadImages(carImage: CarImage, carId: String): Result<List<String>> = withContext(
        Dispatchers.IO) {
        try {
            val imageUrls = mutableListOf<String>()
            val mainImageUrl = uploadImage(carImage.mainImage, carId, "main")
            imageUrls.add(mainImageUrl)
            var imageIndex = 0
            carImage.imageList.forEachIndexed { index, uri ->
                val url = uploadImage(uri, carId, imageIndex.toString())
                imageIndex++
                imageUrls.add(url)
            }

            Result.success(imageUrls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun uploadImage(imageUri: Uri, carId: String, identifier: String): String {
        val imagePath = "cars/$carId/$identifier.jpg"
        val imageRef = storageRef.child(imagePath)
        val uploadTask = imageRef.putFile(imageUri).await()
        return uploadTask.storage.downloadUrl.await().toString()
    }

    suspend fun fetchImageUrlsByCarId(carId: String): List<String> = withContext(Dispatchers.IO) {
        try {
            val listResult = storageRef.child("cars/$carId").listAll().await()
            listResult.items.map { it.downloadUrl.await().toString() }
        } catch (e: Exception) {
            emptyList()  // Handle exceptions by returning an empty list
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchAllMainImagesLiveData(carIds : List<String>): LiveData<List<String>> {
        val liveData = MutableLiveData<List<String>>()

        GlobalScope.launch(Dispatchers.IO) {
            val allMainImages = mutableListOf<String>()

            for (carId in carIds) {
                try {
                    val mainImageRef = storageRef.child("cars/$carId/main.jpg")
                    val mainImageUrl = mainImageRef.downloadUrl.await().toString()
                    allMainImages.add(mainImageUrl)
                } catch (e: Exception) {
                    // Skip to the next carId if any error occurs
                    continue
                }
            }

            withContext(Dispatchers.Main) {
                liveData.value = allMainImages
            }
        }

        return liveData
    }

    // Method to delete an image
    suspend fun deleteImage(imagePath: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val ref = storageRef.child(imagePath)
            ref.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadUserImage(imageUri: Uri, userId: String, identifier: String): String {
        val imagePath = "user/$userId/$identifier.jpg"
        val imageRef = storageRef.child(imagePath)
        val uploadTask = imageRef.putFile(imageUri).await()
        return uploadTask.storage.downloadUrl.await().toString()
    }

}