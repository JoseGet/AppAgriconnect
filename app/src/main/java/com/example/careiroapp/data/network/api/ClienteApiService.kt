package com.example.careiroapp.data.network.api

import com.example.careiroapp.loginCadastro.data.dto.ClienteDTO
import com.example.careiroapp.products.data.models.AddFavoritesReqModel
import com.example.careiroapp.products.data.models.ProductModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

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

    @PUT("clientes/{cpf}/favoritos")
    suspend fun addToFavorites(
        @Path("cpf") cpf: String,
        @Body productId: AddFavoritesReqModel
    ): Response<Any>

    @HTTP(method = "DELETE", path = "clientes/{cpf}/favoritos", hasBody = true)
    suspend fun removeFromFavorites(
        @Path("cpf") cpf: String,
        @Body productId: AddFavoritesReqModel
    ): Response<Any>

    @GET("clientes/{cpf}/favoritos")
    suspend fun getFavorites(
        @Path("cpf") cpf: String,
    ): Response<MutableList<ProductModel>>

}