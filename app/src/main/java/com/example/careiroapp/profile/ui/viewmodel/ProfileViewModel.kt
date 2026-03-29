package com.example.careiroapp.profile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.models.Pedidos
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.bag.data.repository.PedidoRepository
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity
import com.example.careiroapp.loginCadastro.domain.usecases.GetFavoritesUseCase
import com.example.careiroapp.products.data.models.ProductModel
import com.example.careiroapp.profile.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val bagRepository: BagRepository,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val userRepository: UserRepository,
    private val pedidoRepository: PedidoRepository
): ViewModel() {

    private val _profileUiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    var profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    val userData: Flow<UserEntity?> = userRepository.getUserData()
    fun setCurrentModule(newModule: ProfileModules) {
        _profileUiState.update { it.copy(currentProfileModule = newModule) }
    }

    fun getFavoritesProducts(
        cpf: String
    ) {
        if (_profileUiState.value.isLoading) return
        
        viewModelScope.launch {
            try {
                _profileUiState.update { it.copy(isLoading = true) }

                val favoriteRes = getFavoritesUseCase.invoke(cpf)

                if (favoriteRes.isSuccessful) {
                    val favoriteList = favoriteRes.body()
                    _profileUiState.update { it.copy(
                        favoriteItensList = favoriteList?.toList() ?: emptyList()
                    ) }
                }

            } catch (e: Exception) {
                Log.e("ProfileVM", "Error loading favorites", e)
            } finally {
                _profileUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun getPedidos() {
        if (_profileUiState.value.isLoading) return

        viewModelScope.launch {
            try {
                _profileUiState.update { it.copy(isLoading = true) }

                val pedidos = pedidoRepository.getPedidos()

                if (pedidos.isSuccessful) {
                    val pedidosList = pedidos.body()
                    _profileUiState.update {
                        it.copy(
                        pedidosList = pedidosList?.toList() ?: emptyList()
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileVM", "Error loading pedidos", e)
            } finally {
                _profileUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateSelectedPedido(pedido: Pedidos) {
        _profileUiState.update {
            it.copy(
                selectedPedido = pedido
            )
        }
    }

    fun clearSelectedOrder() {
        _profileUiState.update {
            it.copy(
                selectedPedido = null
            )
        }
    }

    fun addProductToBag(product: ProductModel, cpf: String) {
        viewModelScope.launch {
            try {
                val bagItem = BagItem(
                    productId = product.id,
                    userId = cpf,
                    name = product.nomeProduto,
                    price = product.precoProduto,
                    imageUrl = product.image,
                    quantity = 1,
                )
                bagRepository.addToBag(bagItem, cpf)
            } catch (e: Exception) { }
        }
    }
}
