package com.example.careiroapp.feiras.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FeiraModel(

    @SerializedName("id_feira")
    @Expose
    val id: Int,

    @SerializedName("nome")
    @Expose
    val nome: String,

    @SerializedName("data_hora")
    @Expose
    val dataHora: String,

    @SerializedName("descricao")
    @Expose
    val descricao: String,

    @SerializedName("localizacao")
    @Expose
    val localizacao: String,

    @SerializedName("image")
    @Expose
    val image: String
)