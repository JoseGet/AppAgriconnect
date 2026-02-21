package com.example.careiroapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.careiroapp.data.room.dao.CartDao
import com.example.careiroapp.data.room.entities.BagItem

@Database(entities = [BagItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}