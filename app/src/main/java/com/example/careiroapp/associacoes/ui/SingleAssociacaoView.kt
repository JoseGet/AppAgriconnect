package com.example.careiroapp.associacoes.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.associacoes.data.models.AssociacaoModel
import com.example.careiroapp.associacoes.ui.components.AssociacaoProductorsRow
import com.example.careiroapp.associacoes.ui.components.AssociacaoProductsRow
import com.example.careiroapp.associacoes.ui.components.AssociacaoSection
import com.example.careiroapp.associacoes.ui.viewmodel.AssociacaoViewModel
import com.example.careiroapp.associacoes.ui.viewmodel.SingleAssociacaoUiState
import com.example.careiroapp.associacoes.ui.viewmodel.SingleAssociacaoViewModel
import com.example.careiroapp.common.components.SingleImage
import com.example.careiroapp.common.components.buttons.BackButton
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun SingleAssociacaoView(
    navController: NavController,
    viewModel: SingleAssociacaoViewModel,
    resetScrollFunction: () -> Unit
) {

    val uiState by viewModel.singleAssociacaoUiState.collectAsState()
    val userData by viewModel.userData.collectAsStateWithLifecycle(initialValue = null)
    val loadingAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    BackHandler() {
        navController.popBackStack()
        resetScrollFunction()
    }

    Box() {
        when (val state = uiState) {
            is SingleAssociacaoUiState.Loading -> {
                LottieAnimation(loadingAnimation, iterations = LottieConstants.IterateForever)
            }

            is SingleAssociacaoUiState.Success -> {
                val associacao = state.associacao
                val products = state.products
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
                    SingleImage(associacao.image)
                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            associacao.nome,
                            style = TextStyle(
                                fontFamily = montserratBoldFontFamily,
                                fontSize = 18.sp,
                                color = colorResource(R.color.dark_gray)
                            )
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    AssociacaoSection(
                        title = stringResource(R.string.descricao),
                        content = associacao.descricao
                    )
                    Spacer(Modifier.height(24.dp))
                    AssociacaoSection(
                        title = stringResource(R.string.hora_funcionamento),
                        content = associacao.dataHora ?: ""
                    )
                    Spacer(Modifier.height(24.dp))
                    AssociacaoSection(
                        title = stringResource(R.string.endereco),
                        content = associacao.endereco ?: ""
                    )
                    associacao.productorsList.takeIf { it.isNotEmpty() }
                        ?.let { list ->
                            Spacer(Modifier.height(24.dp))
                            AssociacaoProductorsRow(
                                productorsList = list
                            )
                        }
                    products.takeIf { it.isNotEmpty() }?.let { list ->
                        Spacer(Modifier.height(24.dp))
                        AssociacaoProductsRow(
                            productsList = list,
                            associacao.nome,
                            onButtonClick = { product ->
                                viewModel.addProductToBag(product, userData?.cpf ?: "")
                            }
                        )
                    }
                }
            }
            is SingleAssociacaoUiState.None -> {}
        }
    }
}