package com.example.careiroapp.feiras.ui

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.common.components.SingleImage
import com.example.careiroapp.common.components.buttons.BackButton
import com.example.careiroapp.feiras.ui.components.FeiraDescription
import com.example.careiroapp.feiras.ui.components.FeiraLocalizacao
import com.example.careiroapp.feiras.ui.components.FeiraTitle
import com.example.careiroapp.feiras.ui.viewmodel.FeiraViewModel

@Composable
fun SingleFeiraView(
    navController: NavController,
    feiraViewModel: FeiraViewModel,
    resetScrollFunction: () -> Unit
) {

    val context: Context = LocalContext.current
    val uiState by feiraViewModel.feiraUiState.collectAsState()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    BackHandler() {
        navController.popBackStack()
        feiraViewModel.cleanSelectedFeira()
        resetScrollFunction()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        BackButton(
            onClick = {
                navController.popBackStack()
                feiraViewModel.cleanSelectedFeira()
            }
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(),
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
            SingleImage(uiState.selectedFeira?.image)
        }
        Spacer(Modifier.height(24.dp))
        FeiraTitle(
            feiraName = uiState.selectedFeira?.nome ?: "",
            feiraDataHora = uiState.selectedFeira?.dataHora ?: ""
        )
        Spacer(Modifier.height(24.dp))
        FeiraLocalizacao(
            localizacao = uiState.selectedFeira?.localizacao ?: ""
        )
        Spacer(Modifier.height(24.dp))
        FeiraDescription(
            description = uiState.selectedFeira?.descricao ?: ""
        )
        Spacer(Modifier.height(24.dp))
    }
}

@Preview
@Composable
private fun SingleFeiraViewPreview() {}