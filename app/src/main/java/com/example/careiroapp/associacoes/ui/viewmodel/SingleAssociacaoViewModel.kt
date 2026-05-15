package com.example.careiroapp.associacoes.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.associacoes.data.models.AssociacaoProductModel
import com.example.careiroapp.associacoes.domain.usecases.GetAssociacaoByIdUseCase
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.common.events.Events
import com.example.careiroapp.common.events.NotificationEvents
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity
import com.example.careiroapp.products.domain.usecases.GetProductsByAssociacao
import com.example.careiroapp.profile.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SingleAssociacaoViewModel @Inject constructor(
    private val getAssociacaoByIdUseCase: GetAssociacaoByIdUseCase,
    private val getProductsByAssociacao: GetProductsByAssociacao,
    private val bagRepository: BagRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _singleAssociacaoUiState = MutableStateFlow<SingleAssociacaoUiState>(SingleAssociacaoUiState.None)

    var singleAssociacaoUiState: StateFlow<SingleAssociacaoUiState> = _singleAssociacaoUiState.asStateFlow()

    val userData: Flow<UserEntity?> = userRepository.getUserData()

    private val id: UUID = UUID.fromString(savedStateHandle.get<String>("associacaoId")!!)

    init {
        getAssociacaoById(id)
    }

    fun getAssociacaoById(id: UUID) {
        _singleAssociacaoUiState.update { SingleAssociacaoUiState.Loading }
        viewModelScope.launch {
            try {

                val response = getAssociacaoByIdUseCase.invoke(id)

                if (response.isSuccessful) {

                    val associacao = response.body()!!
                    val products = getProductsByAssociacao(id)

                    _singleAssociacaoUiState.update {
                        SingleAssociacaoUiState.Success(associacao = associacao, products = products)
                    }
                }
            } catch (e: Exception) {
                _singleAssociacaoUiState.update { SingleAssociacaoUiState.None }
            }
        }
    }

    private suspend fun getProductsByAssociacao(idAssociacao: UUID): List<AssociacaoProductModel> {
        return getProductsByAssociacao.invoke(idAssociacao) ?: emptyList()
    }

    fun addProductToBag(product: AssociacaoProductModel, cpf: String) {
        viewModelScope.launch {
            try {
                val bagItem = BagItem(
                    productId = product.idProduct,
                    name = product.nome,
                    price = product.preco,
                    imageUrl = product.image,
                    quantity = 1,
                    userId = cpf
                )
                bagRepository.addToBag(bagItem, cpf)
                NotificationEvents.sendEvent(Events.ProductAddedToBag())
            } catch (e: Exception) { }
        }
    }

}