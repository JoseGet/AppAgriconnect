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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: BagRepository,
    private val userRepository: UserRepository,
    private val pedidoRepository: PedidoRepository,
    private val paymentRepository: PaymentRepository
): ViewModel() {

    private val _checkoutUiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.None())
    val checkoutUiState: StateFlow<CheckoutUiState> = _checkoutUiState.asStateFlow()
    private val _currentOrder = MutableStateFlow(OrderModel())
    val currentOrder: StateFlow<OrderModel> = _currentOrder.asStateFlow()

    private var pixPaymentId: String? = null
    private var pixPollingJob: Job? = null

    val userData: Flow<UserEntity?> = userRepository.getUserData()

    companion object {
        val needsProfileRedirect: MutableState<Boolean> = mutableStateOf(false)
    }

    fun clearNeedsProfileRedirect() {
        needsProfileRedirect.value = false
    }

    init {
        viewModelScope.launch {
            NotificationEvents.events.collect { event ->
                when (event) {
                    is Events.PaymentPixConfirmed -> {
                        val current = _checkoutUiState.value
                        if (current is CheckoutUiState.Success) {
                            _checkoutUiState.value = current.copy(isPaymentPixDone = true)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    val totalPrice: LiveData<Double> = userData
        .flatMapLatest { user ->
            val cpf = user?.cpf
            if (cpf.isNullOrBlank()) flowOf(0.0)
            else repository.getTotalPrice(cpf).map { it ?: 0.0 }
        }
        .asLiveData()

    val cartItems: StateFlow<List<BagItem>> = userData
        .flatMapLatest { user ->
            val cpf = user?.cpf
            if (cpf == null) flowOf(emptyList())
            else repository.getAllItems(cpf)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createPedido() {
        if (_checkoutUiState.value is CheckoutUiState.Loading) return

        val produtos = bagItemsConverter(cartItems.value)

        viewModelScope.launch {
            _checkoutUiState.value = CheckoutUiState.Loading

            try {
                when (_currentOrder.value.paymentType) {
                    PaymentType.PIX -> {
                        val user = userData.firstOrNull()
                        val currentTotal = totalPrice.value ?: 0.0

                        val pedido = PedidoBody(
                            valorTotal = currentTotal.toFloat(),
                            produtos = produtos,
                            status = OrderState.PENDENTE.name,
                            paymentType = _currentOrder.value.paymentType,
                            retiradaLocal = _currentOrder.value.address,
                            retiradaData = _currentOrder.value.date,
                            retiradaHora = _currentOrder.value.time,
                        )

                        val response = pedidoRepository.createPedido(pedido)

                        if (response.isSuccessful) {
                            val pixPaymentBody = PixPaymentRequestBody(
                                method = "PIX",
                                data = PaymentDataRequest(
                                    amount = (currentTotal * 100).toInt(),
                                    expiresIn = 3600,
                                    description = "Cobrança PIX no checkout transparente",
                                    customer = Customer(
                                        name = user?.name ?: "",
                                        email = user?.email ?: "",
                                        taxId = user?.cpf ?: "",
                                        cellphone = user?.telefone ?: ""
                                    ),
                                    metadata = Metadata(pedidoId = response.body()?.id ?: 0)
                                )
                            )

                            val paymentResponse = paymentRepository.createPixPayment(pixPaymentBody)

                            if (paymentResponse.isSuccessful) {
                                val paymentId = paymentResponse.body()?.data?.id
                                _currentOrder.update {
                                    it.copy(
                                        totalValue = response.body()?.valorTotal?.toFloat() ?: 0f,
                                        pixPayload = paymentResponse.body()?.data?.brCode,
                                        pixQrCode = paymentResponse.body()?.data?.brCodeBase64
                                    )
                                }
                                userData.firstOrNull()?.cpf?.let { cpf -> repository.clearBag(cpf) }
                                _checkoutUiState.value = CheckoutUiState.Success(isOrderComplete = true)
                                if (paymentId != null) {
                                    pixPaymentId = paymentId
                                    startPixPolling()
                                }
                            } else {
                                _checkoutUiState.value = CheckoutUiState.None(
                                    order = _currentOrder.value,
                                    checkoutStep = CheckoutStep.TWO
                                )
                            }
                        } else {
                            _checkoutUiState.value = CheckoutUiState.None(
                                order = _currentOrder.value,
                                checkoutStep = CheckoutStep.TWO
                            )
                        }
                    }

                    PaymentType.DINHEIRO -> {
                        val pedido = PedidoBody(
                            valorTotal = totalPrice.value?.toFloat() ?: 0f,
                            produtos = produtos,
                            status = OrderState.CONFIRMADO.name,
                            paymentType = _currentOrder.value.paymentType,
                            retiradaLocal = _currentOrder.value.address,
                            retiradaData = _currentOrder.value.date,
                            retiradaHora = _currentOrder.value.time
                        )

                        val response = pedidoRepository.createPedido(pedido)

                        if (response.isSuccessful) {
                            _currentOrder.update {
                                it.copy(totalValue = response.body()?.valorTotal?.toFloat() ?: 0f)
                            }
                            userData.firstOrNull()?.cpf?.let { cpf -> repository.clearBag(cpf) }
                            _checkoutUiState.value = CheckoutUiState.Success(isOrderComplete = true)
                        } else {
                            _checkoutUiState.value = CheckoutUiState.None(
                                order = _currentOrder.value,
                                checkoutStep = CheckoutStep.TWO
                            )
                        }
                    }

                    else -> {
                        _checkoutUiState.value = CheckoutUiState.None(
                            order = _currentOrder.value,
                            checkoutStep = CheckoutStep.TWO
                        )
                    }
                }
            } catch (e: Exception) {
                _checkoutUiState.value = CheckoutUiState.None(
                    order = _currentOrder.value,
                    checkoutStep = CheckoutStep.TWO
                )
            }
        }
    }

    private fun bagItemsConverter(bagItems: List<BagItem>): List<PedidoProdutoModel> {
        return bagItems.map { item ->
            PedidoProdutoModel(idProduto = item.productId, quantidade = item.quantity)
        }
    }

    fun setNeedsProfileRedirect() {
        needsProfileRedirect.value = true
    }

    fun changeCheckoutStep(newStep: CheckoutStep) {
        _checkoutUiState.value = CheckoutUiState.None(
            order = _currentOrder.value,
            checkoutStep = newStep
        )
    }

    fun updatePaymentType(paymentType: PaymentType) {
        _currentOrder.update { it.copy(paymentType = paymentType) }
        val current = _checkoutUiState.value
        if (current is CheckoutUiState.None) {
            _checkoutUiState.value = current.copy(order = _currentOrder.value)
        }
    }

    fun saveOrderDateLocal(date: String, time: String, local: String) {
        _currentOrder.update { it.copy(date = date, time = time, address = local) }
    }

    fun resetPaymentMode() {
        _currentOrder.update { it.copy(paymentType = null) }
        val current = _checkoutUiState.value
        if (current is CheckoutUiState.None) {
            _checkoutUiState.value = current.copy(order = _currentOrder.value)
        }
    }

    fun checkPixStatusNow() {
        val id = pixPaymentId ?: return
        val current = _checkoutUiState.value
        if (current !is CheckoutUiState.Success || current.isPaymentPixDone) return
        viewModelScope.launch { handlePixStatusCheck(id) }
    }

    private fun startPixPolling() {
        pixPollingJob?.cancel()
        val id = pixPaymentId ?: return
        pixPollingJob = viewModelScope.launch {
            while (isActive) {
                delay(5_000)
                handlePixStatusCheck(id)
                if ((_checkoutUiState.value as? CheckoutUiState.Success)?.isPaymentPixDone == true) break
            }
        }
    }

    private suspend fun handlePixStatusCheck(id: String) {
        try {
            val response = paymentRepository.getPixStatus(id)
            if (response.isSuccessful && response.body()?.success == true) {
                val current = _checkoutUiState.value
                if (current is CheckoutUiState.Success && !current.isPaymentPixDone) {
                    _checkoutUiState.value = current.copy(isPaymentPixDone = true)
                    pixPollingJob?.cancel()
                }
            }
        } catch (e: Exception) { }
    }
}
