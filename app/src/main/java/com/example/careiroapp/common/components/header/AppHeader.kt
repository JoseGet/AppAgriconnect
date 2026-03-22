package com.example.careiroapp.common.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.careiroapp.R
import com.example.careiroapp.navigation.NavigationItem

@Composable
fun AppHeader(
    leftIconAction: () -> Unit,
    appNavController: NavController,
    tabBarNavController: NavController,
    bagItemsCount: Int,
    resetScrollFunction: () -> Unit
) {
    Column (
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = colorResource(R.color.light_gray))
            .padding(top = 16.dp)
    ) {
        TopBar(
            leftIconOnClick = leftIconAction,
            rightIconAction = { appNavController.navigate(NavigationItem.Sacola.route) },
            bagItemsCount = bagItemsCount
        )
        AppSearchBar()
        Spacer(Modifier.height(16.dp))
        ModulesBar(
            navController = tabBarNavController,
            resetScrollFunction
        )
    }
}

@Composable
@Preview
fun AppHeaderPreview() {
    AppHeader(
        leftIconAction = {},
        appNavController = rememberNavController(),
        tabBarNavController = rememberNavController(),
        bagItemsCount = 0,
        resetScrollFunction = {}
    );
}