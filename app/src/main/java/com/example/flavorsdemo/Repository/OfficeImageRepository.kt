package com.example.flavorsdemo.Repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flavorsdemo.Model.CarImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class OfficeImageRepository {
    private val storageRef = Firebase.storage.reference

    suspend fun uploadImages(officeImage: Uri, officeId: String): Result<List<String>> = withContext(
        Dispatchers.IO) {
        try {
            val imageUrls = mutableListOf<String>()
            val mainImageUrl = uploadImage(officeImage, officeId, "main")
            imageUrls.add(mainImageUrl)
            Result.success(imageUrls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun uploadImage(imageUri: Uri, officeId: String, identifier: String): String {
        val imagePath = "offices/$officeId/$identifier.jpg"
        val imageRef = storageRef.child(imagePath)
        val uploadTask = imageRef.putFile(imageUri).await()
        return uploadTask.storage.downloadUrl.await().toString()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchAllMainImagesLiveData(officeIds : List<String>): LiveData<List<String>> {
        val liveData = MutableLiveData<List<String>>()

        GlobalScope.launch(Dispatchers.IO) {
            val allMainImages = mutableListOf<String>()

            for (officeId in officeIds) {
                try {
                    val mainImageRef = storageRef.child("offices/$officeId/main.jpg")
                    val mainImageUrl = mainImageRef.downloadUrl.await().toString()
                    allMainImages.add(mainImageUrl)
                } catch (e: Exception) {
                    continue
                }
            }
            withContext(Dispatchers.Main) {
                liveData.value = allMainImages
            }
        }

        return liveData
    }
    suspend fun deleteImage(imagePath: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val ref = storageRef.child(imagePath)
            ref.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}