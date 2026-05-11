package com.example.careiroapp.home.ui.viewmodel

import com.example.careiroapp.feiras.data.models.FeiraModel
import com.example.careiroapp.products.data.models.ProductModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val feirasList: List<FeiraModel> = emptyList(),
    val featuredProducts: List<ProductModel> = emptyList()
)