package com.example.careiroapp.products.ui.viewmodel

import com.example.careiroapp.products.data.models.ProductModel

sealed class SingleProductUiState {
    data object None : SingleProductUiState()
    data object Loading : SingleProductUiState()
    data class Success(
        val product: ProductModel,
        val productorName: String = "",
        val isProductFavorite: Boolean? = null
    ) : SingleProductUiState()
}
