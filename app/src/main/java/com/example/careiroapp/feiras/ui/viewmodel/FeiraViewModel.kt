package com.example.careiroapp.feiras.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.feiras.data.models.FeiraModel
import com.example.careiroapp.feiras.domain.usecases.GetFeiraByIdUseCase
import com.example.careiroapp.feiras.domain.usecases.GetFeirasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeiraViewModel @Inject constructor(
    private val getFeirasUseCase: GetFeirasUseCase,
    private val getFeiraByIdUseCase: GetFeiraByIdUseCase
): ViewModel() {
    private val _feiraUiState: MutableStateFlow<FeiraUiState> = MutableStateFlow(FeiraUiState())
    var feiraUiState: StateFlow<FeiraUiState> = _feiraUiState.asStateFlow()

    init {
        getFeiras()
    }

    fun getFeiras() {
        viewModelScope.launch {

            _feiraUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val feirasList = getFeirasUseCase.invoke()

            val feirasCardList: List<FeiraModel>? = feirasList?.map { feira ->
                FeiraModel(
                    id = feira.id,
                    nome = feira.nome,
                    descricao = feira.descricao,
                    dataHora = feira.dataHora,
                    localizacao = feira.localizacao,
                    image = feira.image
                )
            }

            _feiraUiState.update {
                it.copy(
                    isLoading = false,
                    feirasCardList = feirasCardList ?: emptyList()
                )
            }

        }
    }
    
    fun getFeiraById(id: Int) {
        viewModelScope.launch {

            _feiraUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val selectedFeira: FeiraModel? = getFeiraByIdUseCase.invoke(id)

            _feiraUiState.update {
                it.copy(
                    isLoading = false,
                    selectedFeira = selectedFeira
                )
            }

        }
    }

    fun cleanSelectedFeira() {
        viewModelScope.launch {
            _feiraUiState.update {
                it.copy(
                    selectedFeira = null
                )
            }
        }
    }
}