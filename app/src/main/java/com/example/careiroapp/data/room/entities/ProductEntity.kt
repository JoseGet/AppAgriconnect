package com.example.careiroapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sacola", primaryKeys = ["productId", "userId"])
data class BagItem(
    val productId: UUID,
    val userId: String,
    val name: String,
    val price: Float,
    val imageUrl: String,
    val quantity: Int = 1
)