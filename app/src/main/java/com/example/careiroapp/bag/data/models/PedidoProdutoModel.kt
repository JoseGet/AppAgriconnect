package com.example.careiroapp.bag.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class PedidoProdutoModel(

    @SerializedName("pedido_id")
    @Expose
    val idPedido: Int = 0,

    @SerializedName("produto_id")
    @Expose
    val idProduto: UUID,

    @SerializedName("quantidade")
    @Expose
    val quantidade: Int
)