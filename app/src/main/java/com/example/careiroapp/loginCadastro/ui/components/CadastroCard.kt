package com.example.careiroapp.loginCadastro.ui.components


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton
import com.example.careiroapp.common.montserratBoldFontFamily
import com.example.careiroapp.common.montserratRegularFontFamily
import com.example.careiroapp.loginCadastro.ui.components.formatters.CpfFormatter
import com.example.careiroapp.loginCadastro.ui.components.formatters.PhoneNumberFormatter

@Composable
fun CadastroCard(
    onClickFazerLogin: () -> Unit,
    onClickFazerCadastro: (String, String, String, String, String, String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var nome = rememberTextFieldState("")
    var cpf = rememberTextFieldState("")
    var telefone = rememberTextFieldState("")
    var email = rememberTextFieldState("")
    var senha = rememberTextFieldState("")

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )

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
                stringResource(R.string.cadastro),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = montserratBoldFontFamily,
                    color = colorResource(R.color.dark_green)
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                stringResource(R.string.crie_conta_agriconnect),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = montserratRegularFontFamily,
                    color = colorResource(R.color.dark_gray)
                )
            )
            Spacer(Modifier.height(24.dp))
            CadastroImageProfile(
                image = imageUri,
                onClick = {
                    galleryLauncher.launch("image/*")
                }
            )
            Spacer(Modifier.height(24.dp))
            LoginTextField(
                title = stringResource(R.string.cadastro_nome),
                placeholder = stringResource(R.string.cadastro_digite_nome),
                state = nome
            )
            Spacer(Modifier.height(24.dp))
            LoginTextField(
                title = stringResource(R.string.cadastro_cpf),
                placeholder = stringResource(R.string.cadastro_digite_cpf),
                keyboardType = KeyboardType.Number,
                maxChar = 11,
                state = cpf,
                outputTransformation = CpfFormatter(),
            )
            Spacer(Modifier.height(24.dp))
            LoginTextField(
                title = stringResource(R.string.cadastro_telefone),
                placeholder = stringResource(R.string.cadastro_digite_telefone),
                keyboardType = KeyboardType.Number,
                maxChar = 11,
                outputTransformation = PhoneNumberFormatter(),
                state = telefone
            )
            Spacer(Modifier.height(24.dp))
            LoginTextField(
                title = stringResource(R.string.email),
                placeholder = stringResource(R.string.digete_email),
                keyboardType = KeyboardType.Email,
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
                    onClickFazerCadastro(
                        nome.text.toString(),
                        cpf.text.toString(),
                        telefone.text.toString(),
                        email.text.toString(),
                        senha.text.toString(),
                        imageUri.toString()
                    )
                },
                text = stringResource(R.string.fazer_cadastro),
                isActivate = false
            )
            Spacer(Modifier.height(24.dp))
            Row() {
                Text(
                    text = stringResource(R.string.ja_tem_conta),
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
                            onClickFazerLogin()
                        },
                    text = stringResource(R.string.faca_login),
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
private fun CadastroCardPreview() {
    CadastroCard(
        onClickFazerLogin = {},
        onClickFazerCadastro = { a, b, c, d, e, f -> }
    )
}