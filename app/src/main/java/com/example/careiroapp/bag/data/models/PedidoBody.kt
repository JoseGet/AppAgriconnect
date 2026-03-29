package com.example.careiroapp.bag.data.models

import com.example.careiroapp.bag.ui.viewmodel.PaymentType
import com.google.gson.annotations.SerializedName

data class PedidoBody(
    @SerializedName("valor_total")
    val valorTotal: Float,

    @SerializedName("produtos")
    val produtos: List<PedidoProdutoModel>,

    @SerializedName("fk_feira")
    val feiraFk: Int = 13,

    @SerializedName("payment_type")
    val paymentType: PaymentType?,

    @SerializedName("retirada_local")
    val retiradaLocal: String,

    @SerializedName("retirada_data")
    val retiradaData: String,

    @SerializedName("retirada_hora")
    val retiradaHora: String,
)

