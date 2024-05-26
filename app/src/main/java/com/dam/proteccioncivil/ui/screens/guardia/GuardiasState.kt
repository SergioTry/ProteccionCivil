package com.dam.proteccioncivil.ui.screens.guardia

import com.dam.proteccioncivil.data.model.Guardia

data class GuardiasMtoState(
    val fechaGuardia: String = "",
    val codGuardia: String = "0",
    val descripcion: String = "",
    val codUsuario1: String? = "0",
    val codUsuario2: String? = "0",
    val datosObligatorios: Boolean = false
)

data class GuardiasBusState(
    val showDlgDate: Boolean = false,
    val showDlgConfirmation: Boolean = false)

fun GuardiasMtoState.toGuardia(): Guardia = Guardia(
    codGuardia = if (codGuardia.isEmpty()) 0 else codGuardia.toInt(),
    fechaGuardia = fechaGuardia,
    descripcion = descripcion,
    codUsuario1 = if (codUsuario1.isNullOrEmpty() || codUsuario1 == "null") 0 else codUsuario1.toInt(),
    codUsuario2 = if (codUsuario2.isNullOrEmpty() || codUsuario1 == "null") 0 else codUsuario2.toInt()
)

sealed interface GuardiasUiState {
    data class Success(val guardias: List<Guardia>) : GuardiasUiState
    data class Error(val err: String) : GuardiasUiState
    object Loading : GuardiasUiState
}

sealed interface GuardiasMessageState {
    data object Success : GuardiasMessageState
    data class Error(val err: String) : GuardiasMessageState
    data object Loading : GuardiasMessageState
}
