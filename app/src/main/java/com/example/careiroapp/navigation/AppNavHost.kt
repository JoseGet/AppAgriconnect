package com.example.careiroapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.careiroapp.BaseView
import com.example.careiroapp.bag.ui.BagView
import com.example.careiroapp.checkout.ui.CheckoutView
import com.example.careiroapp.checkout.ui.PickupView
import com.example.careiroapp.loginCadastro.ui.LoginView
import com.example.careiroapp.loginCadastro.ui.viewmodel.LoginCadastroViewModel
import com.example.careiroapp.navigation.NavigationItem.Checkout
import com.example.careiroapp.navigation.NavigationItem.Login
import com.example.careiroapp.navigation.NavigationItem.Main
import com.example.careiroapp.navigation.NavigationItem.Pickup
import com.example.careiroapp.navigation.NavigationItem.Sacola

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: LoginCadastroViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination

    if (startDestination == null) {
        Box {}
    } else {
        NavHost(
            navController = navController,
            startDestination = startDestination!!
        ) {
            composable(
                Main.route
            ) {
                BaseView(navController)
            }

            composable(
                Sacola.route
            ) {
                Column {
                    BagView(navController)
                }
            }
            composable(
                Checkout.route
            ) {
                Column {
                    CheckoutView(navController)
                }
            }

            composable (
                Pickup.route
            ){
                PickupView(navController)
            }

            composable(
                Login.route
            ) {
                LoginView(navController)
            }
        }
    }
}