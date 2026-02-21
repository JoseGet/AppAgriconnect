package com.example.careiroapp.associacoes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun AssociacaoProductCard(
    productName: String,
    productPrice: Float,
    productImage: String,
    onClick:() -> Unit,
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(productImage)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .size(173.dp),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .wrapContentHeight(),
        ) {
            Text(
                productName,
                style = TextStyle(
                    fontFamily = montserratBoldFontFamily,
                    fontSize = 16.sp,
                    color = colorResource(R.color.dark_gray)
                )
            )
            Text(
                "R$ $productPrice",
                style = TextStyle(
                    fontFamily = montserratBoldFontFamily,
                    fontSize = 16.sp,
                    color = colorResource(R.color.dark_green)
                )
            )
            OutlineAppButton(
                text = stringResource(R.string.adicionar),
                modifier = Modifier
                    .wrapContentWidth(),
                onClick = onClick,
                icon = painterResource(R.drawable.bag)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AssociacaoProductCardPreview() {

    val produto1 = R.drawable.tomates
    val context = LocalContext.current
    val uriProduto1 = "android.resource://${context.packageName}/$produto1"

    AssociacaoProductCard(
        productImage = uriProduto1,
        productName = "Tomate",
        productPrice = 4.0f,
        onClick = {}
    )
}