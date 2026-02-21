package com.example.careiroapp.bag.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.careiroapp.common.montserratBoldFontFamily

@Composable
fun StepCircle(
    number: Int,
    isActive: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    size: Dp = 48.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = if (isActive) activeColor else inactiveColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = montserratBoldFontFamily
        )
    }
}
