package com.example.careiroapp.bag.data.repository

import com.example.careiroapp.data.room.dao.CartDao
import com.example.careiroapp.data.room.entities.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    val allItems: Flow<List<CartItem>> = cartDao.getAllItems()

    suspend fun addToCart(item: CartItem) {
        cartDao.insertOrUpdate(item)
    }

    suspend fun removeItem(item: CartItem) {
        cartDao.deleteItem(item)
    }
}