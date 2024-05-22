package com.example.flavorsdemo.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Repository.CarRepositoryImpl
import com.example.flavorsdemo.View.components.car
import kotlinx.coroutines.launch

class CarViewModelOwner : ViewModel() {
    private val repository = CarRepositoryImpl()

    private val _uiState = MutableLiveData<String>()
    val uiState: LiveData<String> = _uiState

    suspend fun addCar(carToAdd: Car) {
        repository.addCar(carToAdd,
            onSuccess = {
                _uiState.postValue("Car Added Successfully")
                car = Car()
            },
            onFailure = {
                _uiState.postValue("Failed to Add Car")
                car = Car()
            }
        )
    }
     fun updateCar(carToUpdate: Car) {
        viewModelScope.launch {
            repository.updateCar(carToUpdate,
                onSuccess = { /* Handle success, e.g., navigate back or show a success message */
                    car = Car()
                            },
                onFailure = { exception ->
                    // Handle failure, e.g., show an error message
                    Log.e("UpdateCar", "Failed to update car: ${exception.message}")
                    car = Car()
                }
            )
        }
    }

    fun deleteCar(carId: String) {
        viewModelScope.launch {
            repository.deleteCar(carId,
                onSuccess = { /* Handle success, e.g., show a confirmation message */
                            car = Car()},
                onFailure = { exception ->
                    // Handle failure, e.g., show an error message
                    car = Car()
                    Log.e("DeleteCar", "Failed to delete car: ${exception.message}")
                }
            )
        }
    }
}