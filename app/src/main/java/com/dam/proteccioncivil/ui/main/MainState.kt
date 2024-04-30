package com.dam.proteccioncivil.ui.main

import com.dam.proteccioncivil.data.model.Preferencias

data class MainState(
    val showDlgSalir: Boolean = false,
    val token: String = "",
    val showPreferences: Boolean = false
)

data class LoginState(
    val username: String = "",
    val password: String = "",
    val datosObligatorios: Boolean = false
)

data class PrefState(
    val token: String = "",
    val defaultTimeSplash: String = "1"
)

fun PrefState.toPreferencias(): Preferencias = Preferencias(
    token = token,
    defaultTimeSplash = defaultTimeSplash.toInt()
)

sealed interface MainInfoState {
    data object Loading : MainInfoState
    data object Success : MainInfoState
    data object Error : MainInfoState
}