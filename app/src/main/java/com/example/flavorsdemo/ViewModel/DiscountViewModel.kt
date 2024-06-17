package com.example.flavorsdemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.flavorsdemo.Model.Car
import com.example.flavorsdemo.Model.Discount
import com.example.flavorsdemo.Repository.DiscountRepositoryImpl
import com.example.flavorsdemo.View.components.car
import com.example.flavorsdemo.View.components.discountGlobal

class DiscountViewModel : ViewModel() {
    private val discountRepository = DiscountRepositoryImpl()
    val discounts = discountRepository.getDiscounts().asLiveData()
    suspend fun addDiscount(discountToAdd: Discount) {
        discountRepository.addDiscount(discountToAdd,
            onSuccess = {
                discountGlobal = Discount()
            },
            onFailure = {
                discountGlobal = Discount()
            }
        )
    }
    suspend fun deleteDiscount(discountToDelete: Discount) {
        discountRepository.deleteDiscount(discountToDelete.id,
            onSuccess = {
                discountGlobal = Discount()
            },
            onFailure = {
                discountGlobal = Discount()
            }
        )
    }
    suspend fun updateDiscount(discountToUpdate: Discount) {
        discountRepository.updateDiscount(discountToUpdate,
            onSuccess = {
                discountGlobal = Discount()
            },
            onFailure = {
                discountGlobal = Discount()
            }
        )
    }

    suspend fun updateDiscountsOfficeIds(discounts : List<Discount>, officeId: String) {
        discountRepository.updateDiscountOfficeIds(discounts, officeId,
            onSuccess = {
                discountGlobal = Discount()
            },
            onFailure = {
                discountGlobal = Discount()
            })
    }
}