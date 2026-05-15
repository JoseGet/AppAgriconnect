package com.example.careiroapp.associacoes.domain.usecases

import com.example.careiroapp.associacoes.data.models.AssociacaoModel
import com.example.careiroapp.associacoes.data.repositories.AssociacaoRepository
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class GetAssociacaoByIdUseCase @Inject constructor(
    private val associacaoRepository: AssociacaoRepository
) {
    suspend operator fun invoke(id: UUID): Response<AssociacaoModel> {
        return associacaoRepository.getAssociacaoById(id)
    }
}