package com.example.careiroapp.associacoes.ui.viewmodel

import com.example.careiroapp.associacoes.data.models.AssociacaoModel
import com.example.careiroapp.associacoes.data.models.AssociacaoProductModel

sealed class SingleAssociacaoUiState {
    data object None: SingleAssociacaoUiState()
    data object Loading: SingleAssociacaoUiState()
    data class Success(
        val associacao: AssociacaoModel,
        val products: List<AssociacaoProductModel> = emptyList()
    ): SingleAssociacaoUiState()
}