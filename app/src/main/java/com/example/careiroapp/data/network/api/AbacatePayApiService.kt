package com.example.careiroapp.data.network.api

import com.example.careiroapp.bag.data.models.PixPaymentRequestBody
import com.example.careiroapp.bag.data.models.PixPaymentResponse
import com.example.careiroapp.bag.data.models.PixStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AbacatePayApiService {

    @POST("abacatepay/payment/pix")
    suspend fun createPixPayment(
        @Body req: PixPaymentRequestBody
    ): Response<PixPaymentResponse>

    @GET("abacatepay/payment/done/pix")
    suspend fun getPixPaymentById(
        @Query("id") id: String
    ): Response<PixStatusResponse>

}