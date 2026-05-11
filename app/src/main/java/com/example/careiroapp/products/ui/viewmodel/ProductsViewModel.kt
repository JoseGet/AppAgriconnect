package com.example.careiroapp.products.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity
import com.example.careiroapp.loginCadastro.domain.usecases.AddToFavoritesUseCase
import com.example.careiroapp.loginCadastro.domain.usecases.GetFavoritesUseCase
import com.example.careiroapp.loginCadastro.domain.usecases.RemoveFromFavoritesUseCase
import com.example.careiroapp.products.data.models.AddFavoritesReqModel
import com.example.careiroapp.products.data.models.ProductModel
import com.example.careiroapp.products.domain.usecases.GetProductByIdUseCase
import com.example.careiroapp.products.domain.usecases.GetProductVendedorUseCase
import com.example.careiroapp.products.domain.usecases.GetProductsByCategoriaCountUseCase
import com.example.careiroapp.products.domain.usecases.GetProductsByCategoriaUseCase
import com.example.careiroapp.products.domain.usecases.GetProductsCountUseCase
import com.example.careiroapp.products.domain.usecases.GetProductsUseCase
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
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductsByCategoriaUseCase: GetProductsByCategoriaUseCase,
    private val getProductsCountUseCase: GetProductsCountUseCase,
    private val getProductsByCategoriaCountUseCase: GetProductsByCategoriaCountUseCase,
    private val getProductVendedorUseCase: GetProductVendedorUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val bagRepository: BagRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val TAG = "ProductsViewModel"

    private val _productUiState = MutableStateFlow(ProductsUiState())
    var productUiState: StateFlow<ProductsUiState> = _productUiState.asStateFlow()

    private var offset: Int = 0
    private val limit: Int = 20

    private var isInitializedByNavArg = false

    val userData: Flow<UserEntity?> = userRepository.getUserData()

    fun getProducts(isNecessaryLoadMore: Boolean) {
        val state = _productUiState.value

        if (state.productsCardList.isNotEmpty() && !isNecessaryLoadMore) return
        if (isNecessaryLoadMore && (state.isLoadingMore || state.endOfListReached)) return

        if (isNecessaryLoadMore) offset += limit

        viewModelScope.launch {
            try {
                _productUiState.update {
                    it.copy(
                        isLoading = !isNecessaryLoadMore,
                        isLoadingMore = isNecessaryLoadMore
                    )
                }

                val productsList = getProductsUseCase.invoke(offset, limit)
                val productsCount = getProductsCount()

                if (productsList?.isEmpty() == true) {
                    _productUiState.update { it.copy(endOfListReached = true) }
                    return@launch
                }

                _productUiState.update {
                    it.copy(
                        productsCardList = if (isNecessaryLoadMore) it.productsCardList + (productsList ?: emptyList())
                                          else productsList ?: emptyList(),
                        productsCount = productsCount
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            } finally {
                _productUiState.update { it.copy(isLoading = false, isLoadingMore = false) }
            }
        }
    }

    fun getProductById(
        cpf: String,
        id: UUID
    ) {
        viewModelScope.launch {
            try {
                _productUiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                val getFavoritesReq = getFavoritesUseCase.invoke(cpf)
                val selectedItem = getProductByIdUseCase.invoke(id)
                val productorName = getVendedorName(selectedItem?.fkVendedor)
                val isFavorite = isItemFavorite(
                    productId = id,
                    body = getFavoritesReq.body()
                )

                _productUiState.update {
                    it.copy(
                        isLoading = false,
                        selectedProduct = selectedItem,
                        productorName = productorName,
                        isSelectedProductFavorite = isFavorite
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun getProductsByCategoria(nomeCategoria: String?, isNecessaryLoadMore: Boolean) {
        if (nomeCategoria == null) return

        val state = _productUiState.value
        if (isNecessaryLoadMore && (state.isLoadingMore || state.endOfListReached)) return

        if (isNecessaryLoadMore) offset += limit

        viewModelScope.launch {
            try {
                _productUiState.update {
                    it.copy(
                        isLoading = !isNecessaryLoadMore,
                        isLoadingMore = isNecessaryLoadMore
                    )
                }

                val productsList = getProductsByCategoriaUseCase.invoke(nomeCategoria, offset, limit)
                val productsCount = getProductsByCategoriaCount(nomeCategoria)

                if (productsList?.isEmpty() == true) {
                    _productUiState.update { it.copy(endOfListReached = true) }
                    return@launch
                }

                _productUiState.update {
                    it.copy(
                        productsCardList = if (isNecessaryLoadMore) it.productsCardList + (productsList ?: emptyList())
                                          else productsList ?: emptyList(),
                        productsCount = productsCount
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            } finally {
                _productUiState.update { it.copy(isLoading = false, isLoadingMore = false) }
            }
        }
    }

    private suspend fun getProductsCount(): Int? = getProductsCountUseCase.invoke()

    private suspend fun getProductsByCategoriaCount(nomeCategoria: String): Int? =
        getProductsByCategoriaCountUseCase.invoke(nomeCategoria)

    fun updateFilterActivate(filterName: String?) {
        _productUiState.update {
            it.copy(
                filterNameActivate = filterName
            )
        }
    }

    fun resetListState() {
        offset = 0
        _productUiState.update {
            it.copy(
                endOfListReached = false,
                isLoadingMore = false
            )
        }
    }

    fun cleanProductsList() {
        viewModelScope.launch {
            _productUiState.update {
                it.copy(
                    productsCardList = listOf()
                )
            }
        }
    }

    fun cleanSelectedProduct() {
        viewModelScope.launch {
            _productUiState.update {
                it.copy(
                    selectedProduct = null,
                    isSelectedProductFavorite = null
                )
            }
        }
    }

    fun initializeFilter(categoryFromNav: String?) {
        if (isInitializedByNavArg) return

        updateFilterActivate(categoryFromNav)
        isInitializedByNavArg = true
    }

    suspend fun getVendedorName(idVendedor: UUID?): String {
        return getProductVendedorUseCase.invoke(idVendedor)?.nome ?: ""
    }

    fun addProductToFavorites(
        cpf: String
    ) {
        viewModelScope.launch {
            try {

                val productIdBody = AddFavoritesReqModel(
                    productId = productUiState.value.selectedProduct?.id
                )

                val addToFavoritesReq = addToFavoritesUseCase.invoke(
                    cpf = cpf,
                    productId = productIdBody
                )

                if (addToFavoritesReq.isSuccessful) {
                    _productUiState.update { it.copy(isSelectedProductFavorite = true) }
                }

            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun removeProductFromFavorites(
        cpf: String
    ) {
        viewModelScope.launch {
            try {

                val productIdBody = AddFavoritesReqModel(
                    productId = productUiState.value.selectedProduct?.id
                )

                val removeFromFavoritesReq = removeFromFavoritesUseCase.invoke(
                    cpf = cpf,
                    productId = productIdBody
                )

                if (removeFromFavoritesReq.isSuccessful) {
                    _productUiState.update { it.copy(isSelectedProductFavorite = false) }
                }

            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            }
        }
    }

    private fun isItemFavorite(
        productId: UUID,
        body: MutableList<ProductModel>?
    ): Boolean {
        val favoriteIdList = body?.map { it.id }
        if (favoriteIdList?.contains(productId) == true) {
            return true
        }

        return false
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
            } catch (e: Exception) { }
        }
    }

    companion object {
        private const val TAG = "ProductsViewModel"
    }

}

