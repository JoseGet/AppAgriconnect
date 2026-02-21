package com.example.careiroapp.bag.ui.viewmodel

data class BagUiState(
    val isLoading: Boolean = false,
    val checkoutStep: CheckoutStep = CheckoutStep.ONE
)

enum class CheckoutStep() {
    ONE,
    TWO,
    THREE,
    FINAL
}
