package com.example.careiroapp.bag.data.repository

import com.example.careiroapp.data.room.dao.CartDao
import com.example.careiroapp.data.room.entities.BagItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class BagRepository @Inject constructor(
    private val cartDao: CartDao
) {
    val allItems: Flow<List<BagItem>> = cartDao.getAllItems()

    suspend fun addToBag(item: BagItem) {
        if (cartDao.isItemInCart(item.productId)) {
            incrementQuantity(item.productId)
        } else {
            cartDao.insertOrUpdate(item)
        }
    }
    suspend fun removeFromBag(item: BagItem) {
        cartDao.deleteItem(item)
    }

    suspend fun incrementQuantity(itemId: UUID) {
        cartDao.incrementQuantity(itemId)
    }

    suspend fun decreaseQuantity(itemId: UUID) {
        cartDao.decrementQuantity(itemId)
    }

    fun getTotalPrice(): Flow<Double?> {
        return cartDao.getTotalPrice()
    }
}