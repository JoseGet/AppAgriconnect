package com.example.careiroapp.loginCadastro.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.example.careiroapp.common.AppAlertDialog
import com.example.careiroapp.loginCadastro.ui.viewmodel.LoginCadastroUiEvents

@Composable
fun UiEventsHandler(
    uiEvent: LoginCadastroUiEvents,
    openAlertDialog: MutableState<Boolean>,
    resetUiEventFunction: () -> Unit
) {
    when(uiEvent) {
        is LoginCadastroUiEvents.InvalidCredentials -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "Credenciais inválidas",
                    dialogText = "O e-mail ou senha informados estão incorretos. Tente novamente.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.NetworkError -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "Problema de conexão",
                    dialogText = "Verifique sua internet e tente novamente.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.ServerError -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "Algo deu errado",
                    dialogText = "Estamos com problemas para realizar o login. Tente novamente em instantes.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.InvalidInput -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "Informações inválidas",
                    dialogText = "Verifique se todos os campos foram preenchidos corretamente.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.EmailAlreadyExist -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "Email já cadastrado",
                    dialogText = "Este email já está em uso. Faça login ou utilize outro email para continuar.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.CpfAlreadyExist -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "CPF já cadastrado",
                    dialogText = "Este CPF já está vinculado a uma conta. Tente fazer login ou utilize outro CPF.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.InvalidCpf -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "CPF inválido",
                    dialogText = "O CPF informado é inválido. Verifique os dados e tente novamente.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.InvalidEmail -> {
            openAlertDialog.value = true
            if (openAlertDialog.value) {
                AppAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        resetUiEventFunction()
                        openAlertDialog.value = false
                    },
                    dialogTitle = "Email inválido",
                    dialogText = "O Email informado é inválido. Verifique os dados e tente novamente.",
                    hadDismissButton = false
                )
            }
        }

        is LoginCadastroUiEvents.RegisterSuccess -> {
            val context = LocalContext.current
            Toast.makeText(
                context,
                "Cadastro realizado com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            resetUiEventFunction()
        }

        is LoginCadastroUiEvents.SessionExpired -> {}

        is LoginCadastroUiEvents.None -> {}
    }
}