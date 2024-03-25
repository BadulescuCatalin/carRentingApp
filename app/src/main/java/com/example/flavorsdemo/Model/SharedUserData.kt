package com.example.flavorsdemo.Model

import androidx.lifecycle.ViewModel
import com.example.flavorsdemo.FlavorConfig
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {
    private val _userData = MutableStateFlow<User?>(User("Catalin", "Badulescu", "0725422494", "Romania", "badulescucatalin01@gmail.com", FlavorConfig.userType)) // Default value
    val userData: StateFlow<User?> = _userData

    fun fetchUserData(email: String) {
        val db = Firebase.firestore

        // Launch a coroutine in the ViewModel scope
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = db.collection("users")
                    .whereEqualTo("emailAddress", email)
                    .get()
                    .await() // Use `await` to wait for the query to complete

                if (result.documents.isNotEmpty()) {
                    val document = result.documents.first() // Assuming email is unique and there's only one document
                    val user = User(
                        firstName = document.getString("firstName").orEmpty(),
                        lastName = document.getString("lastName").orEmpty(),
                        emailAddress = document.getString("emailAddress").orEmpty(),
                        phoneNumber = document.getString("phoneNumber").orEmpty(),
                        country = document.getString("country").orEmpty(),
                        userType = document.getString("userType").orEmpty()
                    )
                    _userData.value = user // Update the state flow with the new user data
                } else {
                    _userData.value = null // No user found, handle as needed
                }
            } catch (e: Exception) {
                _userData.value = null // Error occurred, handle as needed
            }
        }
    }
}