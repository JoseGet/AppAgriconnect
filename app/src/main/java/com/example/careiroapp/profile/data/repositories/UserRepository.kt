package com.example.careiroapp.profile.data.repositories

import com.example.careiroapp.data.room.dao.UserDao
import com.example.careiroapp.data.room.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    fun getUserData(): Flow<UserEntity?> {
        return userDao.getUser()
    }

    suspend fun saveUserData(
        user: UserEntity
    ) {
        return userDao.saveUser(user)
    }

    suspend fun eraseUserData() {
        return userDao.clearUser()
    }

}