package com.example.careiroapp.data.network.api

import com.example.careiroapp.bag.data.models.CreatePedidoResponse
import com.example.careiroapp.bag.data.models.Pedidos
import com.example.careiroapp.bag.data.models.PedidoBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PedidoApiService {

    @POST("pedido/cadastro")
    suspend fun criarPedido(
        @Body pedido: PedidoBody
    ): Response<CreatePedidoResponse>

    @GET("pedido/")
    suspend fun getPedidos(): Response<List<Pedidos>>

}