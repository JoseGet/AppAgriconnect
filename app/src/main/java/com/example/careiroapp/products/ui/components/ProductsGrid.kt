package com.example.careiroapp.products.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.careiroapp.products.data.models.ProductModel
import java.util.UUID

@Composable
fun ProductsGrid(
    modifier: Modifier = Modifier,
    gridListState: LazyGridState,
    list: List<ProductModel>,
    onItemClicker: (UUID) -> Unit,
    loadMore: () -> Unit,
    addToBag: (ProductModel) -> Unit
) {

    val buffer = 5

    val finalOfList: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = gridListState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == gridListState.layoutInfo.totalItemsCount - buffer
        }
    }

    LaunchedEffect(finalOfList) {
        if (finalOfList) loadMore()
    }

    LazyVerticalGrid(
        state = gridListState,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list) { item ->
            ProductCard(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                image = item.image,
                nomeProduto = item.nomeProduto,
                precoProduto = item.precoProduto,
                isPromocao = item.isPromocao,
                precoPromocao = item.precoPromocao,
                haveButton = true,
                onClick = {
                    onItemClicker(item.id)
                },
                onButtonClick = {
                    addToBag(item)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AppGridPreview() {
    ProductsGrid(
        gridListState = rememberLazyGridState(),
        modifier = Modifier,
        list = emptyList(),
        onItemClicker = {},
        loadMore = {},
        addToBag = {}
    )
}