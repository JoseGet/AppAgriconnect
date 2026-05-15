package com.example.careiroapp.products.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.BackButton
import com.example.careiroapp.products.ui.components.BuyProductRow
import com.example.careiroapp.products.ui.components.ProductDescription
import com.example.careiroapp.products.ui.components.ProductTitle
import com.example.careiroapp.products.ui.components.ProductorRow
import com.example.careiroapp.products.ui.viewmodel.SingleProductUiState
import com.example.careiroapp.products.ui.viewmodel.SingleProductViewModel

@Composable
fun SingleProductView(
    navController: NavController,
    viewModel: SingleProductViewModel,
    resetScrollFunction: () -> Unit
) {
    val uiState by viewModel.singleProductUiState.collectAsState()
    val userData by viewModel.userData.collectAsStateWithLifecycle(initialValue = null)
    val loadingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    BackHandler {
        navController.popBackStack()
        resetScrollFunction()
    }

    Box {
        when (val state = uiState) {
            is SingleProductUiState.Loading -> {
                LottieAnimation(loadingAnimation, iterations = LottieConstants.IterateForever)
            }

            is SingleProductUiState.Success -> {
                val product = state.product
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    BackButton(
                        onClick = {
                            navController.popBackStack()
                            resetScrollFunction()
                        }
                    )
                    Spacer(Modifier.height(16.dp))
                    AsyncImage(
                        modifier = Modifier
                            .height(360.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(24.dp))
                    ProductTitle(
                        productName = product.nomeProduto,
                        productPrice = product.precoProduto,
                        promotionProductPrice = product.precoPromocao,
                        unity = product.unidadeMedida
                    )
                    Spacer(Modifier.height(16.dp))
                    BuyProductRow(
                        isProductFavorite = state.isProductFavorite,
                        favoriteButtonClick = {
                            if (state.isProductFavorite == false) {
                                viewModel.addProductToFavorites(cpf = userData?.cpf ?: "")
                            } else {
                                viewModel.removeProductFromFavorites(cpf = userData?.cpf ?: "")
                            }
                        },
                        addToBagClick = {
                            viewModel.addProductToBag(product, userData?.cpf ?: "")
                        }
                    )
                    Spacer(Modifier.height(16.dp))
                    ProductorRow(productorName = state.productorName)
                    Spacer(Modifier.height(16.dp))
                    ProductDescription(productDescription = product.descricao)
                }
            }

            is SingleProductUiState.None -> {}
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SingleProductViewPreview() {}
