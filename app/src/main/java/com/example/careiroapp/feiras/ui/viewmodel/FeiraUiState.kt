package com.example.careiroapp.feiras.ui.viewmodel

import com.example.careiroapp.feiras.data.models.FeiraModel

data class FeiraUiState(
    val isLoading: Boolean = false,
    val feirasCardList: List<FeiraModel> = listOf(),
)