package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.HorizontalDivider
import com.example.careiroapp.bag.ui.viewmodel.PaymentType
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun PaymentSection() {

    var selectedPayment by rememberSaveable { mutableStateOf<PaymentType?>(null) }

    Column {
        Text(
            text = stringResource(R.string.pagamento),
            fontFamily = montserratBoldFontFamily,
            fontSize = 18.sp,
            color = colorResource(R.color.dark_green)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.pagamento_description),
            fontFamily = montserratRegularFontFamily,
            fontSize = 14.sp,
            color = colorResource(R.color.dark_gray)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            PaymentRadioButton(
                title = stringResource(R.string.dinheiro),
                description = stringResource(R.string.dinheiro_description),
                isSelected = selectedPayment == PaymentType.DINHEIRO,
                onSelect = {
                    selectedPayment = PaymentType.DINHEIRO
                }
            )
            PaymentRadioButton(
                title = stringResource(R.string.pix),
                description = stringResource(R.string.pix_description),
                isSelected = selectedPayment == PaymentType.PIX,
                onSelect = {
                    selectedPayment = PaymentType.PIX
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentSectionPreview() {
    PaymentSection()
}