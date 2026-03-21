package com.example.careiroapp.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun OrderCard(
    orderId: Int,
    orderTotalValue: Float,
    orderStatus: String,
    onClick: () -> Unit
) {

    val orderDefaultImage = R.drawable.order_image_default
    val image = "android.resource://${LocalContext.current.packageName}/$orderDefaultImage"

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.light_gray)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Pedido #$orderId",
                fontFamily = montserratBoldFontFamily,
                fontSize = 24.sp,
                color = colorResource(R.color.dark_gray)
            )
            OrderProductRow(
                status = orderStatus,
                total = orderTotalValue,
                image = image
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlineAppButton(
                    onClick = onClick,
                    isActivate = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    text = stringResource(R.string.ver_detalhes),
                    icon = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun OrderCardPreview() {
    OrderCard(
        orderId = 1,
        orderStatus = "Pedido concluido",
        orderTotalValue = 23.0f,
        onClick = {}
    )
}