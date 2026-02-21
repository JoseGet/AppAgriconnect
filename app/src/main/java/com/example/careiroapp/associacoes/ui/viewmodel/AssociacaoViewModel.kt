package com.example.careiroapp.associacoes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.associacoes.data.models.AssociacaoModel
import com.example.careiroapp.associacoes.data.models.AssociacaoProductModel
import com.example.careiroapp.associacoes.domain.usecases.GetAssociacaoByIdUseCase
import com.example.careiroapp.associacoes.domain.usecases.GetAssociacoesUseCase
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.products.data.models.ProductModel
import com.example.careiroapp.products.domain.usecases.GetProductsByAssociacao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AssociacaoViewModel @Inject constructor(
    private val getAssociacoesUseCase: GetAssociacoesUseCase,
    private val getAssociacaoByIdUseCase: GetAssociacaoByIdUseCase,
    private val getProductsByAssociacao: GetProductsByAssociacao,
    private val bagRepository: BagRepository
): ViewModel() {

    private val _associacaoUiState = MutableStateFlow(AssociacaoUiState())
    var associacaoUiState: StateFlow<AssociacaoUiState> = _associacaoUiState.asStateFlow()

    init {
        getAssociacoes()
    }

    fun getAssociacoes() {
        viewModelScope.launch {
            _associacaoUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val associacoesList = getAssociacoesUseCase.invoke()?.map { associacao ->
                AssociacaoModel(
                    idAssociacao = associacao.idAssociacao,
                    nome = associacao.nome,
                    image = associacao.image,
                    dataHora = associacao.dataHora,
                    descricao = associacao.descricao,
                    endereco = associacao.endereco,
                    productorsList = associacao.productorsList
                )
            }

            _associacaoUiState.update {
                it.copy(
                    isLoading = false,
                    associacoesList = associacoesList ?: emptyList()
                )
            }

        }
    }

    fun getAssociacaoById(id: UUID) {
        viewModelScope.launch {
            _associacaoUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val selectedAssociacao = getAssociacaoByIdUseCase.invoke(id)
            val products = getProductsByAssociacao(id)

            _associacaoUiState.update {
                it.copy(
                    isLoading = false,
                    selectedAssociacao = selectedAssociacao,
                    productsList = products
                )
            }

        }
    }

    private suspend fun getProductsByAssociacao(idAssociacao: UUID): List<AssociacaoProductModel> {
        return getProductsByAssociacao.invoke(idAssociacao) ?: emptyList()
    }

    fun clearSelectedAssociacao() {
        viewModelScope.launch {
            _associacaoUiState.update {
                it.copy(
                    selectedAssociacao = null,
                    productsList = emptyList()
                )
            }
        }
    }

    fun addProductToBag(product: AssociacaoProductModel) {
        viewModelScope.launch {
            try {
                val bagItem = BagItem(
                    productId = product.idProduct,
                    name = product.nome,
                    price = product.preco,
                    imageUrl = product.image,
                    quantity = 1
                )
                bagRepository.addToBag(bagItem)
            } catch (e: Exception) { }
        }
    }

}