package com.example.careiroapp.bag.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.data.dataStore.UserDataStore
import com.example.careiroapp.data.dataStore.model.UserDataStoreModel
import com.example.careiroapp.data.room.entities.BagItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class BagViewModel @Inject constructor(
    private val repository: BagRepository,
    userDataStore: UserDataStore
): ViewModel() {

    private val _uiState: MutableStateFlow<BagUiState> = MutableStateFlow(BagUiState())
    var uiState: StateFlow<BagUiState> = _uiState.asStateFlow()

    private val _orderUiState: MutableStateFlow<OrderUiState> = MutableStateFlow(OrderUiState())
    var orderUiState: StateFlow<OrderUiState> = _orderUiState.asStateFlow()

    val userDataStoreUiState: StateFlow<UserDataStoreModel> = userDataStore.getUserData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserDataStoreModel()
        )
    val totalPrice: LiveData<Double> = userDataStore.userIdFlow
        .flatMapLatest { userId ->
            if (userId.isNullOrBlank()) {
                flowOf(0.0)
            } else {
                repository.getTotalPrice(userId).map { it ?: 0.0 }
            }
        }
        .asLiveData()
    val cartItems: StateFlow<List<BagItem>> = userDataStore.userIdFlow
        .flatMapLatest { userId ->
            if (userId == null) {
                flowOf(emptyList())
            } else {
                repository.getAllItems(userId)
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

    fun changeCheckoutStep(newStep: CheckoutStep) {
        viewModelScope.launch {
            _uiState.update { it.copy(checkoutStep = newStep) }
        }
    }

    fun savePayerData(
        email: String,
        name: String,
        telefone: String,
    ) {

        val payerData = PayerData(
            email = email,
            name = name,
            telefone = telefone
        )

        viewModelScope.launch {
            _orderUiState.update { it.copy(
                payerData = payerData
            ) }
        }
    }

    fun saveOrderDateLocal(
        date: String,
        time: String,
        local: String
    ) {

        val order = OrderModel(
            date = date,
            time = time,
            address = local
        )

        viewModelScope.launch {
            _orderUiState.update { it.copy(
                order = order
            ) }
        }
    }

}