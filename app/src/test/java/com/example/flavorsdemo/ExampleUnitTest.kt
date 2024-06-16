package com.example.flavorsdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Repository.CarRepository
import com.example.flavorsdemo.Repository.CarRepositoryImpl
import com.example.flavorsdemo.Utils.checkEmail
import com.example.flavorsdemo.Utils.isValidPassword
import com.example.flavorsdemo.Utils.isValidPhoneNumber
import com.example.flavorsdemo.ViewModel.CarViewModel
import kotlinx.coroutines.flow.flowOf


import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Captor
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testPhoneNumberIsCorrectRO() {
        val res = isValidPhoneNumber("0725422494", "RO")
        assert(res)
    }
    @Test
    fun testPhoneNumberIsIncorrectRO() {
        val res = isValidPhoneNumber("2072542294", "RO")
        assert(!res)
    }
    @Test
    fun testPhoneNumberIsCorrectCA() {
        val res = isValidPhoneNumber("5145555555", "CA")
        assert(res)
    }
    @Test
    fun testPhoneNumberIsCorrectUS() {
        val res = isValidPhoneNumber("+447389643364", "US")
        assert(res)
    }
    @Test
    fun testPasswordIsCorrect() {
        val res = isValidPassword("Password123!")
        assert(res)
    }

    @Test
    fun testPasswordIsIncorrect() {
        val res = isValidPassword("password123!")
        assert(!res)
    }

    @Test
    fun testEmailIsCorrect() {
        val res = checkEmail("")
        assert(!res)
    }
}

