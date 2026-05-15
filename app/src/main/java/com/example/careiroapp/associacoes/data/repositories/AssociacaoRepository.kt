package com.example.careiroapp.associacoes.data.repositories

import com.example.careiroapp.associacoes.data.datasources.AssociacaoDataSource
import com.example.careiroapp.associacoes.data.models.AssociacaoModel
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class AssociacaoRepository @Inject constructor(
    private val associacaoDataSource: AssociacaoDataSource
) {

    suspend fun getAssociacoes(): MutableList<AssociacaoModel>? {
        return associacaoDataSource.getAssociacoes()
    }

    suspend fun getAssociacaoById(id: UUID): Response<AssociacaoModel> {
        return associacaoDataSource.getAssociacaoById(id)
    }

}