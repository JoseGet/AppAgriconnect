package com.example.careiroapp.bag.data.repository

import com.example.careiroapp.bag.data.models.PixPaymentRequestBody
import com.example.careiroapp.bag.data.models.PixPaymentResponse
import com.example.careiroapp.data.network.api.AbacatePayApiService
import retrofit2.Response
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val abacatePayApiService: AbacatePayApiService
) {

    suspend fun createPixPayment(
        req: PixPaymentRequestBody
    ): Response<PixPaymentResponse> {
        return abacatePayApiService.createPixPayment(req)
    }


}