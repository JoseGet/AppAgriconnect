package com.example.careiroapp.common.components.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

@Composable
fun AppFooter(
    navController: NavController,
    resetScrollFunction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = colorResource(R.color.light_gray))
            .padding(vertical = 34.dp, horizontal = 44.dp),
    ) {
        ColumnsFooter(
            navController = navController,
            resetScrollFunction = resetScrollFunction
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            BottomFooter()
        }
    }
}

@Composable
@Preview
private fun AppFooterPreview() {
    AppFooter(
        navController = rememberNavController(),
        resetScrollFunction = {}
    )
}
