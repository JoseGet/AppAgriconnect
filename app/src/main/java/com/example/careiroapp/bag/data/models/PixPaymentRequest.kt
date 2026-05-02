package com.example.careiroapp.bag.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PixPaymentRequestBody(

    @SerializedName("method")
    @Expose
    val method: String,

    @SerializedName("data")
    @Expose
    val data: PaymentDataRequest,
)


data class PaymentDataRequest(

    @SerializedName("amount")
    @Expose
    val amount: Int,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("expiresIn")
    @Expose
    val expiresIn: Int,

    @SerializedName("customer")
    @Expose
    val customer: Customer
)

data class Customer(

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("taxId")
    @Expose
    val taxId: String,

    @SerializedName("cellphone")
    @Expose
    val cellphone: String
)