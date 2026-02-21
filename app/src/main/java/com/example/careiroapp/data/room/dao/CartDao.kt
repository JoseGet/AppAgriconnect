package com.example.careiroapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.careiroapp.data.room.entities.BagItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CartDao {
    @Query("SELECT * FROM sacola")
    fun getAllItems(): Flow<List<BagItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: BagItem)

    @Delete
    suspend fun deleteItem(item: BagItem)

    @Query("DELETE FROM sacola")
    suspend fun clearCart()

    @Query("UPDATE sacola SET quantity = quantity + 1 WHERE productId = :id")
    suspend fun incrementQuantity(id: UUID)

    @Query("UPDATE sacola SET quantity = MAX(0, quantity - 1) WHERE productId = :id")
    suspend fun decrementQuantity(id: UUID)

    @Query("SELECT EXISTS(SELECT 1 FROM sacola WHERE productId = :id)")
    suspend fun isItemInCart(id: UUID): Boolean

    @Query("SELECT SUM(price * quantity) FROM sacola")
    fun getTotalPrice(): Flow<Double?>
}