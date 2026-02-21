package com.example.careiroapp.loginCadastro.ui.components.validators

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CpfValidator: ViewModel() {
    var cpf by mutableStateOf("")

    val cpfHasError by derivedStateOf {
        if (cpf.isNotEmpty()) {

        } else {
            false
        }
    }

    fun updateCpf(cpf: String) {
        this.cpf = cpf
    }

}