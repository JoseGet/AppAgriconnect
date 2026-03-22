package com.example.careiroapp.bag.ui.viewmodel

data class OrderUiState(
    val isLoading: Boolean = false,
    val order: OrderModel = OrderModel(),
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
    val orderId: Int = 0,
    val date: String = "",
    val time: String = "",
    val address: String = "",
    val paymentType: PaymentType? = null,
    val payerData: PayerData = PayerData()
)