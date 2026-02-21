package com.example.careiroapp.loginCadastro.domain.usecases

import com.example.careiroapp.loginCadastro.data.model.LogoutRequestModel
import com.example.careiroapp.loginCadastro.data.repositories.LoginCadastroRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: LoginCadastroRepository
) {
    suspend operator fun invoke(
        refreshToken: LogoutRequestModel
    ): Response<Any> {
        return repository.logout(refreshToken)
    }
}