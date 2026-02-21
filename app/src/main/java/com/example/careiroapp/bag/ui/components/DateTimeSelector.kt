package com.example.careiroapp.bag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.careiroapp.models.DateTimeOptionModel

@Composable
fun DateTimeSelector(
    options: List<DateTimeOptionModel>,
    selectedId: String?,
    onSelect: (DateTimeOptionModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 96.dp),
        modifier = Modifier.height(150.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        items(options) { option ->
            DateTimeChip(
                date = option.date,
                time = option.time,
                selected = option.id == selectedId,
                onClick = { onSelect(option) }
            )
        }
    }
}
