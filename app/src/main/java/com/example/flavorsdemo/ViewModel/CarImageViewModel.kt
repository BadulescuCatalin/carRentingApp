package com.example.flavorsdemo.ViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.flavorsdemo.Model.CarImage
import com.example.flavorsdemo.R
import com.example.flavorsdemo.Repository.CarImageRepository
import com.example.flavorsdemo.View.components.carImages
import com.example.flavorsdemo.View.components.imageMap
import com.example.flavorsdemo.View.components.userProfileImage
import com.example.flavorsdemo.currentUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CarImageViewModel : ViewModel() {

    private val repository = CarImageRepository() // Ideally injected
    private val carViewModel = CarViewModel() // Ideally injected
    private val _uploadStatus = MutableLiveData<String>()
    val uploadStatus: LiveData<String> = _uploadStatus
    private val carIds = carViewModel.carIds

    val mainImagesList = repository.fetchAllMainImagesLiveData(carIds = carIds.value ?: emptyList())

    private val _carImages = MutableLiveData<Map<String, String>>()
    private val _userImage = MutableLiveData<String>()
    private val _allCarImages = MutableLiveData<Map<String, List<String>>>()
    val carMainImages: LiveData<Map<String, String>> = _carImages
    val allCarImages: LiveData<Map<String, List<String>>> = _allCarImages
    val userImage: LiveData<String> = _userImage
    init {
        fetchCarMainImages()
        fetchAllCarImages()
//        Log.d("CarImageViewModel", "init: $imageMap")
//        fetchUserImage(currentUser.id)
    }

    fun fetchUserImage(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val storage = FirebaseStorage.getInstance()
                val imagePath = "user/$userId/profile.jpg"
                val imageRef = storage.getReference(imagePath)

                val imageUrl = imageRef.downloadUrl.await().toString()
                userProfileImage = imageUrl
                _userImage.postValue(imageUrl)
            } catch (e: Exception) {
                // Handle exception, such as posting an error message or logging
                _userImage.postValue(Uri.EMPTY.toString())
                userProfileImage = "https://firebasestorage.googleapis.com/v0/b/carrentingapp-5537e.appspot.com/o/default%2Fprofile_image.jpg?alt=media&token=d9ff6858-0f12-48af-a721-d4bcb4ead832"
            }
        }
    }
    private fun fetchCarMainImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val db = FirebaseFirestore.getInstance()
                val storage = FirebaseStorage.getInstance()
                val documents = db.collection("cars").get().await()
                val imageMap = mutableMapOf<String, String>()
                documents.documents.forEach { document ->
                    val carId = document.id

                    val imagePath = "cars/$carId/main.jpg"
                    val imageRef = storage.getReference(imagePath)

                    Log.d("CarImageViewModel", "carId: $imageRef")
                    val imageUrl = imageRef.downloadUrl.await().toString()
                    imageMap[carId] = imageUrl
                }
                _carImages.postValue(imageMap)
            } catch (e: Exception) {
                // Handle exception, such as posting an error message or logging
            }
        }
    }
    fun uploadCarImages(carImage: CarImage, carId: String) {
        viewModelScope.launch {
            repository.uploadImages(carImage, carId).onSuccess { urls ->
                // Here, you could also save URLs to Firestore if needed
                _uploadStatus.value = "Upload successful"
                imageMap[carId] = carImage.mainImage.toString()
                carImages = CarImage()
            }.onFailure {
                _uploadStatus.value = "Upload failed: ${it.message}"

                carImages = CarImage()
            }
        }
    }

    fun getImageUrls(carId: String) = liveData {
        val imageUrls = repository.fetchImageUrlsByCarId(carId)
        emit(imageUrls)  // Emit the list of image URLs to the UI
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

    fun addUserImage(image: Uri, userId: String) {
        viewModelScope.launch {
            repository.uploadUserImage(image, userId, "profile")
        }
    }

    private fun fetchAllCarImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val db = FirebaseFirestore.getInstance()
                val storage = FirebaseStorage.getInstance()
                val documents = db.collection("cars").get().await()
                val imageMap = mutableMapOf<String, List<String>>()

                documents.documents.forEach { document ->
                    val carId = document.id
                    val imagesRef = storage.reference.child("cars/$carId")
                    val imageUrls = mutableListOf<String>()
                    imagesRef.listAll().await().items.forEach { item ->
                        val imageUrl = item.downloadUrl.await().toString()
                        if (!imageUrl.contains("main.jpg")) {
                            imageUrls.add(imageUrl)
                        }
                    }
                    imageMap[carId] = imageUrls
                }

                _allCarImages.postValue(imageMap)
            } catch (e: Exception) {
                // Handle exception, such as posting an error message or logging
            }
        }
    }



}