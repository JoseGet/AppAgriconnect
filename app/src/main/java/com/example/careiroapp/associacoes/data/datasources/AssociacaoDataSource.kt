package com.example.careiroapp.associacoes.data.datasources

import com.example.careiroapp.associacoes.data.models.AssociacaoModel
import com.example.careiroapp.data.network.api.AssociacaoApiService
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class AssociacaoDataSource @Inject constructor(
    private val associacaoApiService: AssociacaoApiService
) {
    suspend fun getAssociacoes(): MutableList<AssociacaoModel>? {
        return associacaoApiService.getAssociacoes().body()
    }

    suspend fun getAssociacaoById(id: UUID): Response<AssociacaoModel> {
        return associacaoApiService.getAssociacaoById(id)
    }

}