package com.example.careiroapp.bag.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.data.room.entities.BagItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    private val repository: BagRepository
): ViewModel() {

    val totalPrice = repository.getTotalPrice().map { it ?: 0.0 }.asLiveData()
    val cartItems = repository.allItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addQuantity(productId: UUID) {
        viewModelScope.launch {
            try {
                repository.incrementQuantity(productId)
            } catch (e: Exception) {}
        }
    }

    fun decreaseQuantity(productId: UUID) {
        viewModelScope.launch {
            try {
                repository.decreaseQuantity(productId)
            } catch (e: Exception) {}
        }
    }

    fun removeProduct(bagItem: BagItem) {
        viewModelScope.launch {
            try {
                repository.removeFromBag(item = bagItem)
            } catch (e: Exception) {}
        }
    }
}