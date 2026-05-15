package com.example.careiroapp.profile.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.models.PixStatusResponse
import com.example.careiroapp.bag.data.repository.PaymentRepository
import com.example.careiroapp.bag.data.repository.PedidoRepository
import com.example.careiroapp.common.events.Events
import com.example.careiroapp.common.events.NotificationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleOrderViewModel @Inject constructor(
    private val pedidoRepository: PedidoRepository,
    private val paymentRepository: PaymentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _singleOrderUiState = MutableStateFlow<SingleOrderUiState>(SingleOrderUiState.None)
    var singleOrderUiState: StateFlow<SingleOrderUiState> = _singleOrderUiState.asStateFlow()

    private val _pixStatus = MutableStateFlow<PixStatusResponse?>(null)
    val pixStatus: StateFlow<PixStatusResponse?> = _pixStatus.asStateFlow()

    val pixPaymentDone: MutableState<Boolean> = mutableStateOf(false)

    private val pedidoId: Int = savedStateHandle.get<Int>("pedidoId")!!

    init {
        loadPedido()
        viewModelScope.launch {
            NotificationEvents.events.collect { event ->
                when (event) {
                    is Events.PaymentPixConfirmed -> pixPaymentDone.value = true
                    else -> {}
                }
            }
        }
    }

    private fun loadPedido() {
        _singleOrderUiState.update { SingleOrderUiState.Loading }
        viewModelScope.launch {
            try {
                val response = pedidoRepository.getPedidos(page = 1, limit = PEDIDOS_PAGE_SIZE)
                if (response.isSuccessful) {
                    val pedido = response.body()?.find { it.id == pedidoId }
                    if (pedido != null) {
                        _singleOrderUiState.update { SingleOrderUiState.Success(pedido = pedido) }
                    } else {
                        _singleOrderUiState.update { SingleOrderUiState.None }
                    }
                } else {
                    _singleOrderUiState.update { SingleOrderUiState.None }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading pedido", e)
                _singleOrderUiState.update { SingleOrderUiState.None }
            }
        }
    }

    fun getPixStatus(id: String) {
        viewModelScope.launch {
            try {
                val response = paymentRepository.getPixStatus(id)
                if (response.isSuccessful) {
                    _pixStatus.update { response.body() }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching pix status", e)
            }
        }
    }

    companion object {
        private const val TAG = "SingleOrderViewModel"
        private const val PEDIDOS_PAGE_SIZE = 10
    }
}
