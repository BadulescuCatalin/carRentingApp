package com.example.flavorsdemo.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Repository.CarRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {
    private val carRepository = CarRepositoryImpl()

    // Live data to observe cars
    val cars = carRepository.getCars().asLiveData()
    val carIds = liveData(Dispatchers.IO) {
        val ids = carRepository.getAllCarIds()
        emit(ids)
    }
}