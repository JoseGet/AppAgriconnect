package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun BagProductCard(
    name: String,
    image: String,
    price: Float,
    amount: Int,
    increaseProduct: () -> Unit,
    decreaseProduct: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Card(
                modifier = Modifier
                    .wrapContentSize(),
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .size(56.dp),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    name,
                    fontFamily = montserratRegularFontFamily,
                    fontSize = 14.sp
                )
                Row {
                    Text(
                        "R$$price ",
                        fontFamily = montserratBoldFontFamily,
                        color = colorResource(R.color.dark_green),
                        fontSize = 14.sp
                    )
                    Text(
                        "/ un",
                        fontFamily = montserratRegularFontFamily,
                        color = colorResource(R.color.dark_green),
                        fontSize = 14.sp
                    )
                }
            }
        }

        CounterButton(
            amount = amount,
            decrease = { decreaseProduct() },
            increase = { increaseProduct() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BagProductCardPreview() {
    BagProductCard(
        name = "Banana",
        image = "",
        price = 2.0f,
        amount = 1,
        increaseProduct = {},
        decreaseProduct = {}
    )
}