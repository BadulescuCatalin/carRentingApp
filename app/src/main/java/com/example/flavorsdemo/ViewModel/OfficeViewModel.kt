package com.example.flavorsdemo.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.Office
import com.example.flavorsdemo.Repository.OfficeRepository
import com.example.flavorsdemo.View.screens.car
import com.example.flavorsdemo.View.screens.office
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfficeViewModel : ViewModel() {
    private val officeRepository = OfficeRepository()

    // Live data to observe cars
    val offices = officeRepository.getOffices().asLiveData()
    val officeIds = liveData(Dispatchers.IO) {
        val ids = officeRepository.getAllOfficesIds()
        emit(ids)
    }
    private val _uiState = MutableLiveData<String>()
    val uiState: LiveData<String> = _uiState
    suspend fun addOffice(officeToAdd: Office) {
        officeRepository.addOffice(officeToAdd,
            onSuccess = {
                _uiState.postValue("Car Added Successfully")
                office = Office()
            },
            onFailure = {
                _uiState.postValue("Failed to Add Car")
                office = Office()
            }
        )
    }
    fun updateOffice(officeToUpdate: Office) {
        viewModelScope.launch {
            officeRepository.updateOffice(officeToUpdate,
                onSuccess = { /* Handle success, e.g., navigate back or show a success message */
                    office = Office()
                },
                onFailure = { exception ->
                    // Handle failure, e.g., show an error message
                    Log.e("UpdateCar", "Failed to update car: ${exception.message}")
                    office = Office()
                }
            )
        }
    }

    fun deleteOffice(officeId: String) {
        viewModelScope.launch {
            officeRepository.deleteOffice(officeId,
                onSuccess = { /* Handle success, e.g., show a confirmation message */
                    office = Office()
                },
                onFailure = { exception ->
                    // Handle failure, e.g., show an error message
                    office = Office()
                    Log.e("DeleteCar", "Failed to delete car: ${exception.message}")
                }
            )
        }
    }
}