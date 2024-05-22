package com.example.flavorsdemo.ViewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorsdemo.Repository.OfficeImageRepository
import com.example.flavorsdemo.View.components.imageMapOffice
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OfficeImageViewModel : ViewModel() {
    private val repository = OfficeImageRepository() // Ideally injected
    private val officeViewModel = OfficeViewModel() // Ideally injected
    private val _uploadStatus = MutableLiveData<String>()
    val uploadStatus: LiveData<String> = _uploadStatus
    private val officeIds = officeViewModel.officeIds

    val mainImagesList = repository.fetchAllMainImagesLiveData(officeIds = officeIds.value ?: emptyList())

    private val _officeImages = MutableLiveData<Map<String, String>>()
    val officeMainImages: LiveData<Map<String, String>> = _officeImages

    init {
        fetchOfficeMainImages()
    }

    private fun fetchOfficeMainImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val db = FirebaseFirestore.getInstance()
                val storage = FirebaseStorage.getInstance()
                val documents = db.collection("offices").get().await()
                val imageMap = mutableMapOf<String, String>()

                documents.documents.forEach { document ->
                    val officeId = document.id
                    val imagePath = "offices/$officeId/main.jpg"
                    val imageRef = storage.getReference(imagePath)
                    val imageUrl = imageRef.downloadUrl.await().toString()
                    imageMap[officeId] = imageUrl
                }

                _officeImages.postValue(imageMap)
            } catch (e: Exception) {
                // Handle exception, such as posting an error message or logging
            }
        }
    }
    fun uploadOfficeImages(officeImage: Uri, officeId: String) {
        viewModelScope.launch {
            repository.uploadImages(officeImage, officeId).onSuccess { urls ->
                // Here, you could also save URLs to Firestore if needed
                _uploadStatus.value = "Upload successful"
                imageMapOffice[officeId] = officeImage.toString()
            }.onFailure {
                _uploadStatus.value = "Upload failed: ${it.message}"
            }
        }
    }

    fun deleteImage(imagePath: String) {
        viewModelScope.launch {
            repository.deleteImage(imagePath).onSuccess {
                // Handle success (e.g., update UI or notify user)
            }.onFailure {
                // Handle error (e.g., show error message)
            }
        }
    }
}