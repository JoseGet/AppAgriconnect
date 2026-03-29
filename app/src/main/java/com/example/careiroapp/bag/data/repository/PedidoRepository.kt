package com.example.careiroapp.bag.data.repository

import com.example.careiroapp.bag.data.models.CreatePedidoResponse
import com.example.careiroapp.bag.data.models.PedidoBody
import com.example.careiroapp.data.network.api.PedidoApiService
import retrofit2.Response
import javax.inject.Inject

class PedidoRepository @Inject constructor(
    private val pedidoApiService: PedidoApiService
) {

    suspend fun createPedido(
        pedido: PedidoBody
    ): Response<CreatePedidoResponse> {
        return pedidoApiService.criarPedido(
            pedido = pedido
        )
    }

}