package com.example.careiroapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.careiroapp.data.room.entities.UserEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface UserDao {
    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun clearUser()
}
