package com.example.careiroapp.bag.ui

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.bag.ui.components.FinalStepButtonsRow
import com.example.careiroapp.bag.ui.viewmodel.OrderModel
import com.example.careiroapp.bag.ui.viewmodel.PaymentType
import com.example.careiroapp.common.components.buttons.AppButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun CheckoutFinalStepView(
    padding: PaddingValues,
    orderData: OrderModel,
    isPaymentPixDone: Boolean,
    onClickLeftButton: () -> Unit,
    onClickRightButton: () -> Unit
) {

    val walletAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wallet_animation))
    val successPayment by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.checkmark_animation))

    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 15.dp, vertical = 24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (orderData.paymentType) {
            PaymentType.PIX -> {
                if (!isPaymentPixDone) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Pedido feito!",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = montserratBoldFontFamily,
                                color = colorResource(R.color.dark_green)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Faça o pagamento PIX para confirmar o pedido:",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = montserratRegularFontFamily,
                                color = colorResource(R.color.dark_green)
                            )
                        )
                        Spacer(modifier = Modifier.height(19.dp))
                        Image(
                            painter = rememberAsyncImagePainter(orderData.pixQrCode),
                            contentDescription = null,
                            modifier = Modifier.size(300.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        AppButton(
                            text = "Copiar codigo PIX",
                            modifier = Modifier,
                            onClick = {
                                val clipData = ClipData.newPlainText("plain text", orderData.pixPayload)
                                val clipEntry = ClipEntry(clipData)
                                clipboardManager.setClip(clipEntry)
                            },
                            icon = painterResource(R.drawable.copy),
                            containerColor = colorResource(R.color.dark_green)
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LottieAnimation(
                                successPayment
                            )
                            Text(
                                stringResource(R.string.payment_pix_done_title),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = montserratBoldFontFamily,
                                    color = colorResource(R.color.dark_green)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                stringResource(R.string.payment_pix_done_description),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = montserratRegularFontFamily,
                                    color = colorResource(R.color.dark_gray),
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        FinalStepButtonsRow(
                            onClickLeftButton = onClickLeftButton,
                            onClickRightButton = onClickRightButton
                        )
                    }
                }
            }
            PaymentType.DINHEIRO -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Pedido feito!",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = montserratBoldFontFamily,
                                color = colorResource(R.color.dark_green)
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Seu pedido foi registrado com sucesso. Por favor, prepare o valor em dinheiro e pague diretamente na retirada.",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = montserratRegularFontFamily,
                                textAlign = TextAlign.Center,
                                color = colorResource(R.color.dark_green)
                            )
                        )
                        LottieAnimation(walletAnimation)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Valor a pagar em dinheiro:",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = montserratRegularFontFamily,
                                color = colorResource(R.color.dark_gray)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "R$ ${orderData.totalValue}",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = montserratBoldFontFamily,
                                color = colorResource(R.color.dark_green)
                            )
                        )
                    }
                    FinalStepButtonsRow(
                        onClickLeftButton = onClickLeftButton,
                        onClickRightButton = onClickRightButton
                    )
                }
            }

            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutFinalStepViewPixPreview() {

    val order = OrderModel(
        paymentType = PaymentType.PIX
    )

    CheckoutFinalStepView(
        padding = PaddingValues(),
        orderData = order,
        isPaymentPixDone = false,
        onClickLeftButton = {},
        onClickRightButton = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CheckoutFinalStepViewPixDonePreview() {

    val order = OrderModel(
        paymentType = PaymentType.PIX
    )

    CheckoutFinalStepView(
        padding = PaddingValues(),
        orderData = order,
        isPaymentPixDone = true,
        onClickLeftButton = {},
        onClickRightButton = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CheckoutFinalStepViewDinheiroPreview() {

    val order = OrderModel(
        paymentType = PaymentType.DINHEIRO
    )

    CheckoutFinalStepView(
        padding = PaddingValues(),
        orderData = order,
        isPaymentPixDone = false,
        onClickLeftButton = {},
        onClickRightButton = {},
    )
}