package com.example.careiroapp.bag.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.careiroapp.bag.ui.components.CheckoutMainButton
import com.example.careiroapp.bag.ui.components.CollectSection
import com.example.careiroapp.bag.ui.components.PaymentSection
import com.example.careiroapp.bag.ui.components.Stepper
import com.example.careiroapp.bag.ui.components.SummarySection
import com.example.careiroapp.bag.ui.viewmodel.OrderModel
import com.example.careiroapp.data.room.entities.BagItem

@Composable
fun CheckoutStepThreeView(
    padding: PaddingValues,
    orderData: OrderModel,
    productsList: List<BagItem>,
    totalValue: Double
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 15.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Stepper(3)
        PaymentSection()
        CollectSection(
            orderData.address,
            orderData.time,
            orderData.date
        )
        SummarySection(
            list = productsList,
            total = totalValue.toFloat()
        )
        CheckoutMainButton(
            onButtonClick = {}
        )
    }
}

