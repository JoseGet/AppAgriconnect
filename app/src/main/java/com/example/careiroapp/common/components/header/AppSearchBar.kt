package com.example.careiroapp.common.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.careiroapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar() {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(CircleShape)
            .background(Color.White)
            .height(40.dp)
            .fillMaxWidth()
            .border(0.5.dp, colorResource(R.color.search_bar_border_color), RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            BasicTextField(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                value = text,
                onValueChange = {
                    text = it
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                "O que você procura?",
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                },
            )

            Icon(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(R.drawable.lupa),
                contentDescription = null,
                tint = colorResource(R.color.dark_green)
            )
        }
    }
}

@Preview
@Composable
fun AppSearchBarPreview() {
    AppSearchBar()
}
