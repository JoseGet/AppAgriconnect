package com.example.careiroapp.products.ui

import android.os.Build.VERSION.SDK_INT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.BackButton
import com.example.careiroapp.common.components.cards.CardCadastroAssociacao
import com.example.careiroapp.products.ui.components.BuyProductRow
import com.example.careiroapp.products.ui.components.FeaturedProducts
import com.example.careiroapp.products.ui.components.ProductDescription
import com.example.careiroapp.products.ui.components.ProductTitle
import com.example.careiroapp.products.ui.components.ProductorRow
import com.example.careiroapp.products.ui.viewmodel.ProductsViewModel

@Composable
fun SingleProductView(
    navController: NavController,
    productViewModel: ProductsViewModel,
    resetScrollFunction: () -> Unit
) {

    val context = LocalContext.current
    val productViewUiState by productViewModel.productUiState.collectAsState()
    val userDataStoreState by productViewModel.userData.collectAsStateWithLifecycle()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    BackHandler() {
        navController.popBackStack()
        productViewModel.cleanSelectedProduct()
        resetScrollFunction()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        BackButton(
            onClick = {
                navController.popBackStack()
                productViewModel.cleanSelectedProduct()
            }
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (productViewUiState.isLoading) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.load, imageLoader = imageLoader),
                    contentDescription = null
                )
            }
            AsyncImage(
                modifier = Modifier
                    .height(360.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(productViewUiState.selectedProduct?.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(24.dp))
        ProductTitle(
            productName = productViewUiState.selectedProduct?.nomeProduto ?: "",
            productPrice = productViewUiState.selectedProduct?.precoProduto ?: 0.0f,
            promotionProductPrice = productViewUiState.selectedProduct?.precoPromocao
        )
        Spacer(Modifier.height(16.dp))
        productViewUiState.selectedProduct?.let {
            BuyProductRow(
                isProductFavorite = productViewUiState.isSelectedProductFavorite,
                favoriteButtonClick = {
                    if (productViewUiState.isSelectedProductFavorite == false) {
                        productViewModel.addProductToFavorites(cpf = userDataStoreState.cpf)
                    } else {
                        productViewModel.removeProductFromFavorites(cpf = userDataStoreState.cpf)
                    }
                },
                addToBagClick = {
                    productViewModel.addProductToBag(
                        productViewUiState.selectedProduct!!,
                        userDataStoreState.cpf
                    )
                }
            )
        }
        Spacer(Modifier.height(16.dp))
        ProductorRow(
            productorName = productViewUiState.productorName
        )
        Spacer(Modifier.height(16.dp))
        ProductDescription(
            productDescription = productViewUiState.selectedProduct?.descricao ?: ""
        )
        Spacer(Modifier.height(24.dp))
        FeaturedProducts(
            navController
        )
        Spacer(Modifier.height(24.dp))
        CardCadastroAssociacao()
    }
}

@Composable
@Preview(showBackground = true)
private fun SingleProductViewPreview() {}