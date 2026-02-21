package com.example.careiroapp.bag.ui.viewmodel

data class OrderUiState(
    val isLoading: Boolean = false,
    val order: OrderModel = OrderModel(),
    val payerData: PayerData = PayerData(),
    val paymentType: PaymentType? = null,
)
enum class PaymentType{
    PIX,
    CASH
}

data class PayerData(
    val email: String = "",
    val name: String = "",
    val telefone: String = ""
)

data class OrderModel (
    val date: String = "",
    val time: String = "",
    val address: String = ""
)