package com.example.careiroapp.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.feiras.domain.usecases.GetFeirasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeirasUseCase: GetFeirasUseCase
): ViewModel(

) {
    private val _uiState = MutableStateFlow(HomeUiState())
    var uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        setupView()
    }

    private fun setupView() {
        getFeirasList()
    }

    private fun getFeirasList() {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }

            try {
                val feirasListRequest = getFeirasUseCase.invoke()

                if (feirasListRequest.isSuccessful) {
                    val feirasList = feirasListRequest.body()
                    _uiState.update { it.copy(feirasList = feirasList?.toList() ?: emptyList()) }
                }

            } catch (e: Exception) {

            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

}