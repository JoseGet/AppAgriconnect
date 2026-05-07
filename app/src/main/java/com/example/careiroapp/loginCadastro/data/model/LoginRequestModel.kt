package com.example.careiroapp.loginCadastro.data.model

data class LoginRequestModel(
    val email: String,
    val senha: String,
    val fcmToken: String?
)