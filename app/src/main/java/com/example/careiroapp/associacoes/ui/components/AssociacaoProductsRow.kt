package com.example.careiroapp.associacoes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.careiroapp.R
import com.example.careiroapp.associacoes.data.models.AssociacaoProductModel
import com.example.careiroapp.common.components.ModulesHeader
import com.example.careiroapp.products.data.models.ProductModel

@Composable
fun AssociacaoProductsRow(
    productsList: List<AssociacaoProductModel>,
    associacaoName: String?,
    onButtonClick: (product: AssociacaoProductModel) -> Unit
) {
    Column() {
        ModulesHeader(
            titulo = "${stringResource(R.string.confira_produtos_associacao)} $associacaoName",
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.height(380.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(productsList) { item ->
                AssociacaoProductCard(
                    productImage = item.image,
                    productName = item.nome,
                    productPrice = item.preco,
                    onClick = { onButtonClick(item) }
                )
                Spacer(Modifier.height(24.dp))
                AssociacaoProductCard(
                    productImage = item.image,
                    productName = item.nome,
                    productPrice = item.preco,
                    onClick = { onButtonClick(item) }
                )
            }
        }
    }
}