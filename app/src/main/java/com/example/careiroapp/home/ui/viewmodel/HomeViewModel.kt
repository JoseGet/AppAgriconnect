package com.example.careiroapp.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.feiras.domain.usecases.GetFeirasUseCase
import com.example.careiroapp.products.domain.usecases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeirasUseCase: GetFeirasUseCase,
    private val getProductsUseCase: GetProductsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    var uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        setupView()
    }

    private fun setupView() {
        getFeirasList()
        getFeaturedProducts()
    }

    private fun getFeirasList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isFairsLoading = true) }
            try {
                val feirasListRequest = getFeirasUseCase.invoke()
                if (feirasListRequest.isSuccessful) {
                    val feirasList = feirasListRequest.body()
                    _uiState.update { it.copy(feirasList = feirasList?.toList() ?: emptyList()) }
                }
            } catch (e: Exception) {
            } finally {
                _uiState.update { it.copy(isFairsLoading = false) }
            }
        }
    }

    private fun getFeaturedProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isFeaturedProductsLoading = true) }
            try {
                val products = getProductsUseCase.invoke(offset = 0, limit = 2)
                _uiState.update { it.copy(featuredProducts = (products ?: emptyList()).take(2)) }
            } catch (e: Exception) {
            } finally {
                _uiState.update { it.copy(isFeaturedProductsLoading = false) }
            }
        }
    }

}