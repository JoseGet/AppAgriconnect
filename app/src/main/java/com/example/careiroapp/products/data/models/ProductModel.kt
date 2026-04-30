package com.example.careiroapp.products.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ProductModel(

    @SerializedName("id_produto")
    @Expose
    val id: UUID,

    @SerializedName("nome")
    @Expose
    val nomeProduto: String,

    @SerializedName("descricao")
    @Expose
    val descricao: String,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("disponivel")
    @Expose
    val disponivel: Boolean,

    @SerializedName("preco")
    @Expose
    val precoProduto: Float,

    @SerializedName("is_promocao")
    @Expose
    val isPromocao: Boolean,

    @SerializedName("preco_promocao")
    @Expose
    val precoPromocao: Double?,

    @SerializedName("fk_vendedor")
    @Expose
    val fkVendedor: UUID,

    @SerializedName("unidade_medida")
    @Expose
    val unidadeMedida: String
)