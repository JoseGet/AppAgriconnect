package com.example.careiroapp.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.careiroapp.AboutUsView
import com.example.careiroapp.associacoes.ui.AssociacoesView
import com.example.careiroapp.associacoes.ui.SingleAssociacaoView
import com.example.careiroapp.associacoes.ui.viewmodel.AssociacaoViewModel
import com.example.careiroapp.feiras.ui.FeirasView
import com.example.careiroapp.feiras.ui.SingleFeiraView
import com.example.careiroapp.feiras.ui.viewmodel.FeiraViewModel
import com.example.careiroapp.home.ui.HomeView
import com.example.careiroapp.products.ui.ProductsView
import com.example.careiroapp.products.ui.SingleProductView
import com.example.careiroapp.products.ui.viewmodel.ProductsViewModel
import com.example.careiroapp.profile.ui.OrderView
import com.example.careiroapp.profile.ui.ProfileView
import com.example.careiroapp.profile.ui.viewmodel.ProfileViewModel

@Composable
fun TapBarNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
    resetScrollFunction: () -> Unit,
    scrollState: ScrollState,
    scrollTopOffsetPx: Float
) {

    NavHost(
        navController,
        startDestination
    ) {
        composable(
            NavigationItem.Home.route
        ) {
            HomeView(
                navController,
                resetScrollFunction = resetScrollFunction
            )
        }

        composable(
            NavigationItem.Produtos.route
        ) { backStackEntry ->
            val viewModel: ProductsViewModel = hiltViewModel(backStackEntry)
            ProductsView(
                navController,
                productViewModel = viewModel,
                resetScrollFunction = resetScrollFunction
            )
        }

        composable(
            "${NavigationItem.Produtos.route}/{nomeCategoria}"
        ) { backStackEntry ->
            val viewModel: ProductsViewModel = hiltViewModel(backStackEntry)
            val nomeCategoria: String? = backStackEntry.arguments?.getString("nomeCategoria")

            LaunchedEffect(Unit) {
                viewModel.initializeFilter(nomeCategoria)
            }

            ProductsView(
                navController,
                productViewModel = viewModel,
                resetScrollFunction = resetScrollFunction,
            )
        }

        composable(
            NavigationItem.ProdutoUnico.route
        ) { backStackEntry ->
            val viewModel: ProductsViewModel =
                if (navController.previousBackStackEntry != null) hiltViewModel(
                    navController.previousBackStackEntry!!
                ) else hiltViewModel()
            SingleProductView(
                navController,
                productViewModel = viewModel,
                resetScrollFunction
            )
        }

        composable(
            NavigationItem.Feiras.route
        ) { backStackEntry ->
            val viewModel: FeiraViewModel = hiltViewModel(backStackEntry)
            FeirasView(
                navController,
                viewModel
            )
        }

        composable(
            NavigationItem.FeiraUnica.route
        ) { backStackEntry ->
            val viewModel: FeiraViewModel =
                if (navController.previousBackStackEntry != null) hiltViewModel(
                    navController.previousBackStackEntry!!
                ) else hiltViewModel()
            SingleFeiraView(
                navController,
                viewModel,
                resetScrollFunction
            )
        }

        composable(
            NavigationItem.Associacoes.route
        ) { backStackEntry ->
            val viewModel: AssociacaoViewModel = hiltViewModel(backStackEntry)
            AssociacoesView(
                navController,
                viewModel
            )
        }

        composable(
            NavigationItem.AssociacaoUnica.route
        ) { backStackEntry ->
            val viewModel: AssociacaoViewModel =
                if (navController.previousBackStackEntry != null) hiltViewModel(
                    navController.previousBackStackEntry!!
                ) else hiltViewModel()
            SingleAssociacaoView(
                navController,
                viewModel,
                resetScrollFunction
            )
        }

        composable(
            NavigationItem.Profile.route
        ) {
            ProfileView(
                navController = navController,
                resetScrollFunction = resetScrollFunction
            )
        }

        composable(
            NavigationItem.Pedido.route
        ) {
            val viewModel: ProfileViewModel =
                if (navController.previousBackStackEntry != null) hiltViewModel(
                    navController.previousBackStackEntry!!
                ) else hiltViewModel()
            OrderView(
                navController,
                viewModel.profileUiState.value.selectedPedido,
                clearSelectedOrder = {
                    viewModel.clearSelectedOrder()
                }
            )
        }

        composable(
            "${NavigationItem.SobreNos.route}?section={section}",
            arguments = listOf(
                navArgument("section") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val section = backStackEntry.arguments?.getInt("section") ?: -1
            AboutUsView(
                targetSection = section,
                scrollState = scrollState,
                scrollTopOffsetPx = scrollTopOffsetPx
            )
        }
    }

}
