package com.example.careiroapp.products.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.OutlineAppButton

@Composable
fun BuyProductRow(
    isProductFavorite: Boolean?,
    favoriteButtonClick: () -> Unit
) {

    var isFavorite by remember { mutableStateOf(isProductFavorite) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlineAppButton(
            text = stringResource(R.string.adicionar_a_sacola),
            modifier = Modifier,
            onClick = {},
            icon = painterResource(R.drawable.bag)
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlineAppButton(
            text = "",
            modifier = Modifier,
            onClick = {
                isFavorite = !isFavorite!!
                favoriteButtonClick()
            },
            icon = isFavorite?.let { if (!it) painterResource(R.drawable.star) else painterResource(R.drawable.filled_star) }
        )
    }
}

@Composable
@Preview
private fun BuyProductRowPreview() {
    BuyProductRow(
        false,
        {}
    )
}