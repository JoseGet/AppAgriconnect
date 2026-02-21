package com.example.careiroapp.products.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratMediumFontFamily

@Composable
fun ProductCard(
    modifier: Modifier,
    image: String,
    nomeProduto: String,
    precoProduto: Float,
    isPromocao: Boolean,
    precoPromocao: Double?,
    haveButton: Boolean,
    onClick: () -> Unit,
    onButtonClick: () -> Unit = {}
) {

    val modifiedPrecoProduto = String.format("%.2f", precoProduto).replace('.', ',');
    val modifiedPrecoPromocao = String.format("%.2f", precoPromocao).replace('.', ',');

    Card(
        modifier = modifier
            .wrapContentSize()
            .background(color = Color.Transparent),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(25.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .background(color = colorResource(R.color.light_background))
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(173.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                nomeProduto,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                    ),
                style = TextStyle(
                    color = colorResource(R.color.dark_gray),
                    fontSize = 14.sp,
                    fontFamily = montserratMediumFontFamily,
                )
            )
            Row(
                modifier = Modifier
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "R$ $modifiedPrecoProduto",
                    style = TextStyle(
                        fontWeight = if (isPromocao && precoPromocao != null) FontWeight.Normal else FontWeight.Bold,
                        color = colorResource(R.color.dark_green),
                        textDecoration = if (isPromocao && precoPromocao != null) TextDecoration.LineThrough else TextDecoration.None,
                        fontStyle = if (isPromocao && precoPromocao != null) FontStyle.Italic else FontStyle.Normal,
                        fontSize = if (isPromocao && precoPromocao != null) 14.sp else 16.sp
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (isPromocao && precoPromocao != null) {
                    Text(
                        "R$ $modifiedPrecoPromocao",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.dark_green),
                            fontSize = 16.sp
                        ),
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (haveButton) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = 16.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    Arrangement.Center
                ) {
                    OutlineAppButton(
                        text = stringResource(R.string.adicionar),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 16.dp),
                        onClick = onButtonClick,
                        icon = painterResource(R.drawable.bag)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ProductCardPreview() {
    Column {
        ProductCard(
            modifier = Modifier
                .weight(1f),
            image = "",
            nomeProduto = "Banana",
            precoProduto = 10.00f,
            isPromocao = true,
            precoPromocao = 7.00,
            haveButton = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(10.dp))
        ProductCard(
            modifier = Modifier
                .weight(1f),
            image = "",
            nomeProduto = "Banana",
            precoProduto = 10.00f,
            isPromocao = false,
            precoPromocao = 7.00,
            haveButton = false,
            onClick = {}
        )
    }
}