package com.example.careiroapp.loginCadastro.ui.components.formatters

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert

class PhoneNumberFormatter: OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if (length > 0) insert(0, "(")
        if (length > 3) insert(3, ") ")
        if (length > 10) insert(10, "-")
    }
}