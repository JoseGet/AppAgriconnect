package com.example.careiroapp.profile.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.navigation.NavigationItem
import com.example.careiroapp.products.ui.components.ProductCard
import com.example.careiroapp.profile.ui.components.OrderCard
import com.example.careiroapp.profile.ui.components.ProfileDataWidget
import com.example.careiroapp.profile.ui.components.ProfileModulesBar
import com.example.careiroapp.profile.ui.viewmodel.ProfileModules
import com.example.careiroapp.profile.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileView(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController,
    resetScrollFunction: () -> Unit
) {
    val profileUiState by viewModel.profileUiState.collectAsState()
    val userData by viewModel.userData.collectAsState(initial = null)

    val noProfileImage = R.drawable.no_profile
    val context = LocalContext.current
    val uriImage = "android.resource://${context.packageName}/$noProfileImage"
    val gridListState = rememberLazyGridState()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(vertical = 24.dp, horizontal = 16.dp),
    ) {
        ModulesHeader(
            titulo = "Meu Perfil",
            subtitulo = null
        )
        Spacer(Modifier.height(24.dp))
        ProfileDataWidget(
            nomePerfil = userData?.name ?: "",
            emailPerfil = userData?.email ?: "",
            telefonePerfil = userData?.telefone ?: "",
            fotoPerfil = userData?.fotoPerfil?.takeIf { it.isNotBlank() } ?: uriImage
        )
        Spacer(Modifier.height(24.dp))
        ProfileModulesBar(
            profileUiState.currentProfileModule,
            onCLick = { profileModule ->
                viewModel.setCurrentModule(profileModule)
            }
        )
        Spacer(Modifier.height(24.dp))
        when (profileUiState.currentProfileModule) {

            ProfileModules.PEDIDOS -> {

                LaunchedEffect(Unit) {
                    if (profileUiState.pedidosList.isEmpty()) viewModel.getPedidos()
                }

                val pedidosListState = rememberLazyListState()
                val shouldLoadMore = remember {
                    derivedStateOf {
                        val lastVisible = pedidosListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
                        val total = pedidosListState.layoutInfo.totalItemsCount
                        total > 0 && lastVisible >= total - 2
                    }
                }

                LaunchedEffect(shouldLoadMore.value) {
                    if (shouldLoadMore.value) viewModel.loadMorePedidos()
                }

                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(500.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileUiState.isLoading) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = R.drawable.load,
                                imageLoader = imageLoader
                            ),
                            contentDescription = null
                        )
                    }
                    LazyColumn(
                        state = pedidosListState,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(profileUiState.pedidosList) { pedido ->
                            OrderCard(
                                orderId = pedido.id,
                                orderTotalValue = pedido.valorTotal.toFloat(),
                                orderStatus = pedido.status ?: "",
                                onClick = {
                                    viewModel.updateSelectedPedido(pedido)
                                    navController.navigate(NavigationItem.Pedido.route)
                                    resetScrollFunction()
                                }
                            )
                        }
                        if (profileUiState.isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }

            ProfileModules.FAVORITOS -> {

                LaunchedEffect(userData?.cpf) {
                    if (profileUiState.favoriteItensList.isEmpty()) {
                        userData?.cpf?.let { cpf ->
                            viewModel.getFavoritesProducts(cpf)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(500.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileUiState.isLoading) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = R.drawable.load,
                                imageLoader = imageLoader
                            ),
                            contentDescription = null
                        )
                    }
                    LazyVerticalGrid(
                        state = gridListState,
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(profileUiState.favoriteItensList) { item ->
                            ProductCard(
                                modifier = Modifier
                                    .padding(bottom = 16.dp),
                                image = item.image,
                                nomeProduto = item.nomeProduto,
                                precoProduto = item.precoProduto,
                                isPromocao = item.isPromocao,
                                precoPromocao = item.precoPromocao,
                                haveButton = true,
                                onClick = {},
                                onButtonClick = {
                                    viewModel.addProductToBag(item, userData?.cpf ?: "")
                                }
                            )
                        }
                    }
                }
            }

//            ProfileModules.ASSINATURAS -> {
//
//            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileViewPreview() {
    //ProfileView()
}
