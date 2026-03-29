package com.example.careiroapp.bag.data.models

import com.example.careiroapp.bag.ui.viewmodel.PaymentType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Pedidos(
    @SerializedName("pedido_id")
    @Expose
    val id: Int,

    @SerializedName("status")
    @Expose
    val status: String?,

    @SerializedName("valor_total")
    @Expose
    val valorTotal: Double,

    @SerializedName("produtos_no_pedido")
    @Expose
    val itens: List<ItemPedido>,

    @SerializedName("payment_type")
    @Expose
    val paymentType: PaymentType?,

    @SerializedName("retirada_local")
    @Expose
    val retiradaLocal: String,

    @SerializedName("retirada_data")
    @Expose
    val retiradaData: String,

    @SerializedName("retirada_hora")
    @Expose
    val retiradaHora: String,
)

data class ItemPedido(
    @SerializedName("id_item")
    @Expose val id: UUID,

    @SerializedName("quantidade")
    @Expose val quantidade: Int?,

    @SerializedName("produto")
    @Expose val produto: Produto?
)

data class Produto(
    @SerializedName("id_produto")
    @Expose val id: UUID,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("nome")
    @Expose val nome: String?,

    @SerializedName("preco")
    @Expose val preco: Double?,
)