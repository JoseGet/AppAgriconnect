package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.careiroapp.R
import com.example.careiroapp.common.components.buttons.AppButton
import com.example.careiroapp.common.components.buttons.OutlineAppButton

@Composable
fun FinalStepButtonsRow(
    onClickLeftButton: () -> Unit,
    onClickRightButton: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        OutlineAppButton(
            text = "Ir para o inicio",
            modifier = Modifier
                .weight(1f),
            onClick = onClickLeftButton,
            icon = null,
            isActivate = false
        )
        AppButton(
            text = "Ver pedido",
            modifier = Modifier
                .weight(1f),
            onClick = onClickRightButton,
            icon = null,
            containerColor = colorResource(R.color.dark_green)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun FinalStepButtonsRowPreview() {
    FinalStepButtonsRow(
        {},
        {}
    )
}