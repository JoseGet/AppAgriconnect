package com.example.careiroapp.loginCadastro.ui

import android.app.Activity
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.example.careiroapp.R
import com.example.careiroapp.loginCadastro.ui.components.CadastroCard
import com.example.careiroapp.loginCadastro.ui.components.LoginCard
import com.example.careiroapp.loginCadastro.ui.viewmodel.CardState
import com.example.careiroapp.loginCadastro.ui.viewmodel.LoginCadastroViewModel
import com.example.careiroapp.navigation.NavigationItem

@Composable
fun LoginView(
    navController: NavController
) {
    val viewModel: LoginCadastroViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    BackHandler() {
        if (uiState.cardState == CardState.CADASTRO) {
            viewModel.changeCardState(CardState.LOGIN)
        } else {
            (context as? Activity)?.finish()
        }
    }

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(R.drawable.login_background),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        color = colorResource(R.color.black).copy(alpha = 0.7f),
                        blendMode = BlendMode.SrcOver
                    )
                )
                .padding(horizontal = 32.dp)
                .verticalScroll(scrollState)
                .padding(vertical = 45.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .width(240.dp)
                    .height(100.dp),
                painter = painterResource(R.drawable.agriconnect_login_logo),
                contentDescription = null
            )
            Spacer(Modifier.height(24.dp))
            when (uiState.cardState) {
                CardState.LOGIN -> LoginCard(
                    onClickCadastrar = {
                        viewModel.changeCardState(CardState.CADASTRO)
                    },
                    onClickEntrar = { email, senha ->
                        viewModel.login(
                            email,
                            senha,
                            goToMainView = {
                                navController.navigate(NavigationItem.Main.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                                Log.i("NAV", "Indo para a Home")
                            }
                        )
                    }
                )

                CardState.CADASTRO -> CadastroCard(
                    onClickFazerLogin = {
                        viewModel.changeCardState(CardState.LOGIN)
                    },
                    onClickFazerCadastro = { nome, cpf, telefone, email, senha, image ->
                        viewModel.makeCadastro(
                            context,
                            nome,
                            cpf,
                            telefone,
                            email,
                            senha,
                            image
                        )
                    }

                )
            }
        }
        if (uiState.isLoading) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = R.drawable.load,
                    imageLoader = imageLoader
                ),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginViewPreview() {
    LoginView(
        navController = rememberNavController()
    )
}
