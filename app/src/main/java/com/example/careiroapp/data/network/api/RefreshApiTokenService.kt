package com.example.careiroapp.data.network.api

import com.example.careiroapp.data.model.RefreshTokenResponseModel
import com.example.careiroapp.loginCadastro.data.model.LogoutRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApiTokenService {

    @POST("refresh/token")
    suspend fun refreshToken(
        @Body refreshToken: LogoutRequestModel,
    ): Response<RefreshTokenResponseModel>

    @POST("refresh/logout")
    suspend fun logout(
        @Body refreshToken: LogoutRequestModel,
    ): Response<Any>
}