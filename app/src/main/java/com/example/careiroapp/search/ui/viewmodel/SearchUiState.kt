package com.example.careiroapp.search.ui.viewmodel

import com.example.careiroapp.products.data.models.ProductModel

data class SearchUiState(
    val isLoading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val query: String = ""
)
