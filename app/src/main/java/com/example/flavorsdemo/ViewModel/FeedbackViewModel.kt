package com.example.flavorsdemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.flavorsdemo.Model.Feedback
import com.example.flavorsdemo.Repository.FeedbackRepositoryImpl

class FeedbackViewModel : ViewModel() {
    private val feedbackRepository = FeedbackRepositoryImpl()
    val feedbacks = feedbackRepository.getFeedbacks().asLiveData()

    suspend fun addFeedback(feedback: Feedback) {
        feedbackRepository.addFeedback(feedback  )
    }

}