package com.example.careiroapp.products.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.data.dataStore.UserDataStore
import com.example.careiroapp.data.dataStore.model.UserDataStoreModel
import com.example.careiroapp.data.room.entities.BagItem
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
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
    userDataStore: UserDataStore
) : ViewModel() {

    private val TAG = "ProductsViewModel"

    private val _productUiState = MutableStateFlow(ProductsUiState())
    var productUiState: StateFlow<ProductsUiState> = _productUiState.asStateFlow()

    private var offset: Int = 0
    private val limit: Int = 20

    private var isInitializedByNavArg = false

    val userData: StateFlow<UserDataStoreModel> = userDataStore.getUserData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserDataStoreModel()
        )

    fun getProducts(isNecessaryLoadMore: Boolean) {

        if (_productUiState.value.productsCardList.isNotEmpty() && !isNecessaryLoadMore) {
            return
        }

        viewModelScope.launch {
            try {
                _productUiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                val currentList = productUiState.value.productsCardList
                val productsList = getProductsUseCase.invoke(offset, limit)

                val productsCount = getProductsCount()

                if (productsList?.isEmpty() == true) {
                    _productUiState.update {
                        it.copy(
                            endOfListReached = true
                        )
                    }
                    return@launch
                }

                val newList = currentList + (productsList ?: emptyList())

                _productUiState.update {
                    it.copy(
                        isLoading = false,
                        productsCardList = newList,
                        productsCount = productsCount
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
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

        if (nomeCategoria == null) {
            return
        }

        viewModelScope.launch {
            try {
                _productUiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                val currentList = productUiState.value.productsCardList
                val productsList =
                    getProductsByCategoriaUseCase.invoke(nomeCategoria, offset, limit)

                val productsCount = getProductsByCategoriaCount(nomeCategoria)

                if (productsList?.isEmpty() == true) {
                    _productUiState.update {
                        it.copy(
                            endOfListReached = true
                        )
                    }
                    return@launch
                }

                val newList = currentList + (productsList ?: emptyList())

                _productUiState.update {
                    it.copy(
                        isLoading = false,
                        productsCardList = newList,
                        productsCount = productsCount
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, e.toString())
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

    fun loadMoreProducts(function: () -> Unit) {
        offset = offset + limit
        function()
    }

    fun resetListState() {
        offset = 0
        _productUiState.update {
            it.copy(
                endOfListReached = false
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
                    quantity = 1
                )
                bagRepository.addToBag(bagItem, cpf)
            } catch (e: Exception) { }
        }
    }

    companion object {
        private const val TAG = "ProductsViewModel"
    }

}

