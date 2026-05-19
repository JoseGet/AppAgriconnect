package com.example.careiroapp.bag.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.BagTopBar
import com.example.careiroapp.bag.ui.viewmodel.CheckoutStep
import com.example.careiroapp.bag.ui.viewmodel.CheckoutUiState
import com.example.careiroapp.bag.ui.viewmodel.CheckoutViewModel
import com.example.careiroapp.navigation.NavigationItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckoutView(
    navController: NavController
) {
    val viewModel: CheckoutViewModel = hiltViewModel()
    val uiState by viewModel.checkoutUiState.collectAsStateWithLifecycle()
    val orderData by viewModel.currentOrder.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val totalPrice by viewModel.totalPrice.observeAsState(0.0)
    val bagItems by viewModel.cartItems.collectAsStateWithLifecycle()

    val loadingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    val isLoading = uiState is CheckoutUiState.Loading

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.checkPixStatusNow()
        }
    }

    Scaffold(
        topBar = {
            BagTopBar(
                onBackClick = {
                    when (uiState) {
                        is CheckoutUiState.None if (uiState as CheckoutUiState.None).checkoutStep == CheckoutStep.ONE -> {
                            navController.popBackStack()
                        }

                        is CheckoutUiState.None if (uiState as CheckoutUiState.None).checkoutStep == CheckoutStep.TWO -> {
                            viewModel.changeCheckoutStep(CheckoutStep.ONE)
                            viewModel.resetPaymentMode()
                        }

                        is CheckoutUiState.Success -> {
                            navController.navigate(NavigationItem.Main.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }

                        else -> {}
                    }
                },
                isLoading = isLoading
            )
        },
        containerColor = colorResource(R.color.light_background)
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            when (val state = uiState) {
                is CheckoutUiState.None -> {
                    when (state.checkoutStep) {
                        CheckoutStep.ONE -> {
                            CheckoutStepOneView(
                                innerPadding,
                                onButtonClick = { date, time, local ->
                                    viewModel.saveOrderDateLocal(date, time, local = local)
                                    viewModel.changeCheckoutStep(CheckoutStep.TWO)
                                }
                            )
                        }
                        CheckoutStep.TWO -> {
                            CheckoutStepTwoView(
                                innerPadding,
                                orderData = orderData,
                                productsList = bagItems,
                                totalValue = totalPrice,
                                onButtonClick = {
                                    if (orderData.paymentType == null) {
                                        Toast.makeText(
                                            context,
                                            "Selecione uma forma de pagamento",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        viewModel.createPedido()
                                    }
                                },
                                updatePaymentType = { paymentType ->
                                    viewModel.updatePaymentType(paymentType)
                                }
                            )
                        }
                    }
                }
                is CheckoutUiState.Success -> {
                    CheckoutFinalStepView(
                        innerPadding,
                        orderData = orderData,
                        isPaymentPixDone = state.isPaymentPixDone,
                        onClickLeftButton = {
                            navController.navigate(NavigationItem.Main.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        },
                        onClickRightButton = {
                            viewModel.setNeedsProfileRedirect()
                            navController.navigate(NavigationItem.Main.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                is CheckoutUiState.Loading -> {}
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .zIndex(1f)
                        .pointerInput(Unit) {}
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
