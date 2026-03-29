package com.example.careiroapp.bag.ui

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.BagTopBar
import com.example.careiroapp.bag.ui.viewmodel.BagViewModel
import com.example.careiroapp.bag.ui.viewmodel.CheckoutStep
import com.example.careiroapp.navigation.NavigationItem

@Composable
fun CheckoutView(
    navController: NavController
) {

    val viewModel: BagViewModel = hiltViewModel()
    val bagUiState by viewModel.uiState.collectAsState()
    val orderUiState by viewModel.orderUiState.collectAsState()
    val context = LocalContext.current
    val totalPrice by viewModel.totalPrice.observeAsState(0.0)
    val bagItems by viewModel.cartItems.collectAsStateWithLifecycle()

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Scaffold(
        topBar = {
            BagTopBar(
                onBackClick = {
                    when (bagUiState.checkoutStep) {
                        CheckoutStep.ONE -> {
                            navController.popBackStack()
                        }
                        CheckoutStep.TWO -> {
                            viewModel.changeCheckoutStep(CheckoutStep.ONE)
                        }
                        CheckoutStep.THREE -> {
                            viewModel.changeCheckoutStep(CheckoutStep.TWO)
                        }
                        CheckoutStep.FINAL -> {
                            navController.navigate(NavigationItem.Main.route)
                            viewModel.resetOrderState()
                        }
                    }
                }
            )
        },
        containerColor = colorResource(R.color.light_background)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when(bagUiState.checkoutStep) {

                CheckoutStep.ONE -> {
                    CheckoutStepOneView(
                        innerPadding,
                        payerData = orderUiState.order.payerData,
                        onButtonClick = {email, name, telefone ->
                            viewModel.savePayerData(
                                email,
                                name,
                                telefone
                            )
                            viewModel.changeCheckoutStep(CheckoutStep.TWO)
                        }
                    )
                }

                CheckoutStep.TWO -> {
                    CheckoutStepTwoView(
                        innerPadding,
                        onButtonClick = {date,time, local ->
                            viewModel.saveOrderDateLocal(date, time, local = local)
                            viewModel.changeCheckoutStep(CheckoutStep.THREE)
                        }
                    )
                }

                CheckoutStep.THREE -> {
                    CheckoutStepThreeView(
                        innerPadding,
                        orderData = orderUiState.order,
                        productsList = bagItems,
                        totalValue = totalPrice,
                        onButtonClick = {

                            if (orderUiState.order.paymentType == null) {
                                Toast.makeText(context, "Selecione uma forma de pagamento", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.createPedido()
                            }
                        },
                        updatePaymentType = { paymentType ->
                            viewModel.updatePaymentType(paymentType)
                        }
                    )
                }

                CheckoutStep.FINAL -> {
                    CheckoutFinalStepView(
                        innerPadding,
                        orderData = orderUiState.order,
                        pixPayload = orderUiState.order.pixPayload ?: ""
                    )
                }
            }
            if (bagUiState.isLoading) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.load,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null
                )
            }
        }
    }
}
