package com.example.careiroapp.loginCadastro.data.repositories

import com.example.careiroapp.loginCadastro.data.datasource.LoginCadastroDataSource
import com.example.careiroapp.loginCadastro.data.dto.ClienteDTO
import com.example.careiroapp.loginCadastro.data.model.LoginRequestModel
import com.example.careiroapp.loginCadastro.data.model.LoginResponseModel
import com.example.careiroapp.loginCadastro.data.model.LogoutRequestModel
import com.example.careiroapp.products.data.models.AddFavoritesReqModel
import com.example.careiroapp.products.data.models.ProductModel
import okhttp3.MultipartBody
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
    suspend fun addToFavorites(
        cpf: String,
        productId: AddFavoritesReqModel
    ): Response<Any> {
        return dataSource.addToFavorite(cpf, productId)
    }

    suspend fun removeFromFavorites(
        cpf: String,
        productId: AddFavoritesReqModel
    ): Response<Any> {
        return dataSource.removeFromFavorites(cpf, productId)
    }

    suspend fun getFavorites(
        cpf: String
    ): Response<MutableList<ProductModel>> {
        return dataSource.getFavorites(cpf)
    }
    suspend fun logout(
        refreshToken: LogoutRequestModel
    ): Response<Any>  {
        return dataSource.logout(refreshToken)
    }

}