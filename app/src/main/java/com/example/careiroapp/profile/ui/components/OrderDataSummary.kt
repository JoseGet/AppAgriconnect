package com.example.careiroapp.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.CollectSectionRow
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun OrderDataSummary(
    local: String,
    date: String,
    time: String,
    paymentType: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.light_gray)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                "Retirada",
                fontFamily = montserratBoldFontFamily,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            CollectSectionRow(painter = painterResource(R.drawable.map_marker), local)
            Spacer(modifier = Modifier.height(8.dp))
            CollectSectionRow(painter = painterResource(R.drawable.clock),time)
            Spacer(modifier = Modifier.height(8.dp))
            CollectSectionRow(painter = painterResource(R.drawable.calendar),date)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Pagamento",
                fontFamily = montserratBoldFontFamily,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                paymentType,
                fontFamily = montserratRegularFontFamily,
                fontSize = 16.sp
            )
            Text(
                ""
            )
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlineAppButton(
                    text = "Cancelar pedido",
                    modifier = Modifier,
                    onClick = {},
                    icon = null,
                    isActivate = false
                )
            }
        }
    }
}


@Preview
@Composable
private fun OrderDataSummaryPreview() {
    OrderDataSummary(
        local = "Local",
        date = "Data",
        time = "Hora",
        paymentType = "Pagamento"
    )
}