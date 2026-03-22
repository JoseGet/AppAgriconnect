package com.example.careiroapp.bag.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun CheckoutFinalStepView(
    padding: PaddingValues,
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 15.dp, vertical = 24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Pedido feito!",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = montserratBoldFontFamily,
                color = colorResource(R.color.dark_green)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutFinalStepViewPreview() {
    CheckoutFinalStepView(
        padding = PaddingValues()
    )
}