package com.example.careiroapp.loginCadastro.data.repositories

import com.example.careiroapp.loginCadastro.data.datasource.LoginCadastroDataSource
import com.example.careiroapp.loginCadastro.data.dto.ClienteDTO
import com.example.careiroapp.loginCadastro.data.model.LoginRequestModel
import com.example.careiroapp.loginCadastro.data.model.LoginResponseModel
import com.example.careiroapp.loginCadastro.data.model.LogoutRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class LoginCadastroRepository @Inject constructor(
    private val dataSource: LoginCadastroDataSource
) {
    suspend fun createCliente(
        cliente: ClienteDTO, imagePart: MultipartBody.Part?
    ): Response<ClienteDTO> {
        return dataSource.createCliente(cliente, imagePart)
    }

    suspend fun login(
        loginRequestModel: LoginRequestModel
    ): Response<LoginResponseModel> {
        return dataSource.login(loginRequestModel)
    }

    suspend fun logout(
        refreshToken: LogoutRequestModel
    ): Response<Any>  {
        return dataSource.logout(refreshToken)
    }

}