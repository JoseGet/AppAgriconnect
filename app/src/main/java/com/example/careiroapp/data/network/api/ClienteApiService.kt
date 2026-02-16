package com.example.careiroapp.data.network.api

import com.example.careiroapp.loginCadastro.data.dto.ClienteDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface ClienteApiService {

    @Multipart
    @POST("clientes/")
    suspend fun createCliente(
        @Part("nome") nome: RequestBody,
        @Part("cpf") cpf: RequestBody,
        @Part("email") email: RequestBody,
        @Part("telefone") telefone: RequestBody,
        @Part("senha") senha: RequestBody,
        @Part foto_perfil: MultipartBody.Part?
    ): Response<ClienteDTO>

}