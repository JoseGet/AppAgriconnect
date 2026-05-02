package com.example.careiroapp.bag.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PixPaymentResponse(

    @SerializedName("data")
    @Expose
    val data: PaymentDataResponse,

    @SerializedName("success")
    @Expose
    val success: Boolean,

    @SerializedName("error")
    @Expose
    val error: String?

)

data class PaymentDataResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("amount")
    val amount: Long,

    @SerializedName("status")
    val status: String,

    @SerializedName("devMode")
    val devMode: Boolean,

    @SerializedName("brCode")
    val brCode: String,

    @SerializedName("brCodeBase64")
    val brCodeBase64: String,

    @SerializedName("platformFee")
    val platformFee: Int,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,

    @SerializedName("expiresAt")
    val expiresAt: String,
)
