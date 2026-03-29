package com.example.careiroapp.bag.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.colorResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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

    val totalPrice by viewModel.totalPrice.observeAsState(0.0)
    val bagItems by viewModel.cartItems.collectAsStateWithLifecycle()

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
                        }
                    }
                }
            )
        },
        containerColor = colorResource(R.color.light_background)
    ) { innerPadding ->
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
                        viewModel.createPedido()
                    }
                )
            }

            CheckoutStep.FINAL -> {
                CheckoutFinalStepView(
                    innerPadding,
                    "google.com"
                )
            }
        }
    }
}
