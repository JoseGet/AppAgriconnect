package com.example.careiroapp.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.careiroapp.data.dataStore.model.UserDataStoreModel
import com.example.careiroapp.data.dataStore.util.UserDataSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<UserDataStoreModel> by dataStore(
    fileName = "userData.json",
    serializer = UserDataSerializer,
)

class UserDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val userIdFlow: Flow<String?> = context.dataStore.data
        .map { userData ->
            userData.cpf.ifBlank { null }
        }
        .distinctUntilChanged()

    fun getUserData(): Flow<UserDataStoreModel> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(UserDataStoreModel())
        } else {
            throw exception
        }
    }

    suspend fun saveUserData(newUserData: UserDataStoreModel?) {
        context.dataStore.updateData { userData ->
            userData.copy(
                cpf = newUserData?.cpf ?: "",
                name = newUserData?.name ?: "",
                email = newUserData?.email ?: "",
                telefone = newUserData?.telefone ?: "",
                fotoPerfil = newUserData?.fotoPerfil ?: ""
            )
        }
    }

    suspend fun clearData() {
        context.dataStore.updateData { UserDataStoreModel() }
    }

}