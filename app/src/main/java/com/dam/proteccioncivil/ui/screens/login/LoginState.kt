package com.dam.proteccioncivil.ui.screens.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val datosObligatorios: Boolean = false
)

sealed interface LoginUiState {
    object Success : LoginUiState
    data class Error(val err: String) : LoginUiState
    object Loading : LoginUiState
}