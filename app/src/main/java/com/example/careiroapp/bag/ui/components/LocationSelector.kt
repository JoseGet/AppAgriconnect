package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.careiroapp.models.LocationOptionModel


@Composable
fun LocationSelector(
    locations: List<LocationOptionModel>,
    selectedId: String?,
    onSelect: (LocationOptionModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 96.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(locations) { location ->
            SelectableChip(
                text = location.label,
                selected = location.id == selectedId,
                onClick = { onSelect(location) }
            )
        }
    }
}
