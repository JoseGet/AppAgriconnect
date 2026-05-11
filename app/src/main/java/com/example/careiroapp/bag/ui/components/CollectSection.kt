package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun CollectSection(
    collectLocation: String,
    collectTime: String,
    collectDate: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.retirada),
            fontFamily = montserratBoldFontFamily,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            CollectSectionRow(painter = painterResource(R.drawable.map_marker), collectLocation)
            CollectSectionRow(painter = painterResource(R.drawable.clock),collectTime)
            CollectSectionRow(painter = painterResource(R.drawable.calendar),collectDate)
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Composable
fun CollectSectionRow(
    painter: Painter,
    content: String
) {
    Row() {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = colorResource(R.color.dark_green)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            content,
            fontFamily = montserratRegularFontFamily,
            fontSize = 16.sp,
            color = colorResource(R.color.black)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CollectSectionPreview() {
    CollectSection(
        "Feira Exemplo","11h - 12h","24/08"
    )
}