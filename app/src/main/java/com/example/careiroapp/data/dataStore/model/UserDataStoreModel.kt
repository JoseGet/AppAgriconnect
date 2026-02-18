package com.example.careiroapp.data.dataStore.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDataStoreModel(
    val cpf: String = "",
    val name: String = "",
    val email: String = "",
    val telefone: String = "",
    val fotoPerfil: String = ""
)
