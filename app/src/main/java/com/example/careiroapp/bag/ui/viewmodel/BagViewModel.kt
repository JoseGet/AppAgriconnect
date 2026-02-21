package com.example.careiroapp.bag.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.repository.CartRepository
import com.example.careiroapp.data.room.entities.CartItem
import com.example.careiroapp.products.data.models.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    private val repository: CartRepository
): ViewModel() {

    val cartItems = repository.allItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addProduct(product: ProductModel) {
        viewModelScope.launch {
            repository.addToCart(
                CartItem(
                    productId = product.id,
                    name = product.nomeProduto,
                    price = product.precoProduto,
                    imageUrl = product.image,
                    quantity = 1
                )
            )
        }
    }
}