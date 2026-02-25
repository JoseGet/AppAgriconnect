package com.example.careiroapp.loginCadastro.ui.viewmodel

sealed class LoginErrorUiEvents {
    class CpfNaoFornecido(): LoginErrorUiEvents()
    class InvalidCpf(): LoginErrorUiEvents()
    class None(): LoginErrorUiEvents()
}