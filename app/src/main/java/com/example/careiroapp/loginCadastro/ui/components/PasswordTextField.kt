package com.example.careiroapp.loginCadastro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun PassWordTextField(
    state: TextFieldState,
    keyboardType: KeyboardType = KeyboardType.Text,
) {

    Column() {
        Text(
            stringResource(R.string.senha),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = montserratRegularFontFamily,
                color = Color.Black
            )
        )
        Spacer(Modifier.height(8.dp))
        Surface(
            modifier = Modifier
                .background(Color.White)
                .height(48.dp)
                .fillMaxWidth()
                .border(
                    1.dp,
                    colorResource(R.color.search_bar_border_color),
                    RoundedCornerShape(16.dp)
                ),
            color = colorResource(R.color.light_background)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicSecureTextField(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    state = state,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType
                    ),
                    textObfuscationMode = TextObfuscationMode.RevealLastTyped,
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    decorator = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (state.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.digite_senha),
                                    style = TextStyle(
                                        fontFamily = montserratRegularFontFamily,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.search_bar_border_color)
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)

private fun PasswordTextFieldPreview() {
    PassWordTextField(
        state = rememberTextFieldState()
    )
}
