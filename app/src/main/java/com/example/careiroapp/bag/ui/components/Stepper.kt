package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.careiroapp.R

@Composable
fun Stepper(
    currentStep: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = colorResource(id = R.color.dark_green),
    inactiveColor: Color = colorResource(id = R.color.inactive_color)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        StepCircle(
            number = 1,
            isActive = currentStep >= 1,
            activeColor = activeColor,
            inactiveColor = inactiveColor
        )

        StepLine(
            isActive = currentStep >= 2,
            activeColor = activeColor,
            inactiveColor = inactiveColor,
            modifier = Modifier.weight(1f)
        )

        StepCircle(
            number = 2,
            isActive = currentStep >= 2,
            activeColor = activeColor,
            inactiveColor = inactiveColor
        )
    }
}
