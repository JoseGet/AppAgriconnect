package com.example.careiroapp.feiras.ui.viewmodel

import com.example.careiroapp.feiras.data.models.FeiraModel

sealed class SingleFeiraUiState {
    data object None : SingleFeiraUiState()
    data object Loading : SingleFeiraUiState()
    data class Success(
        val feira: FeiraModel
    ) : SingleFeiraUiState()
}
