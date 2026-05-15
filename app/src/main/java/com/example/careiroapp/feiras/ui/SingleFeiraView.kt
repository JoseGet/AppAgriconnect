package com.example.careiroapp.feiras.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.common.components.SingleImage
import com.example.careiroapp.common.components.buttons.BackButton
import com.example.careiroapp.feiras.ui.components.FeiraDescription
import com.example.careiroapp.feiras.ui.components.FeiraLocalizacao
import com.example.careiroapp.feiras.ui.components.FeiraTitle
import com.example.careiroapp.feiras.ui.viewmodel.SingleFeiraUiState
import com.example.careiroapp.feiras.ui.viewmodel.SingleFeiraViewModel

@Composable
fun SingleFeiraView(
    navController: NavController,
    viewModel: SingleFeiraViewModel,
    resetScrollFunction: () -> Unit
) {
    val uiState by viewModel.singleFeiraUiState.collectAsState()
    val loadingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    BackHandler {
        navController.popBackStack()
        resetScrollFunction()
    }

    Box {
        when (val state = uiState) {
            is SingleFeiraUiState.Loading -> {
                LottieAnimation(loadingAnimation, iterations = LottieConstants.IterateForever)
            }

            is SingleFeiraUiState.Success -> {
                val feira = state.feira
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
                    SingleImage(feira.image)
                    Spacer(Modifier.height(24.dp))
                    FeiraTitle(
                        feiraName = feira.nome,
                        feiraDataHora = feira.dataHora
                    )
                    Spacer(Modifier.height(24.dp))
                    FeiraLocalizacao(localizacao = feira.localizacao)
                    Spacer(Modifier.height(24.dp))
                    FeiraDescription(description = feira.descricao)
                    Spacer(Modifier.height(24.dp))
                }
            }

            is SingleFeiraUiState.None -> {}
        }
    }
}

@Preview
@Composable
private fun SingleFeiraViewPreview() {}
