package com.example.careiroapp.profile.ui.viewmodel

import com.example.careiroapp.products.data.models.ProductModel
import java.util.Collections.emptyList

data class ProfileUiState(
    val isLoading: Boolean = false,
    val currentProfileModule: ProfileModules = ProfileModules.PEDIDOS,
    val favoriteItensList: List<ProductModel> = listOf()
)

enum class ProfileModules {
    PEDIDOS,
    FAVORITOS,
    ASSINATURAS
}