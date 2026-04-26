package com.example.careiroapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.careiroapp.bag.ui.viewmodel.BagViewModel
import com.example.careiroapp.common.components.drawer.AppDrawer
import com.example.careiroapp.common.components.footer.AppFooter
import com.example.careiroapp.common.components.header.AppHeader
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.navigation.NavigationItem
import com.example.careiroapp.navigation.TapBarNavHost
import kotlinx.coroutines.launch

@Composable
fun BaseView(
    navController: NavController
) {
    val scrollState = rememberScrollState();
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val tabBarNavController = rememberNavController()

    val bagViewModel: BagViewModel = hiltViewModel()
    val bagItems: List<BagItem> by bagViewModel.cartItems.collectAsStateWithLifecycle()

    val resetScroll: () -> Unit = {
        scope.launch {
            scrollState.scrollTo(0)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                tabBarNavController,
                closeDrawerFunction = {
                    scope.launch {
                        drawerState.close()
                    }
                },
                resetScrollFunction = resetScroll,
                backToLogin = {
                    navController.navigate(NavigationItem.Login.route)
                }
            )
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                AppHeader(
                    leftIconAction = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    navController,
                    tabBarNavController = tabBarNavController,
                    bagItemsCount = bagItems.size,
                    resetScroll
                )
            },
            content = { innerPadding ->
                val scrollTopOffsetPx = with(LocalDensity.current) {
                    innerPadding.calculateTopPadding().toPx()
                }
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    TapBarNavHost(
                        navController = tabBarNavController,
                        resetScrollFunction = resetScroll,
                        scrollState = scrollState,
                        scrollTopOffsetPx = scrollTopOffsetPx
                    )
                    Spacer(Modifier.height(20.dp))
                    AppFooter(
                        navController = tabBarNavController,
                        resetScrollFunction = resetScroll
                    )
                }
            }
        )
    }
}

@Composable
@Preview
private fun MainViewPreview(

) {
    BaseView(
        navController = rememberNavController()
    )
}