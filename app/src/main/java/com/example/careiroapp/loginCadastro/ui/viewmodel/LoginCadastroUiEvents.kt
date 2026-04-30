package com.example.careiroapp.loginCadastro.ui.viewmodel

sealed class LoginCadastroUiEvents {
    class None(): LoginCadastroUiEvents()
    class InvalidInput(): LoginCadastroUiEvents()
    class InvalidCredentials(): LoginCadastroUiEvents()
    class NetworkError(): LoginCadastroUiEvents()
    class ServerError(): LoginCadastroUiEvents()
    class InvalidCpf(): LoginCadastroUiEvents()
    class InvalidEmail(): LoginCadastroUiEvents()
    class CpfAlreadyExist(): LoginCadastroUiEvents()
    class EmailAlreadyExist(): LoginCadastroUiEvents()
    class RegisterSuccess(): LoginCadastroUiEvents()
    class SessionExpired(): LoginCadastroUiEvents()
}
