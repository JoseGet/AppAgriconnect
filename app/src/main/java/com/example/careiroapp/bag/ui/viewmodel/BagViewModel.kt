package com.example.careiroapp.bag.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.bag.data.models.PedidoBody
import com.example.careiroapp.bag.data.models.PedidoProdutoModel
import com.example.careiroapp.bag.data.repository.BagRepository
import com.example.careiroapp.bag.data.repository.PedidoRepository
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity
import com.example.careiroapp.profile.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

@HiltViewModel
class BagViewModel @Inject constructor(
    private val repository: BagRepository,
    private val userRepository: UserRepository,
    private val pedidoRepository: PedidoRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<BagUiState> = MutableStateFlow(BagUiState())
    var uiState: StateFlow<BagUiState> = _uiState.asStateFlow()

    private val _orderUiState: MutableStateFlow<OrderUiState> = MutableStateFlow(OrderUiState())
    var orderUiState: StateFlow<OrderUiState> = _orderUiState.asStateFlow()

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

    fun createPedido() {

        val produtos = bagItemsConverter(cartItems.value)

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            try {
                val pedido = PedidoBody(
                    valorTotal = totalPrice.value?.toFloat() ?: 0f,
                    produtos = produtos,
                    paymentType = orderUiState.value.order.paymentType,
                    retiradaLocal = orderUiState.value.order.address,
                    retiradaData = orderUiState.value.order.date,
                    retiradaHora = orderUiState.value.order.time
                )

                val response = pedidoRepository.createPedido(pedido)

                if (response.isSuccessful) {
                    _uiState.update { it.copy(isLoading = false) }
                    _orderUiState.update { it.copy(order = orderUiState.value.order.copy(
                        totalValue = response.body()?.valorTotal?.toFloat() ?: 0f,
                        pixPayload = response.body()?.pixPayload
                    ))}
                    changeCheckoutStep(CheckoutStep.FINAL)
                }
            } catch (e: Exception) { }
        }
    }

    private fun bagItemsConverter(bagItems: List<BagItem>): List<PedidoProdutoModel> {
        return bagItems.map { item ->
            PedidoProdutoModel(
                idProduto = item.productId,
                quantidade = item.quantity
            )
        }
    }

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

    fun updatePaymentType(
        paymentType: PaymentType
    ) {
        _orderUiState.update {
            it.copy(
                order = it.order.copy(
                    paymentType = paymentType
                )
            )
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

        _orderUiState.update {
            it.copy(
                order = it.order.copy(
                    payerData = payerData
                )
            )
        }
    }

    fun saveOrderDateLocal(
        date: String,
        time: String,
        local: String
    ) {
        _orderUiState.update {
            it.copy(
                order = it.order.copy(
                    date = date,
                    time = time,
                    address = local
                )
            )
        }
    }

    fun resetOrderState() {
        _orderUiState.update { it.copy(
            order = OrderModel()
        ) }
    }

}
