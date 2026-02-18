package com.example.careiroapp.products.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class AddFavoritesReqModel(
    @SerializedName("produto_id")
    @Expose
    val productId: UUID?
)