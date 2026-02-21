package com.example.careiroapp.associacoes.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
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
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.associacoes.ui.components.AssociacoesGrid
import com.example.careiroapp.associacoes.ui.viewmodel.AssociacaoViewModel
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.common.components.cards.CardCadastroAssociacao
import com.example.careiroapp.navigation.NavigationItem

@Composable
fun AssociacoesView(
    navController: NavController,
    associacaoViewModel: AssociacaoViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {

        val uiState by associacaoViewModel.associacaoUiState.collectAsState()
        val context = LocalContext.current

        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(AnimatedImageDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

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
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.load,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null
                )
            }

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(500.dp)
            ) {
                AssociacoesGrid(
                    list = uiState.associacoesList,
                    onCardClick = { id ->
                        associacaoViewModel.getAssociacaoById(id)
                        navController.navigate(NavigationItem.AssociacaoUnica.route)
                    }
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        CardCadastroAssociacao(
            {
                navController.navigate(NavigationItem.Associacoes.route)
            }
        )
    }
}

@Composable
@Preview
private fun AssociacoesViewPreview() {
    //AssociacoesView()
}