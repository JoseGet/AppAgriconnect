package com.example.careiroapp.profile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun PersonalDataWidget(
    nomePerfil: String,
    emailPerfil: String,
    telefonePerfil: String,
) {
    Surface (
        modifier = Modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.search_bar_border_color)
        ),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                stringResource(R.string.dados_pessoais),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = montserratBoldFontFamily,
                    color = Color.Black
                )
            )
            Spacer(Modifier.height(16.dp))
            SinglePersonalDataRow(
                stringResource(R.string.nome_perfil),
                nomePerfil
            )
            Spacer(Modifier.height(8.dp))
            SinglePersonalDataRow(
                stringResource(R.string.email_perfil),
                emailPerfil
            )
            Spacer(Modifier.height(8.dp))
            SinglePersonalDataRow(
                stringResource(R.string.telefone_perfil),
                telefonePerfil
            )
//            Spacer(Modifier.height(16.dp))
//            OutlineAppButton(
//                text = stringResource(R.string.alterar_dados),
//                modifier = Modifier
//                    .fillMaxWidth(),
//                onClick = {},
//                icon = painterResource(R.drawable.pencil),
//            )
        }
    }
}

@Composable
@Preview
private fun PersonalDataWidgetPreview() {
    PersonalDataWidget(
        nomePerfil = "Maria Joana",
        emailPerfil = "maria.joana@gmail.com",
        telefonePerfil = "(92) 99123-4567"
    )
}