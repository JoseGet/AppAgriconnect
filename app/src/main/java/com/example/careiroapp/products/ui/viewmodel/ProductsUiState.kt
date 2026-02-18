package com.example.careiroapp.products.ui.viewmodel

import com.example.careiroapp.products.data.models.ProductCardModel
import com.example.careiroapp.products.data.models.ProductModel

data class ProductsUiState(
    val isLoading: Boolean = false,
    val productsCardList: List<ProductCardModel> = listOf(),
    val productsCount: Int? = 0,
    val selectedProduct: ProductModel? = null,
    val isSelectedProductFavorite: Boolean? = null,
    val filterNameActivate: String? = null,
    val endOfListReached: Boolean = false,
    val productorName: String = ""
)