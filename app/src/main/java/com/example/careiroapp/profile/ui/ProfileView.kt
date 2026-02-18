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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.products.ui.components.ProductCard
import com.example.careiroapp.profile.ui.components.ProfileDataWidget
import com.example.careiroapp.profile.ui.components.ProfileModulesBar
import com.example.careiroapp.profile.ui.viewmodel.ProfileModules
import com.example.careiroapp.profile.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileView(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userState by viewModel.dataStoreUiState.collectAsStateWithLifecycle()
    val profileUiState by viewModel.profileUiState.collectAsState()

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
            nomePerfil = userState.name,
            emailPerfil = userState.email,
            telefonePerfil = userState.telefone,
            fotoPerfil = if (userState.fotoPerfil == "") uriImage else userState.fotoPerfil
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

            }

            ProfileModules.FAVORITOS -> {

                LaunchedEffect(userState.cpf) {
                    if (profileUiState.favoriteItensList.isEmpty()) viewModel.getFavoritesProducts(userState.cpf)
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
                            )
                        }
                    }
                }
            }

            ProfileModules.ASSINATURAS -> {

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ProfileViewPreview() {
    ProfileView()
}