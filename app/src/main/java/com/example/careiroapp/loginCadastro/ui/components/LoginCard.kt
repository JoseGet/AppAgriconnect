package com.example.careiroapp.loginCadastro.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun LoginCard(
    onClickCadastrar: () -> Unit,
    onClickEntrar: (String, String) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    var email = rememberTextFieldState("")
    var senha = rememberTextFieldState("")

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.light_background)
        ),
        border = BorderStroke(1.dp, color = colorResource(R.color.black))
    ) {
        Column(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 32.dp)
                .wrapContentSize()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.login),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = montserratBoldFontFamily,
                    color = colorResource(R.color.dark_green)
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                stringResource(R.string.acesse_sua_conta),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = montserratRegularFontFamily,
                    color = colorResource(R.color.dark_gray)
                )
            )
            Spacer(Modifier.height(24.dp))
            LoginTextField(
                title = stringResource(R.string.email),
                placeholder = stringResource(R.string.digete_email),
                state = email
            )
            Spacer(Modifier.height(24.dp))
            PassWordTextField(
                state = senha,
                keyboardType = KeyboardType.Password
            )
            Spacer(Modifier.height(24.dp))
            OutlineAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                icon = null,
                onClick = {
                    onClickEntrar(email.text.toString(), senha.text.toString())
                },
                text = stringResource(R.string.entrar),
                isActivate = false
            )
            Spacer(Modifier.height(24.dp))
            Row() {
                Text(
                    text = stringResource(R.string.nao_tenho_conta),
                    style = TextStyle(
                        fontFamily = montserratRegularFontFamily,
                        fontSize = 16.sp,
                        color = colorResource(R.color.dark_gray)
                    )
                )
                Text(
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onClickCadastrar()
                        },
                    text = stringResource(R.string.cadastrese),
                    style = TextStyle(
                        fontFamily = montserratRegularFontFamily,
                        fontSize = 16.sp,
                        color = colorResource(R.color.dark_green),
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LoginCardPreview() {
    LoginCard(
        onClickCadastrar = {},
        onClickEntrar = { email, senha -> }
    )
}