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
    @Query("SELECT * FROM sacola WHERE userId = :userId")
    fun getAllItems(userId: String): Flow<List<BagItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: BagItem)

    @Delete
    suspend fun deleteItem(item: BagItem)

    @Query("DELETE FROM sacola WHERE userId = :userId")
    suspend fun clearBag(userId: String)

    @Query("UPDATE sacola SET quantity = quantity + 1 WHERE productId = :id AND userId = :userId")
    suspend fun incrementQuantity(id: UUID, userId: String)

    @Query("UPDATE sacola SET quantity = MAX(0, quantity - 1) WHERE productId = :id AND userId = :userId")
    suspend fun decrementQuantity(id: UUID, userId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM sacola WHERE productId = :id AND userId = :userId)")
    suspend fun isItemInCart(id: UUID, userId: String): Boolean

    @Query("SELECT SUM(price * quantity) FROM sacola WHERE userId = :userId")
    fun getTotalPrice(userId: String): Flow<Double?>
}