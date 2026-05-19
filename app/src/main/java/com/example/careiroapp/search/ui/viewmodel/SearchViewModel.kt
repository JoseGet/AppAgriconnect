package com.example.careiroapp.search.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.common.events.Events
import com.example.careiroapp.common.events.NotificationEvents
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity
import com.example.careiroapp.products.data.models.ProductModel
import com.example.careiroapp.products.domain.usecases.GetProductsUseCase
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
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductsUseCase: GetProductsUseCase,
    private val bagRepository: BagRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val query: String = checkNotNull(savedStateHandle["query"])

    private val _uiState = MutableStateFlow(SearchUiState(query = query))
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    val userData: Flow<UserEntity?> = userRepository.getUserData()

    init {
        searchProducts()
    }

    private fun searchProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val allProducts = mutableListOf<ProductModel>()
                var offset = 0
                val limit = 100
                while (true) {
                    val page = getProductsUseCase.invoke(offset, limit) ?: break
                    allProducts.addAll(page)
                    if (page.size < limit) break
                    offset += limit
                }
                _uiState.update {
                    it.copy(
                        products = allProducts.filter { p ->
                            p.nomeProduto.contains(query, ignoreCase = true)
                        }
                    )
                }
            } catch (e: Exception) {
                // keep empty list
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
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
}
