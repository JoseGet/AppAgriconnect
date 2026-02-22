package com.example.careiroapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.careiroapp.data.room.dao.CartDao
import com.example.careiroapp.data.room.dao.UserDao
import com.example.careiroapp.data.room.entities.BagItem
import com.example.careiroapp.data.room.entities.UserEntity

@Database(entities = [BagItem::class, UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userDao(): UserDao
}