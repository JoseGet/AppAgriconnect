package com.example.careiroapp.loginCadastro.domain.usecases

import com.example.careiroapp.loginCadastro.data.repositories.LoginCadastroRepository
import com.example.careiroapp.products.data.models.AddFavoritesReqModel
import retrofit2.Response
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val clienteRepository: LoginCadastroRepository
) {
    suspend operator fun invoke(
        cpf: String,
        productId: AddFavoritesReqModel
    ): Response<Any> {
        return clienteRepository.addToFavorites(
            cpf,
            productId
        )
    }
}