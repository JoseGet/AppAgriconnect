package com.example.careiroapp.common.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.AppButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun CardCadastroAssociacao(
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.light_gray)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 14.dp, horizontal = 44.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Faça parte do Agriconnect",
                    style = TextStyle(
                        color = colorResource(R.color.dark_green),
                        fontSize = 18.sp,
                        fontFamily = montserratBoldFontFamily,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Solicite o cadastro da sua associação no Agriconnect. É simples, rápido e prático. Conecte seus produtores e seus produtos ao maior ecommerce da agricultura amazonense e transforme suas realidades.",
                    modifier = Modifier
                        .width(273.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = montserratRegularFontFamily,
                        color = Color.Black
                    ),
                )
                Spacer(modifier = Modifier.height(24.dp))
                AppButton(
                    modifier = Modifier,
                    text = "Cadastrar associação",
                    onClick = onButtonClick,
                    icon = painterResource(R.drawable.user_add),
                    containerColor = colorResource(R.color.dark_green)
                )
            }
        }
    }
}

@Composable
@Preview
fun CardCadastroAssociacaoPreview() {
    CardCadastroAssociacao(
        {}
    )
}