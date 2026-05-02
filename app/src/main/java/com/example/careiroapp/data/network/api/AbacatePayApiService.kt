package com.example.careiroapp.data.network.api

import com.example.careiroapp.bag.data.models.PixPaymentRequestBody
import com.example.careiroapp.bag.data.models.PixPaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AbacatePayApiService {

    @POST("abacatepay/payment/pix")
    suspend fun createPixPayment(
        @Body req: PixPaymentRequestBody
    ): Response<PixPaymentResponse>


}