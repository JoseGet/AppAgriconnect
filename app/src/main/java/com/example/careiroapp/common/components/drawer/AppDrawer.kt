package com.example.careiroapp.common.components.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.careiroapp.R
import com.example.careiroapp.loginCadastro.ui.viewmodel.LoginCadastroViewModel
import com.example.careiroapp.navigation.NavigationItem
import com.example.careiroapp.profile.ui.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    tabBarNavController: NavController,
    closeDrawerFunction: () -> Unit,
    resetScrollFunction: () -> Unit,
    backToLogin: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    loginCadastroViewModel: LoginCadastroViewModel = hiltViewModel()
) {
    val userState by viewModel.uiState.collectAsStateWithLifecycle()

    val noProfileImage = R.drawable.no_profile
    val context = LocalContext.current
    val uriImage = "android.resource://${context.packageName}/$noProfileImage"

    ModalDrawerSheet(
        drawerShape = RectangleShape,
        drawerContainerColor = Color.White
    ) {
        Column {
            DrawerItemProfile(
                name = userState.name,
                image = if (userState.fotoPerfil == "") uriImage else userState.fotoPerfil,
                onClick = {
                    closeDrawerFunction()
                    tabBarNavController.navigate(NavigationItem.Profile.route)
                    resetScrollFunction()
                }
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(16.dp))
                DrawerItem(
                    text = stringResource(R.string.produtos),
                    onClick = {
                        closeDrawerFunction()
                        tabBarNavController.navigate(NavigationItem.Produtos.route)
                        resetScrollFunction()
                    }
                )
                HorizontalDivider(Modifier.padding(horizontal = 15.dp))
                DrawerItem(
                    text = stringResource(R.string.feiras),
                    onClick = {
                        closeDrawerFunction()
                        tabBarNavController.navigate(NavigationItem.Feiras.route)
                        resetScrollFunction()
                    }
                )
                HorizontalDivider(Modifier.padding(horizontal = 15.dp))
                DrawerItem(
                    text = stringResource(R.string.associacoes),
                    onClick = {
                        closeDrawerFunction()
                        tabBarNavController.navigate(NavigationItem.Associacoes.route)
                        resetScrollFunction()
                    }
                )
                HorizontalDivider(Modifier.padding(horizontal = 15.dp))
                DrawerItem(
                    text = stringResource(R.string.assinaturas),
                    onClick = {}
                )
                HorizontalDivider(Modifier.padding(horizontal = 15.dp))
                DrawerItem(
                    text = stringResource(R.string.sobre_nos),
                    onClick = {
                        closeDrawerFunction()
                        tabBarNavController.navigate(NavigationItem.SobreNos.route)
                        resetScrollFunction()
                    }
                )
                HorizontalDivider(Modifier.padding(horizontal = 15.dp))
                DrawerItem(
                    text = stringResource(R.string.sair),
                    onClick = {
                        loginCadastroViewModel.logout(
                            backToLogin = backToLogin
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun AppDrawerPreview() {
    AppDrawer(
        tabBarNavController = rememberNavController(),
        closeDrawerFunction = {},
        resetScrollFunction = {},
        backToLogin = {}
    )
}