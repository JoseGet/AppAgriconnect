package com.example.careiroapp.products.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.navigation.NavigationItem
import com.example.careiroapp.products.ui.components.FeaturedProducts
import com.example.careiroapp.products.ui.components.FilterRow
import com.example.careiroapp.products.ui.components.ProductsGrid
import com.example.careiroapp.products.ui.viewmodel.ProductsViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductsView(
    navController: NavController,
    productViewModel: ProductsViewModel,
    resetScrollFunction: () -> Unit,
) {

    val productViewUiState by productViewModel.productUiState.collectAsState()
    val userData by productViewModel.userData.collectAsStateWithLifecycle(initialValue = null)
    val gridListState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val resetGridListState: () -> Unit = {
        scope.launch {
            gridListState.animateScrollToItem(0)
        }
    }

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    LaunchedEffect(productViewUiState.filterNameActivate) {
        if (productViewUiState.filterNameActivate == null) {
            productViewModel.getProducts(isNecessaryLoadMore = false)
        } else {
            productViewModel.getProductsByCategoria(
                productViewUiState.filterNameActivate,
                isNecessaryLoadMore = false
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        ModulesHeader(
            titulo = stringResource(R.string.confira_nossos_produtos),
            subtitulo = null
        )
        FilterRow(
            productsCounter = if (productViewUiState.productsCardList.isEmpty()) 0 else productViewUiState.productsCount,
            onFilterCLick = { nomeCategoria ->
                productViewModel.updateFilterActivate(nomeCategoria)
                resetGridListState()
                productViewModel.resetListState()
                productViewModel.cleanProductsList()
            },
            filterActivated = productViewUiState.filterNameActivate
        )
        Spacer(Modifier.height(24.dp))
        Box (
            modifier = Modifier
                .wrapContentWidth()
                .height(500.dp),
            contentAlignment = Alignment.Center
        ) {
            if (productViewUiState.isLoading) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.load, imageLoader = imageLoader),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (productViewUiState.filterNameActivate == null) stringResource(R.string.ver_tudo) else productViewUiState.filterNameActivate.toString(),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = montserratBoldFontFamily,
                            color = colorResource(R.color.dark_gray)
                        )
                    )
                }
                Spacer(Modifier.height(24.dp))
                ProductsGrid(
                    gridListState = gridListState,
                    list = productViewUiState.productsCardList,
                    onItemClicker = { id ->
                        productViewModel.getProductById(
                            userData?.cpf ?: "",
                            id
                        )
                        navController.navigate(NavigationItem.ProdutoUnico.route)
                        resetScrollFunction()
                    },
                    loadMore = {
                        productViewModel.loadMoreProducts {
                            if (productViewUiState.filterNameActivate != null) productViewModel.getProductsByCategoria(
                                productViewUiState.filterNameActivate ?: "",
                                isNecessaryLoadMore = true
                            ) else productViewModel.getProducts(isNecessaryLoadMore = true)
                        }
                    },
                    addToBag = { product ->
                        productViewModel.addProductToBag(product, userData?.cpf ?: "")
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun ProductsViewPreview() {
}