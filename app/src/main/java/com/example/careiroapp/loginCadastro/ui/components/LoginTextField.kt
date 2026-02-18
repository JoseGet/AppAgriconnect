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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun LoginTextField(
    title: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxChar: Int? = null,
    state: TextFieldState,
    outputTransformation: OutputTransformation? = null,
) {

    Column() {
        Text(
            title,
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
                BasicTextField(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    state = state,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType
                    ),
                    inputTransformation = if (maxChar != null) {
                        InputTransformation.maxLength(maxChar)
                    } else null,
                    outputTransformation = outputTransformation,
                    lineLimits = TextFieldLineLimits.SingleLine,
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
                                    text = placeholder,
                                    style = TextStyle(
                                        fontFamily = montserratRegularFontFamily,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.search_bar_border_color)
                                    )
                                )
                            }
                            innerTextField()
                        }
                    },
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)

private fun LoginTextFieldPreview() {
    LoginTextField(
        title = "Email",
        placeholder = "Digite seu email",
        state = rememberTextFieldState()
    )
}
