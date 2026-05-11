package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun SummaryProductsHeader() {
    Card(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.light_gray)
        ),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Item",
                fontFamily = montserratBoldFontFamily,
                modifier = Modifier.weight(1f),
                color = colorResource(R.color.black)
            )
            Text(
                "Qtd.",
                fontFamily = montserratBoldFontFamily,
                modifier = Modifier.padding(end = 46.dp),
                color = colorResource(R.color.black)
            )
            Text(
                "Total",
                fontFamily = montserratBoldFontFamily,
                color = colorResource(R.color.black)
            )
        }
    }
}

@Preview
@Composable
fun SummaryProductsHeaderPreview() {
    SummaryProductsHeader()
}