package com.example.careiroapp.profile.ui

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.careiroapp.R
import com.example.careiroapp.bag.data.models.PixStatusResponse
import com.example.careiroapp.common.components.buttons.AppButton
import com.example.careiroapp.common.components.buttons.BackButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily
import com.example.careiroapp.navigation.NavigationItem

@Composable
fun PixStatusView(
    navController: NavHostController,
    pixStatus: PixStatusResponse? = null,
    isPixPaymentDone: Boolean = false
) {

    val clipboardManager = LocalClipboardManager.current
    val successPayment by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.checkmark_animation))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        BackButton(onClick = {
            if (!isPixPaymentDone) {
                navController.popBackStack()
            } else {
                navController.popBackStack(NavigationItem.Profile.route, false)
            }
        })
        Spacer(Modifier.height(16.dp))
        Text(
            "Status do pagamento",
            fontFamily = montserratBoldFontFamily,
            fontSize = 18.sp,
            color = colorResource(R.color.dark_green)
        )
        Spacer(Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isPixPaymentDone) {
                Image(
                    painter = rememberAsyncImagePainter(pixStatus?.data?.firstOrNull()?.brCodeBase64),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                AppButton(
                    text = "Copiar codigo PIX",
                    modifier = Modifier,
                    onClick = {
                        val clipData = ClipData.newPlainText(
                            "plain text",
                            pixStatus?.data?.firstOrNull()?.brCode
                        )
                        val clipEntry = ClipEntry(clipData)
                        clipboardManager.setClip(clipEntry)
                    },
                    icon = painterResource(R.drawable.copy),
                    containerColor = colorResource(R.color.dark_green)
                )
            } else {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PixStatusViewPreview() {
    PixStatusView(navController = rememberNavController())
}
