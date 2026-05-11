package com.example.careiroapp.bag.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PixStatusResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean,

    @SerializedName("data")
    @Expose
    val data: List<PixStatusData>
)


data class PixStatusData(

    @SerializedName("brCode")
    @Expose
    val brCode: String,

    @SerializedName("brCodeBase64")
    @Expose
    val brCodeBase64: String

)