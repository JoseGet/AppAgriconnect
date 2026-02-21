package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun DateTimeChip(
    date: String,
    time: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val green = colorResource(R.color.dark_green)

    Card(
        modifier = Modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, green)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(if (selected) green else Color.White)
                .clickable { onClick() }
                .padding(vertical = 8.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date,
                color = if (selected) Color.White else green,
                fontFamily = montserratBoldFontFamily,
                fontSize = 14.sp
            )
            Text(
                text = time,
                color = if (selected) Color.White else green,
                fontFamily = montserratRegularFontFamily,
                fontSize = 14.sp
            )
        }
    }
}
