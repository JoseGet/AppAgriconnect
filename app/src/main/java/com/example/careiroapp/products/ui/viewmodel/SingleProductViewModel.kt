package com.example.careiroapp.products.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.common.events.Events
import com.example.careiroapp.common.events.NotificationEvents
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity
import com.example.careiroapp.loginCadastro.domain.usecases.AddToFavoritesUseCase
import com.example.careiroapp.loginCadastro.domain.usecases.GetFavoritesUseCase
import com.example.careiroapp.loginCadastro.domain.usecases.RemoveFromFavoritesUseCase
import com.example.careiroapp.products.data.models.AddFavoritesReqModel
import com.example.careiroapp.products.data.models.ProductModel
import com.example.careiroapp.products.domain.usecases.GetProductByIdUseCase
import com.example.careiroapp.products.domain.usecases.GetProductVendedorUseCase
import com.example.careiroapp.profile.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SingleProductViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductVendedorUseCase: GetProductVendedorUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val bagRepository: BagRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _singleProductUiState = MutableStateFlow<SingleProductUiState>(SingleProductUiState.None)
    var singleProductUiState: StateFlow<SingleProductUiState> = _singleProductUiState.asStateFlow()

    val userData: Flow<UserEntity?> = userRepository.getUserData()

    private val id: UUID = UUID.fromString(savedStateHandle.get<String>("productId")!!)

    init {
        viewModelScope.launch {
            val cpf = userRepository.getUserData().first()?.cpf ?: ""
            loadProduct(id, cpf)
        }
    }

    private suspend fun loadProduct(id: UUID, cpf: String) {
        _singleProductUiState.update { SingleProductUiState.Loading }
        try {
            val favoritesReq = getFavoritesUseCase.invoke(cpf)
            val product = getProductByIdUseCase.invoke(id)
            val productorName = getProductVendedorUseCase.invoke(product?.fkVendedor)?.nome ?: ""
            val isFavorite = isItemFavorite(id, favoritesReq.body())

            if (product != null) {
                _singleProductUiState.update {
                    SingleProductUiState.Success(
                        product = product,
                        productorName = productorName,
                        isProductFavorite = isFavorite
                    )
                }
            } else {
                _singleProductUiState.update { SingleProductUiState.None }
            }
        } catch (e: Exception) {
            _singleProductUiState.update { SingleProductUiState.None }
        }
    }

    fun addProductToFavorites(cpf: String) {
        val state = _singleProductUiState.value as? SingleProductUiState.Success ?: return
        viewModelScope.launch {
            try {
                val req = addToFavoritesUseCase.invoke(
                    cpf = cpf,
                    productId = AddFavoritesReqModel(productId = state.product.id)
                )
                if (req.isSuccessful) {
                    _singleProductUiState.update { state.copy(isProductFavorite = true) }
                }
            } catch (e: Exception) { }
        }
    }

    fun removeProductFromFavorites(cpf: String) {
        val state = _singleProductUiState.value as? SingleProductUiState.Success ?: return
        viewModelScope.launch {
            try {
                val req = removeFromFavoritesUseCase.invoke(
                    cpf = cpf,
                    productId = AddFavoritesReqModel(productId = state.product.id)
                )
                if (req.isSuccessful) {
                    _singleProductUiState.update { state.copy(isProductFavorite = false) }
                }
            } catch (e: Exception) { }
        }
    }

    fun addProductToBag(product: ProductModel, cpf: String) {
        viewModelScope.launch {
            try {
                val bagItem = BagItem(
                    productId = product.id,
                    name = product.nomeProduto,
                    price = product.precoProduto,
                    imageUrl = product.image,
                    userId = cpf,
                    quantity = 1,
                )
                bagRepository.addToBag(bagItem, cpf)
                NotificationEvents.sendEvent(Events.ProductAddedToBag())
            } catch (e: Exception) { }
        }
    }

    private fun isItemFavorite(productId: UUID, body: MutableList<ProductModel>?): Boolean {
        val favoriteIdList = body?.map { it.id }
        return favoriteIdList?.contains(productId) == true
    }
}
