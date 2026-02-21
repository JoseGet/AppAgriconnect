package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.careiroapp.R
import com.example.careiroapp.common.montserratRegularFontFamily

@Composable
fun PaymentRadioButton(
    title: String,
    description: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    ListItem(
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
        ),
        headlineContent = {
            Text(
                title,
                fontFamily = montserratRegularFontFamily,
                fontSize = 16.sp,
                color = colorResource(R.color.dark_gray)
            )
        },
        supportingContent = {
            Text(
                description,
                fontFamily = montserratRegularFontFamily,
                fontSize = 12.sp,
                color = colorResource(R.color.dark_gray)
            )
        },
        leadingContent = {
            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = colorResource(R.color.dark_green),
                    unselectedColor = colorResource(R.color.dark_green)
                )
            )
        },
        modifier = Modifier
            .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onSelect()
        }
    )
}

@Preview
@Composable
private fun PaymentRadioButtonPreview() {
    PaymentRadioButton(
        title = stringResource(R.string.pix),
        description = stringResource(R.string.pix_description),
        isSelected = true,
        onSelect = {}
    )
}