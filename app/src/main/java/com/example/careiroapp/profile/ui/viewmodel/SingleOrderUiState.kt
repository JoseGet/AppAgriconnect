package com.example.careiroapp.profile.ui.viewmodel

import com.example.careiroapp.bag.data.models.Pedidos

sealed class SingleOrderUiState {
    data object None : SingleOrderUiState()
    data object Loading : SingleOrderUiState()
    data class Success(val pedido: Pedidos) : SingleOrderUiState()
}
