package com.example.careiroapp.home.ui.viewmodel

import com.example.careiroapp.feiras.data.models.FeiraModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val feirasList: List<FeiraModel> = emptyList()
)