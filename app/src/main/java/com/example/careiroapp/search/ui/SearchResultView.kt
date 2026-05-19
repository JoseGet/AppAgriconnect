package com.example.careiroapp.search.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.navigation.NavigationItem
import com.example.careiroapp.navigation.Screen
import com.example.careiroapp.products.ui.components.ProductCard
import com.example.careiroapp.search.ui.viewmodel.SearchViewModel

@Composable
fun SearchResultView(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userData by viewModel.userData.collectAsStateWithLifecycle(initialValue = null)
    var localQuery by remember { mutableStateOf(uiState.query) }
    val gridState = rememberLazyGridState()
    val loadingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    BackHandler() {
        navController.navigate(NavigationItem.Home.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.light_gray))
                .padding(horizontal = 4.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "Resultado da pesquisa: ",
                style = TextStyle(
                    color = colorResource(R.color.dark_green),
                    fontFamily = montserratBoldFontFamily,
                    fontSize = 16.sp
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                LottieAnimation(
                    composition = loadingAnimation,
                    iterations = LottieConstants.IterateForever
                )
            } else {
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.products) { product ->
                        ProductCard(
                            modifier = Modifier.padding(bottom = 16.dp),
                            image = product.image,
                            nomeProduto = product.nomeProduto,
                            precoProduto = product.precoProduto,
                            isPromocao = product.isPromocao,
                            precoPromocao = product.precoPromocao,
                            haveButton = true,
                            onClick = {
                                navController.navigate("${Screen.PRODUTO_UNICO.name}/${product.id}")
                            },
                            onButtonClick = {
                                viewModel.addProductToBag(product, userData?.cpf ?: "")
                            }
                        )
                    }
                }
            }
        }
    }
}
