package com.example.careiroapp.products.ui.viewmodel

import com.example.careiroapp.products.data.models.ProductModel

data class ProductsUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val productsCardList: List<ProductModel> = listOf(),
    val productsCount: Int? = 0,
    val filterNameActivate: String? = null,
    val endOfListReached: Boolean = false,
)