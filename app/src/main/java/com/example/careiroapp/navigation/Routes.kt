package com.example.careiroapp.navigation

enum class Screen {
    MAIN,
    HOME,
    PRODUTOS,
    PRODUTO_UNICO,
    FEIRAS,
    FEIRA_UNICA,
    ASSOCIACOES,
    ASSOCIACAO_UNICA,
    SACOLA,
    CHECKOUT,
    PICKUP,
    PROFILE,
    SOBRE_NOS,

    PEDIDO,
    LOGIN
}

sealed class NavigationItem(val route: String) {
    object Main : NavigationItem(Screen.MAIN.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Produtos : NavigationItem(Screen.PRODUTOS.name)
    object Feiras : NavigationItem(Screen.FEIRAS.name)
    object Associacoes : NavigationItem(Screen.ASSOCIACOES.name)
    object ProdutoUnico : NavigationItem(Screen.PRODUTO_UNICO.name)
    object Sacola: NavigationItem(route = Screen.SACOLA.name)
    object Checkout: NavigationItem(route = Screen.CHECKOUT.name)
    object Pickup: NavigationItem(route = Screen.PICKUP.name)
    object Profile: NavigationItem(route = Screen.PROFILE.name)
    object SobreNos: NavigationItem(route = Screen.SOBRE_NOS.name)
    object FeiraUnica: NavigationItem(route = Screen.FEIRA_UNICA.name)
    object AssociacaoUnica: NavigationItem(route = Screen.ASSOCIACAO_UNICA.name)

    object Pedido: NavigationItem(route = Screen.PEDIDO.name)

    object Login: NavigationItem(route = Screen.LOGIN.name)
}