package com.example.careiroapp.bag.ui.viewmodel

import com.example.careiroapp.products.data.models.ProductModel

sealed class CheckoutUiState {
    object Loading: CheckoutUiState()
    data class None(
        val order: OrderModel = OrderModel(),
        val checkoutStep: CheckoutStep = CheckoutStep.ONE,
    ): CheckoutUiState()
    data class Success(
        val isOrderComplete: Boolean,
        val isPaymentPixDone: Boolean = false
    ): CheckoutUiState()
}
enum class PaymentType{
    PIX,
    DINHEIRO
}

enum class OrderState{
    PENDENTE,
    CONFIRMADO,
    EXPIRADO
}

enum class CheckoutStep {
    ONE,
    TWO,
    FINAL
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