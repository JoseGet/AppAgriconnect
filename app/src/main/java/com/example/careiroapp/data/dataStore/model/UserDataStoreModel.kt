package com.example.careiroapp.data.dataStore.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataStoreModel(
    @SerialName("cpf")
    val cpf: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("telefone")
    val telefone: String = "",
    @SerialName("foto_perfil")
    val fotoPerfil: String = ""
)
