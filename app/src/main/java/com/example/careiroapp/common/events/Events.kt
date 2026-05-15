package com.example.careiroapp.common.events

sealed class Events {
    class None: Events()
    class PaymentPixConfirmed: Events()
    class ProductAddedToBag: Events()
}