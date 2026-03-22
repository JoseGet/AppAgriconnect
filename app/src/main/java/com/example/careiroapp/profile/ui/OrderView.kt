package com.example.careiroapp.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.SummaryProductsHeader
import com.example.careiroapp.bag.ui.viewmodel.OrderModel
import com.example.careiroapp.common.components.buttons.BackButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.profile.ui.components.OrderDataSummary

@Composable
fun OrderView(
    navController: NavHostController,
    order: OrderModel? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        BackButton(
            onClick = {
                navController.popBackStack()
            }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Pedido #${order?.orderId}",
            fontFamily = montserratBoldFontFamily,
            fontSize = 18.sp,
            color = colorResource(R.color.dark_green)
        )
        Spacer(Modifier.height(4.dp))
        SummaryProductsHeader()
        Spacer(modifier = Modifier.height(16.dp))
        Column (
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            for (product in listOf<ProductModel>()) {
//                SummaryProductCard(
//                    image = product.imageUrl,
//                    name = product.name,
//                    qntd = product.quantity,
//                    price = product.price
//                )
//            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        OrderDataSummary()
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderViewPreview() {
    OrderView(
        navController = rememberNavController(),
        OrderModel(
            1
        )
    )
}