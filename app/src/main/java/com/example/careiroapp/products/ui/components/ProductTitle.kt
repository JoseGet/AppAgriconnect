package com.example.careiroapp.products.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun ProductTitle(
    productName: String,
    productPrice: Float,
    promotionProductPrice: Double?,
    unity: String
) {
    Column() {
        Text(
            "$productName - $unity",
            fontFamily = montserratBoldFontFamily,
            fontSize = 18.sp,
            color = colorResource(R.color.dark_gray)
        )
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "R$$productPrice",
                style = TextStyle(
                    fontFamily = montserratBoldFontFamily,
                    color = colorResource(R.color.dark_gray),
                    fontWeight = if (promotionProductPrice != null) FontWeight.Normal else FontWeight.Bold,
                    textDecoration = if (promotionProductPrice != null) TextDecoration.LineThrough else TextDecoration.None,
                    fontStyle = if (promotionProductPrice != null) FontStyle.Italic else FontStyle.Normal,
                    fontSize = if (promotionProductPrice != null) 16.sp else 18.sp
                ),
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (promotionProductPrice != null) {
                Text(
                    "R$ $promotionProductPrice",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dark_green),
                        fontSize = 18.sp
                    ),
                )
            }
        }
    }
}

@Composable
@Preview
private fun ProductTitlePreview() {
    ProductTitle(
        "Abobora",
        8.50f,
        promotionProductPrice = null,
        unity = "kg"
    )
}