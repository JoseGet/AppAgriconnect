package com.example.careiroapp.loginCadastro.ui.components.formatters

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert

class CpfFormatter: OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if (length > 3) insert(3, ".")
        if (length > 7) insert(7, ".")
        if (length > 11) insert(11, "-")
    }
}