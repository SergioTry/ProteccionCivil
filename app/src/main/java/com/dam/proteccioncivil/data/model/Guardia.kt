package com.dam.proteccioncivil.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Guardia(
    @SerialName("FechaGuardia") val fechaGuardia: String,
    @SerialName("CodGuardia") val codGuardia: Int,
    @SerialName("Descripcion") val descripcion: String,
    @SerialName("CodUsuario1") val codUsuario1: Int?,
    @SerialName("CodUsuario2") val codUsuario2: Int?
)
