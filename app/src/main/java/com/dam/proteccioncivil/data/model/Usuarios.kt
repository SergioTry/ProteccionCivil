package com.dam.proteccioncivil.data.model

import java.time.LocalDateTime


data class Usuario(
    val CodUsuario: Int,
    val DNI: String,
    val Username: String?,
    val Password: String,
    val Nombre: String,
    val Apellidos: String,
    val FechaNacimiento: LocalDateTime,
    val CorreoElectronico: String,
    val Telefono: String?,
    val Rango: String,
    val Conductor: Short
)
