package com.example.careiroapp.bag.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreatePedidoResponse (

    @SerializedName("pedido_id")
    @Expose
    val id: Int = 0,

    @SerializedName("status")
    @Expose
    val status: String? = null,

    @SerializedName("fk_cliente")
    @Expose
    val fkCliente: String? = null,

    @SerializedName("pix_payload")
    @Expose
    val pixPayload: String? = null,

    @SerializedName("valor_total")
    @Expose
    val valorTotal: Double = 0.0,

    @SerializedName("payment_type")
    @Expose
    val paymentType: String? = null,

    @SerializedName("retirada_local")
    @Expose
    val retiradaLocal: String? = null,

    @SerializedName("retirada_data")
    @Expose
    val retiradaData: String? = null,

    @SerializedName("retirada_hora")
    @Expose
    val retiradaHora: String? = null


)