package com.example.careiroapp.bag.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun StepLine(
    isActive: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier,
    height: Dp = 1.5.dp
) {
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .height(height)
            .background(
                color = if (isActive) activeColor else inactiveColor
            )
    )
}

