package com.example.careiroapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sacola")
data class BagItem(
    @PrimaryKey val productId: UUID,
    val name: String,
    val price: Float,
    val imageUrl: String,
    val quantity: Int = 1
)