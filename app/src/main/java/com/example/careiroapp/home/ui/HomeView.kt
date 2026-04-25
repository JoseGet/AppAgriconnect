package com.example.careiroapp.home.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.common.components.cards.CardAssinatura
import com.example.careiroapp.home.ui.components.HomeCardFeira
import com.example.careiroapp.products.ui.components.ProductCard
import com.example.careiroapp.home.ui.components.CategoriasModule
import com.example.careiroapp.home.ui.components.TutorialRow
import com.example.careiroapp.home.ui.viewmodel.HomeViewModel
import com.example.careiroapp.navigation.NavigationItem

@Composable
fun HomeView(
    navController: NavController,
    resetScrollFunction: () -> Unit,
) {
    val context = LocalContext.current
    val categoriesRowScrollState = rememberScrollState()

    val produto1 = R.drawable.tomates
    val uriProduto1 = "android.resource://${context.packageName}/$produto1"

    val produto2 = R.drawable.peras
    val uriProduto2 = "android.resource://${context.packageName}/$produto2"

    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Column() {
        Image(
            modifier = Modifier
                .height(176.dp)
                .fillMaxWidth(),
            painter = painterResource(R.drawable.banner),
            contentDescription = ""
        )
        Spacer(Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 17.dp)
        ) {
            ModulesHeader(
                titulo = stringResource(R.string.produtos_e_associacoes_destaque),
                subtitulo = null
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProductCard(
                    modifier = Modifier
                        .weight(1f),
                    image = uriProduto1,
                    nomeProduto = "Tomates",
                    precoProduto = 10.0f,
                    isPromocao = true,
                    precoPromocao = 8.00,
                    haveButton = false,
                    onClick = {

                    }
                )
                ProductCard(
                    modifier = Modifier
                        .weight(1f),
                    image = uriProduto2,
                    nomeProduto = "Peras",
                    precoProduto = 10.0f,
                    isPromocao = true,
                    precoPromocao = 8.00,
                    haveButton = false,
                    onClick = {

                    }
                )
            }
            Spacer(Modifier.height(24.dp))
            ModulesHeader(
                titulo = stringResource(R.string.conheca_nossas_feiras_titulo),
                subtitulo = stringResource(R.string.conheca_nossas_feiras_descricao)
            )
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    Image(
                        painter = rememberAsyncImagePainter(model = R.drawable.load, imageLoader = imageLoader),
                        contentDescription = null
                    )
                }
                LazyRow (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.feirasList) { item ->
                        HomeCardFeira(
                            modifier = Modifier,
                            image = item.image,
                            localText = item.localizacao,
                            dataText = item.dataHora,
                            titleText = item.nome,
                            onClick = {
                                navController.navigate(NavigationItem.Feiras.route)
                                resetScrollFunction()
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            ModulesHeader(
                titulo = stringResource(R.string.categoria_produtos_titulo),
                subtitulo = stringResource(R.string.categoria_produtos_descricao)
            )
            Spacer(Modifier.height(24.dp))
            CategoriasModule(
                navController = navController,
                resetScrollFunction
            )
            Spacer(Modifier.height(24.dp))
            ModulesHeader(
                titulo = stringResource(R.string.assinaturas_titulo),
                subtitulo = stringResource(R.string.assinaturas_descricao)
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardAssinatura(
                    modifier = Modifier
                        .weight(1f),
                    image = painterResource(R.drawable.macas),
                    nomeAssinatura = "Assinatura",
                    precoAssinatura = 10.0f,
                    haveButton = false
                )
                CardAssinatura(
                    modifier = Modifier
                        .weight(1f),
                    image = painterResource(R.drawable.macas),
                    nomeAssinatura = "Assinatura",
                    precoAssinatura = 10.0f,
                    haveButton = false
                )
            }
            Spacer(Modifier.height(24.dp))
            ModulesHeader(
                titulo = stringResource(R.string.como_funciona_titulo),
                subtitulo = stringResource(R.string.como_funciona_descricao)
            )
            Spacer(Modifier.height(24.dp))
            TutorialRow()
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun HomeViewPreview() {
    HomeView(
        navController = rememberNavController(),
        resetScrollFunction = {}
    )
}