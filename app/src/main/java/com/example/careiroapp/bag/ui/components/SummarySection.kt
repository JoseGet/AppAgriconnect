package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily
import com.example.careiroapp.data.room.entities.BagItem

@Composable
fun SummarySection(
    list: List<BagItem>,
    total: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.resumo_compra),
            fontFamily = montserratBoldFontFamily,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                stringResource(R.string.resumo_compra_description),
                fontFamily = montserratRegularFontFamily,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            SummaryProductsHeader()
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .height(56.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(list) { product ->
                    SummaryProductCard(
                        image = product.imageUrl,
                        name = product.name,
                        qntd = product.quantity,
                        price = product.price
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Total da compra: ",
                fontSize = 16.sp,
                fontFamily = montserratRegularFontFamily,
                color = colorResource(R.color.dark_green)
            )
            Text(
                "R$$total",
                fontSize = 24.sp,
                fontFamily = montserratBoldFontFamily,
                color = colorResource(R.color.dark_green)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummarySectionPreview() {
    SummarySection(
        list = listOf(),
        total = 4.1f
    )
}