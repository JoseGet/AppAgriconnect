package com.example.careiroapp.associacoes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.associacoes.ui.components.AssociacoesGrid
import com.example.careiroapp.associacoes.ui.viewmodel.AssociacaoViewModel
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.navigation.NavigationItem
import com.example.careiroapp.navigation.Screen

@Composable
fun AssociacoesView(
    navController: NavController,
    associacaoViewModel: AssociacaoViewModel
) {
    val uiState by associacaoViewModel.associacaoUiState.collectAsState()
    val loadingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        ModulesHeader(
            titulo = stringResource(R.string.associacoes),
            subtitulo = null
        )
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(500.dp),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                LottieAnimation(loadingAnimation, iterations = LottieConstants.IterateForever)
            }

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(500.dp)
            ) {
                AssociacoesGrid(
                    list = uiState.associacoesList,
                    onCardClick = { id ->
                        navController.navigate("${Screen.ASSOCIACAO_UNICA.name}/$id")
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun AssociacoesViewPreview() {
    //AssociacoesView()
}