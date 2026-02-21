package com.example.careiroapp.common.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun OutlineAppButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
    icon: Painter?,
    isActivate: Boolean = false
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(
            16.dp
        ),
        contentPadding = PaddingValues(horizontal = 12.dp),
        colors = ButtonColors(
            contentColor = if(isActivate) Color.White else colorResource(R.color.dark_green),
            containerColor = if(isActivate) colorResource(R.color.dark_green) else Color.White,
            disabledContentColor = Color.Red,
            disabledContainerColor = Color.Red
        ),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.dark_green)
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text,
                style = TextStyle(
                    fontFamily = montserratBoldFontFamily,
                    fontSize = 14.sp
                )
            )
            Spacer(Modifier.width(if (icon == null || text == "") 0.dp else 8.dp))
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = "",
                )
            }
        }
    }
}

@Composable
@Preview
private fun OutlineAppButtonPreview() {
    OutlineAppButton(
        "Text",
        modifier = Modifier.background(Color.White),
        onClick = {},
        icon = painterResource(R.drawable.bag),
        isActivate = false
    )
}