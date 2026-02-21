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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun EmailTextField(
    email: String,
    onValueChange: (String) -> Unit,
    hasEmailError: Boolean
) {

    Column() {
        Text(
            stringResource(R.string.email),
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
                    value = email,
                    onValueChange = onValueChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (email.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.digete_email),
                                    style = TextStyle(
                                        fontFamily = montserratRegularFontFamily,
                                        fontSize = 14.sp,
                                        color = colorResource(R.color.search_bar_border_color)
                                    )
                                )
                            }
                            innerTextField()
                        }
                        if (hasEmailError) {
                            Text(
                                text = "CPF inválido",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun EmailTextFieldPreview() {
    EmailTextField(
        email = "",
        onValueChange = {},
        hasEmailError = true
    )
}