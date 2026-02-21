package com.example.careiroapp.feiras.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.common.components.cards.CardCadastroAssociacao
import com.example.careiroapp.feiras.ui.components.FeirasGrid
import com.example.careiroapp.feiras.ui.viewmodel.FeiraViewModel
import com.example.careiroapp.navigation.NavigationItem

@Composable
fun FeirasView(
    navController: NavController,
    feiraViewModel: FeiraViewModel
) {

    val uiState by feiraViewModel.feiraUiState.collectAsState()
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        ModulesHeader(
            titulo = stringResource(R.string.feiras),
            subtitulo = null
        )
        Box (
            modifier = Modifier
                .wrapContentWidth()
                .height(500.dp),
            contentAlignment = Alignment.Center
        ) {

            if (uiState.isLoading) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.load, imageLoader = imageLoader),
                    contentDescription = null
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                FeirasGrid(
                    list = uiState.feirasCardList,
                    onClickCard = { id ->
                        feiraViewModel.getFeiraById(id)
                        navController.navigate(NavigationItem.FeiraUnica.route)
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
@Preview(showBackground = true)
private fun FeirasViewPreview() {
    //FeirasView()
}