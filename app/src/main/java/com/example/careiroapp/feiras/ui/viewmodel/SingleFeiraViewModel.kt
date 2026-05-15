package com.example.careiroapp.feiras.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.feiras.domain.usecases.GetFeiraByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleFeiraViewModel @Inject constructor(
    private val getFeiraByIdUseCase: GetFeiraByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _singleFeiraUiState = MutableStateFlow<SingleFeiraUiState>(SingleFeiraUiState.None)
    var singleFeiraUiState: StateFlow<SingleFeiraUiState> = _singleFeiraUiState.asStateFlow()

    private val id: Int = savedStateHandle.get<Int>("feiraId")!!

    init {
        getFeiraById(id)
    }

    fun getFeiraById(id: Int) {
        _singleFeiraUiState.update { SingleFeiraUiState.Loading }
        viewModelScope.launch {
            try {
                val feira = getFeiraByIdUseCase.invoke(id)
                if (feira != null) {
                    _singleFeiraUiState.update { SingleFeiraUiState.Success(feira = feira) }
                } else {
                    _singleFeiraUiState.update { SingleFeiraUiState.None }
                }
            } catch (e: Exception) {
                _singleFeiraUiState.update { SingleFeiraUiState.None }
            }
        }
    }
}
