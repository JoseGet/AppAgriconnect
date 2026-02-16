package com.example.careiroapp.feiras.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun FeiraLocalizacao(
    localizacao: String
) {
    Column {
        Text(
            stringResource(R.string.localizacao),
            fontSize = 16.sp,
            fontFamily = montserratBoldFontFamily,
            color = colorResource(R.color.dark_gray)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            localizacao,
            fontSize = 14.sp,
            fontFamily = montserratRegularFontFamily,
            color = colorResource(R.color.dark_gray)
        )
    }
}

@Composable
@Preview
private fun FeiraLocalizacaoPreview() {
    FeiraLocalizacao(
        localizacao = stringResource(R.string.default_big_text)
    )
}