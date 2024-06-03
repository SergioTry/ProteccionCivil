package com.dam.proteccioncivil.ui.main

import com.dam.proteccioncivil.data.model.Preferencias

data class MainState(
    val showDlgSalir: Boolean = false,
    val showDlgRecursos: Boolean = false,
    val showDlgServicios: Boolean = false,
    val showDlgPassword: Boolean = false,
    val showPreferences: Boolean = false
)

data class PasswordState(
    val UIpassword: String = "",
)

data class PrefState(
    val username: String = "",
    val password: String = "",
    val iv: String = ""
)

fun PrefState.toPreferencias(): Preferencias = Preferencias(
    username = username,
    password = password,
    iv = iv
)

sealed interface MainInfoState {
    data object Loading : MainInfoState
    data object Success : MainInfoState
    data object Error : MainInfoState
}