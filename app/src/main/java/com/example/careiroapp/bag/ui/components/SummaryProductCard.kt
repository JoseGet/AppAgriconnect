package com.example.careiroapp.bag.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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

@SuppressLint("DefaultLocale")
@Composable
fun SummaryProductCard(
    image: String,
    name: String,
    qntd: Int,
    price: Float
) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                name,
                fontSize = 14.sp,
                fontFamily = montserratBoldFontFamily,
                color = colorResource(R.color.dark_gray)
            )
        }
        Text(
            qntd.toString(),
            fontSize = 16.sp,
            modifier = Modifier.width(60.dp),
            textAlign = TextAlign.Center,
            fontFamily = montserratRegularFontFamily,
            color = colorResource(R.color.dark_green)
        )
        Text(
            "R$${String.format("%.2f", qntd*price)}",
            fontSize = 14.sp,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.End,
            fontFamily = montserratBoldFontFamily,
            color = colorResource(R.color.dark_gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryProductCardPreview() {
    SummaryProductCard(
        "",
        "Abobora",
        2,
        2.9f
    )
}