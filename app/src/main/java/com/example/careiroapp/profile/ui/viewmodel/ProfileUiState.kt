package com.example.careiroapp.profile.ui.viewmodel

import com.example.careiroapp.bag.data.models.Pedidos
import com.example.careiroapp.products.data.models.ProductModel

data class ProfileUiState(
    val isLoading: Boolean = false,
    val currentProfileModule: ProfileModules = ProfileModules.PEDIDOS,
    val favoriteItensList: List<ProductModel> = listOf(),
    val pedidosList: List<Pedidos> = listOf(),
    val selectedPedido: Pedidos? = null
)

enum class ProfileModules {
    PEDIDOS,
    FAVORITOS,
    ASSINATURAS
}