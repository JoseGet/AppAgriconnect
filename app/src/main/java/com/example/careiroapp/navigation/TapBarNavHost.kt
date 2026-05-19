package com.example.careiroapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.careiroapp.AboutUsView
import com.example.careiroapp.associacoes.ui.AssociacoesView
import com.example.careiroapp.search.ui.SearchResultView
import com.example.careiroapp.search.ui.viewmodel.SearchViewModel
import com.example.careiroapp.associacoes.ui.SingleAssociacaoView
import com.example.careiroapp.associacoes.ui.viewmodel.AssociacaoViewModel
import com.example.careiroapp.associacoes.ui.viewmodel.SingleAssociacaoViewModel
import com.example.careiroapp.feiras.ui.FeirasView
import com.example.careiroapp.feiras.ui.SingleFeiraView
import com.example.careiroapp.feiras.ui.viewmodel.FeiraViewModel
import com.example.careiroapp.feiras.ui.viewmodel.SingleFeiraViewModel
import com.example.careiroapp.home.ui.HomeView
import com.example.careiroapp.products.ui.ProductsView
import com.example.careiroapp.products.ui.SingleProductView
import com.example.careiroapp.products.ui.viewmodel.ProductsViewModel
import com.example.careiroapp.products.ui.viewmodel.SingleProductViewModel
import com.example.careiroapp.profile.ui.OrderView
import com.example.careiroapp.profile.ui.PixStatusView
import com.example.careiroapp.profile.ui.ProfileView
import com.example.careiroapp.profile.ui.viewmodel.ProfileViewModel
import com.example.careiroapp.profile.ui.viewmodel.SingleOrderUiState
import com.example.careiroapp.profile.ui.viewmodel.SingleOrderViewModel

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
            NavigationItem.ProdutoUnico.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val viewModel: SingleProductViewModel = hiltViewModel(backStackEntry)
            SingleProductView(
                navController,
                viewModel,
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
            NavigationItem.FeiraUnica.route,
            arguments = listOf(navArgument("feiraId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel: SingleFeiraViewModel = hiltViewModel(backStackEntry)
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
            NavigationItem.AssociacaoUnica.route,
            arguments = listOf(navArgument("associacaoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: SingleAssociacaoViewModel = hiltViewModel(backStackEntry)
            SingleAssociacaoView(navController, viewModel, resetScrollFunction)
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
            NavigationItem.Pedido.route,
            arguments = listOf(navArgument("pedidoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val viewModel: SingleOrderViewModel = hiltViewModel(backStackEntry)
            OrderView(
                navController,
                viewModel,
                onPixPaymentClick = {
                    navController.navigate(NavigationItem.PixStatus.route)
                }
            )
        }

        composable(
            NavigationItem.PixStatus.route
        ) {
            val pedidoEntry = try {
                navController.getBackStackEntry(NavigationItem.Pedido.route)
            } catch (e: IllegalArgumentException) {
                return@composable
            }
            val viewModel: SingleOrderViewModel = hiltViewModel(pedidoEntry)
            val pixStatus by viewModel.pixStatus.collectAsStateWithLifecycle()
            val uiState by viewModel.singleOrderUiState.collectAsStateWithLifecycle()
            LaunchedEffect(uiState) {
                val pixPaymentId = (uiState as? SingleOrderUiState.Success)?.pedido?.pixPaymentId
                if (!pixPaymentId.isNullOrBlank()) {
                    viewModel.getPixStatus(pixPaymentId)
                }
            }
            PixStatusView(
                navController = navController,
                pixStatus = pixStatus,
                isPixPaymentDone = viewModel.pixPaymentDone.value
            )
        }

        composable(
            NavigationItem.Busca.route,
            arguments = listOf(navArgument("query") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: SearchViewModel = hiltViewModel(backStackEntry)
            SearchResultView(
                navController = navController,
                viewModel = viewModel
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
