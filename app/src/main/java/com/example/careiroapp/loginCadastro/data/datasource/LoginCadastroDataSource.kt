package com.example.careiroapp.loginCadastro.data.datasource

import com.example.careiroapp.loginCadastro.data.dto.ClienteDTO
import com.example.careiroapp.loginCadastro.data.model.LoginRequestModel
import com.example.careiroapp.loginCadastro.data.model.LoginResponseModel
import com.example.careiroapp.data.network.api.AuthApiService
import com.example.careiroapp.data.network.api.ClienteApiService
import com.example.careiroapp.data.network.api.RefreshApiTokenService
import com.example.careiroapp.loginCadastro.data.model.LogoutRequestModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class LoginCadastroDataSource @Inject constructor(
    private val clienteApiService: ClienteApiService,
    private val authApiService: AuthApiService,
    private val refreshApiTokenService: RefreshApiTokenService
) {
    suspend fun createCliente(
        cliente: ClienteDTO, imagePart: MultipartBody.Part?
    ): Response<ClienteDTO> {

        val nome = cliente.nome.toRequestBody("text/plain".toMediaTypeOrNull())
        val cpf = cliente.cpf.toRequestBody("text/plain".toMediaTypeOrNull())
        val telefone = cliente.telefone.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = cliente.email.toRequestBody("text/plain".toMediaTypeOrNull())
        val senha = cliente.senha.toRequestBody("text/plain".toMediaTypeOrNull())

        return clienteApiService.createCliente(
            nome = nome,
            cpf = cpf,
            telefone = telefone,
            email = email,
            senha = senha,
            foto_perfil = imagePart
        )
    }

    suspend fun login(
        loginRequest: LoginRequestModel
    ): Response<LoginResponseModel> {
        return authApiService.login(loginRequest)
    }

    suspend fun logout(
        refreshToken: LogoutRequestModel
    ): Response<Any> {
        return refreshApiTokenService.logout(refreshToken)
    }

}