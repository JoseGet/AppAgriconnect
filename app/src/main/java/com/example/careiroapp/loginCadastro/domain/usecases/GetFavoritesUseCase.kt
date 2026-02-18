package com.example.careiroapp.loginCadastro.domain.usecases

import com.example.careiroapp.loginCadastro.data.repositories.LoginCadastroRepository
import com.example.careiroapp.products.data.models.ProductModel
import retrofit2.Response
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val clienteRepository: LoginCadastroRepository
) {
    suspend operator fun invoke(
        cpf: String
    ): Response<MutableList<ProductModel>> {
        return clienteRepository.getFavorites(
            cpf
        )
    }
}