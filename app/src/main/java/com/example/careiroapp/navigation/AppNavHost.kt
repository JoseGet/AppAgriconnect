package com.example.careiroapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.careiroapp.BaseView
import com.example.careiroapp.bag.ui.BagView
import com.example.careiroapp.bag.ui.CheckoutView
import com.example.careiroapp.loginCadastro.ui.LoginView
import com.example.careiroapp.loginCadastro.ui.viewmodel.LoginCadastroUiEvents
import com.example.careiroapp.loginCadastro.ui.viewmodel.LoginCadastroViewModel
import com.example.careiroapp.navigation.NavigationItem.Checkout
import com.example.careiroapp.navigation.NavigationItem.Login
import com.example.careiroapp.navigation.NavigationItem.Main
import com.example.careiroapp.navigation.NavigationItem.Sacola

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: LoginCadastroViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()

    LaunchedEffect(uiEvent) {
        if (uiEvent is LoginCadastroUiEvents.SessionExpired) {
            navController.navigate(Login.route) {
                popUpTo(0) { inclusive = true }
            }
            viewModel.resetUiEvent()
        }
    }

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

            composable(
                Login.route
            ) {
                LoginView(navController)
            }
        }
    }
}