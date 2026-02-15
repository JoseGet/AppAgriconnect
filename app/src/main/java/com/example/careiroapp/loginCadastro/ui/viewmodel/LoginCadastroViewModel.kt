package com.example.careiroapp.loginCadastro.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careiroapp.data.dataStore.UserDataStore
import com.example.careiroapp.data.dataStore.model.UserDataStoreModel
import com.example.careiroapp.loginCadastro.data.dto.ClienteDTO
import com.example.careiroapp.loginCadastro.data.model.LoginRequestModel
import com.example.careiroapp.loginCadastro.domain.usecases.LoginUseCase
import com.example.careiroapp.loginCadastro.domain.usecases.RegisterUseCase
import com.example.careiroapp.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class LoginCadastroViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val userDataStore: UserDataStore
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginCadastroUiState())
    var uiState: StateFlow<LoginCadastroUiState> = _uiState.asStateFlow()

    private val _startDestination = mutableStateOf<String?>(null)
    val startDestination = _startDestination

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            userDataStore.getUserData().collect { user ->
                _startDestination.value = if (user.token.isNotEmpty()) {
                    NavigationItem.Main.route
                } else {
                    NavigationItem.Login.route
                }
            }
        }
    }

    fun changeCardState(newCardState: CardState) {

        val newCardState = newCardState

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    cardState = newCardState
                )
            }
        }
    }

    fun makeCadastro(
        context: Context,
        nome: String,
        cpf: String,
        telefone: String,
        email: String,
        senha: String,
        uriImage: String?
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val clienteDTO = ClienteDTO(
                nome,
                cpf,
                telefone,
                email,
                senha,
                ""
            )

            val imagePart = if (uriImage != null) prepararImagemPart(context, uriImage) else null
            val response = registerUseCase.invoke(clienteDTO, imagePart)

            _uiState.update { it.copy(isLoading = false) }

            if (response.isSuccessful) {
                changeCardState(CardState.LOGIN)
            }
        }
    }

    private fun prepararImagemPart(context: Context, uriString: String?): MultipartBody.Part? {

        if (uriString.isNullOrEmpty() || uriString == "null") {
            return null
        }

        val uri = uriString.toUri()
        val contentResolver = context.contentResolver

        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return null

        val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("foto_perfil", "perfil.jpg", requestFile)
    }

    fun login(
        email: String,
        senha: String,
        goToMainView: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val loginRequest = LoginRequestModel(
                email = email,
                senha = senha
            )

            try {
                val loginResponse = loginUseCase.invoke(loginRequest)
                if (loginResponse.isSuccessful) {

                    val data = loginResponse.body()?.let {
                        UserDataStoreModel(
                            token = it.token,
                            name = it.cliente.nome,
                            email = it.cliente.email,
                            telefone = it.cliente.telefone,
                            fotoPerfil = it.cliente.fotoPerfil ?: ""
                        )
                    }
                    userDataStore.saveUserData(data)
                    goToMainView()
                }
            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        val TAG = LoginCadastroViewModel.Companion::class.java.name
    }

}