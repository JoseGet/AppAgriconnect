package com.example.careiroapp.bag.ui.viewmodel

sealed class CheckoutUiEvent() {
    class None: CheckoutUiEvent()
    object PixPaymentDone : CheckoutUiEvent()
}