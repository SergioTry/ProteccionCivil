package com.dam.proteccioncivil.ui.screens.infomur

import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Usuario

data class InfomursMtoState(
    val fechaInfomur: String = "",
    val codInfomur: String = "0",
    val descripcion: String = "",
    val codUsuario1: String = "0",
    val codUsuario2: String = "0",
    val datosObligatorios: Boolean = false
)

data class InfomursBusState(
    val isDetail: Boolean = false,
    val showDlgDate: Boolean = false,
    val showDlgConfirmation: Boolean = false)

fun InfomursMtoState.toInfomur(): Infomur = Infomur(
    codInfomur = if (codInfomur.isEmpty()) 0 else codInfomur.toInt(),
    fechaInfomur = fechaInfomur,
    descripcion = descripcion,
    codUsuario1 = if (codUsuario1.isEmpty()) 0 else codUsuario1.toInt(),
    codUsuario2 = if (codUsuario2.isEmpty()) 0 else codUsuario2.toInt()
)

sealed interface InfomursUiState {
    data class Success(val infomurs: List<Infomur>) : InfomursUiState
    data class Error(val err: String) : InfomursUiState
    object Loading : InfomursUiState
}

sealed interface InfomursMessageState {
    data object Success : InfomursMessageState
    data class Error(val err: String) : InfomursMessageState
    data object Loading : InfomursMessageState
}

data class UsuariosInfomurListState(
    val userList: MutableList<Usuario> = mutableListOf()
)