package com.example.careiroapp.profile.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
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
fun OrderProductRow(
    status: String,
    total: Float,
    image: String
) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(image)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                "R$${total}",
                fontSize = 14.sp,
                fontFamily = montserratBoldFontFamily,
                color = colorResource(R.color.dark_green)
            )
        }
        Column(
            modifier = Modifier
                .wrapContentSize(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                "Status:",
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                fontFamily = montserratRegularFontFamily,
                color = colorResource(R.color.dark_gray)
            )
            Text(
                status,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                fontFamily = montserratBoldFontFamily,
                color = colorResource(R.color.dark_gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderProductRowPreview() {

    val produto1 = R.drawable.tomates
    val uriProduto1 = "android.resource://${LocalContext.current.packageName}/$produto1"

    OrderProductRow(
        status = "Realizar retirada",
        total = 25.5f,
        image = uriProduto1
    )
}