package com.example.careiroapp.bag.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.models.Customer
import com.example.careiroapp.bag.data.models.Metadata
import com.example.careiroapp.bag.data.models.PaymentDataRequest
import com.example.careiroapp.bag.data.models.PedidoBody
import com.example.careiroapp.bag.data.models.PedidoProdutoModel
import com.example.careiroapp.bag.data.models.PixPaymentRequestBody
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.bag.data.repository.PaymentRepository
import com.example.careiroapp.bag.data.repository.PedidoRepository
import com.example.careiroapp.common.events.Events
import com.example.careiroapp.common.events.NotificationEvents
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity
import com.example.careiroapp.profile.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    private val repository: BagRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<BagUiState> = MutableStateFlow(BagUiState())
    var uiState: StateFlow<BagUiState> = _uiState.asStateFlow()

    val userData: Flow<UserEntity?> = userRepository.getUserData()
    val totalPrice: LiveData<Double> = userData
        .flatMapLatest { user ->
            val cpf = user?.cpf
            if (cpf.isNullOrBlank()) {
                flowOf(0.0)
            } else {
                repository.getTotalPrice(cpf).map { it ?: 0.0 }
            }
        }
        .asLiveData()
    val cartItems: StateFlow<List<BagItem>> = userData
        .flatMapLatest { user ->
            val cpf = user?.cpf
            if (cpf == null) {
                flowOf(emptyList())
            } else {
                repository.getAllItems(cpf)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addQuantity(productId: UUID, cpf: String) {
        viewModelScope.launch {
            try {
                repository.incrementQuantity(productId, cpf)
            } catch (e: Exception) {}
        }
    }

    fun decreaseQuantity(productId: UUID, cpf: String) {
        viewModelScope.launch {
            try {
                repository.decreaseQuantity(productId, cpf)
            } catch (e: Exception) {}
        }
    }

    fun removeProduct(bagItem: BagItem) {
        viewModelScope.launch {
            try {
                repository.removeFromBag(item = bagItem)
            } catch (e: Exception) {}
        }
    }

}
