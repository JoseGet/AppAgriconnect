package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily
import java.util.Locale

@Composable
fun BagBottomBar(
    modifier: Modifier = Modifier,
    total: Double,
    onCheckout: () -> Unit = {}
) {
    BottomAppBar(
        modifier = modifier.height(96.dp),
        containerColor = colorResource(R.color.light_gray),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total: ")
                Text(
                    // formata para duas casas decimais com separador ponto e prefixo R$
                    String.format(Locale("pt", "BR"), "R$%.2f", total),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            Button(
                modifier = Modifier
                    .height(48.dp)
                    .wrapContentWidth(),
                onClick = onCheckout,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.dark_green),
                    contentColor = colorResource(R.color.light_background),
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(R.string.complete_order),
                        fontFamily = montserratBoldFontFamily,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.bag_button_arrow_right),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BagBottomBarPreview() {
    BagBottomBar(
        total = 0.0
    ) {  }
}
