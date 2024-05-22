package com.dam.proteccioncivil.ui.screens.usuarios

import com.dam.proteccioncivil.data.model.Usuario
sealed interface UsuariosUiState {
    data class Success(val usuarios: List<Usuario>) : UsuariosUiState
    data class Error(val err: String) : UsuariosUiState
    object Loading : UsuariosUiState
}

sealed interface UsuariosMessageState {
    data object Success : UsuariosMessageState
    data class Error(val err: String) : UsuariosMessageState
    data object Loading : UsuariosMessageState
}

data class UsuariosMtoState(
    val codUsuario: String = "0",
    val dni: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val fechaNacimiento: String = "",
    val correoElectronico: String = "",
    val rango: String = "Nuevo",
    val telefono: String = "",
    val conductor: Boolean = false,
    val datosObligatorios: Boolean = false,
)

data class NewUsuarioState(
    val password: String = "",
    val confirmPassword: String = "",
    val contraseñasCorrectass: Boolean = false,
)

fun UsuariosMtoState.toUsuario(): Usuario = Usuario(
    codUsuario = if (codUsuario.isEmpty()) 0 else codUsuario.toInt(),
    dni = dni,
    username = username,
    password = password,
    nombre = nombre,
    apellidos = apellidos,
    fechaNacimiento = fechaNacimiento,
    telefono = telefono,
    rango = rango,
    correoElectronico = correoElectronico,
    conductor = if (conductor) 1.toShort() else 0.toShort(),
)