package com.example.careiroapp.bag.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.BagTopBar
import com.example.careiroapp.bag.ui.viewmodel.CheckoutStep
import com.example.careiroapp.bag.ui.viewmodel.CheckoutViewModel
import com.example.careiroapp.navigation.NavigationItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckoutView(
    navController: NavController
) {

    val viewModel: CheckoutViewModel = hiltViewModel()
    val uiState by viewModel.checkoutUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val totalPrice by viewModel.totalPrice.observeAsState(0.0)
    val bagItems by viewModel.cartItems.collectAsStateWithLifecycle()

    val loadingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    Scaffold(
        topBar = {
            BagTopBar(
                onBackClick = {
                    when (uiState.checkoutStep) {
                        CheckoutStep.ONE -> {
                            navController.popBackStack()
                        }
                        CheckoutStep.TWO -> {
                            viewModel.changeCheckoutStep(CheckoutStep.ONE)
                            viewModel.resetPaymentMode()
                        }
                        CheckoutStep.FINAL -> {
                            navController.navigate(NavigationItem.Main.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                            viewModel.resetOrderState()
                        }
                    }
                },
                isLoading = uiState.isLoading
            )
        },
        containerColor = colorResource(R.color.light_background)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            when(uiState.checkoutStep) {

                CheckoutStep.ONE -> {
                     CheckoutStepOneView(
                        innerPadding,
                        onButtonClick = {date,time, local ->
                            viewModel.saveOrderDateLocal(date, time, local = local)
                            viewModel.changeCheckoutStep(CheckoutStep.TWO)
                        }
                    )
                }

                CheckoutStep.TWO -> {
                    CheckoutStepTwoView (
                        innerPadding,
                        orderData = uiState.order,
                        productsList = bagItems,
                        totalValue = totalPrice,
                        onButtonClick = {
                            if (uiState.order.paymentType == null) {
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
                        orderData = uiState.order,
                        isPaymentPixDone = uiState.isPaymentPixDone,
                        onClickLeftButton = {
                            navController.navigate(NavigationItem.Main.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                            viewModel.resetOrderState()
                        },
                        onClickRightButton = {
                            viewModel.setNeedsProfileRedirect()
                            navController.navigate(NavigationItem.Main.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                            viewModel.resetOrderState()
                        }
                    )
                }
            }
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .zIndex(1f)
                        .pointerInput(Unit){}
                ) {
                    LottieAnimation(
                        loadingAnimation,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
private fun CheckoutViewPreview() {
    CheckoutView(
        navController = rememberNavController()
    )
}
