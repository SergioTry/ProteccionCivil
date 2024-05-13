package com.dam.proteccioncivil.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    @SerialName("CodUsuario") val codUsuario: Int,
    @SerialName("DNI") val dni: String,
    @SerialName("Username") val username: String,
    @SerialName("Password") val password: String,
    @SerialName("Nombre") val nombre: String,
    @SerialName("Apellidos") val apellidos: String,
    @SerialName("FechaNacimiento") val fechaNacimiento: String,
    @SerialName("CorreoElectronico") val correoElectronico: String,
    @SerialName("Telefono") val telefono: String?,
    @SerialName("Rango") val rango: String,
    @SerialName("Conductor") val conductor: Short
)
