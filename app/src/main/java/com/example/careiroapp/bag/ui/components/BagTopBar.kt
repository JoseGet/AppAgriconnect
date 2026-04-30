package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BagTopBar(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Voltar",
                    tint = colorResource(R.color.dark_green)
                )
            }
        },
        title = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painterResource(R.drawable.ic_agriconnect),
                    contentDescription = "agriconnect logo",
                )
                Text(stringResource(R.string.your_bag),
                    fontFamily = montserratBoldFontFamily,
                    fontSize = 16.sp,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.light_gray),
            titleContentColor = colorResource(R.color.top_bar_title_color)
        )
    )
}

@Preview
@Composable
private fun BagTopBarPreview() {
    BagTopBar() { }
}