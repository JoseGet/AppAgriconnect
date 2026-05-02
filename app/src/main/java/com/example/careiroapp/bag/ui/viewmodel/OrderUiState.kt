package com.example.careiroapp.bag.ui.viewmodel

import com.example.careiroapp.products.data.models.ProductModel

data class OrderUiState(
    val isLoading: Boolean = false,
    val order: OrderModel = OrderModel(),
)
enum class PaymentType{
    PIX,
    DINHEIRO
}

data class OrderModel (
    val date: String = "",
    val time: String = "",
    val address: String = "",
    val paymentType: PaymentType? = null,
    val items: List<ProductModel> = emptyList(),
    val totalValue: Float = 0f,
    val pixPayload: String? = null,
    val pixQrCode: String? = null
)