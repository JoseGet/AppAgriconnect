package com.example.careiroapp.bag.data.repository

import com.example.careiroapp.data.room.dao.CartDao
import com.example.careiroapp.data.room.entities.BagItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class BagRepository @Inject constructor(
    private val cartDao: CartDao
) {
    fun getAllItems(idUsuario: String): Flow<List<BagItem>> {
        return cartDao.getAllItems(idUsuario)
    }

    suspend fun addToBag(item: BagItem, idUsuario: String) {
        if (cartDao.isItemInCart(item.productId, idUsuario)) {
            incrementQuantity(item.productId, idUsuario)
        } else {
            val itemComUsuario = item.copy(userId = idUsuario)
            cartDao.insertOrUpdate(itemComUsuario)
        }
    }
    suspend fun removeFromBag(item: BagItem) {
        cartDao.deleteItem(item)
    }

    suspend fun incrementQuantity(itemId: UUID, idUsuario: String) {
        cartDao.incrementQuantity(itemId, idUsuario)
    }

    suspend fun decreaseQuantity(itemId: UUID, idUsuario: String) {
        cartDao.decrementQuantity(itemId, idUsuario)
    }

    suspend fun clearBag(idUsuario: String) {
        cartDao.clearBag(idUsuario)
    }

    fun getTotalPrice(idUsuario: String): Flow<Double?> {
        return cartDao.getTotalPrice(idUsuario)
    }
}