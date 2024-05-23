package com.dam.proteccioncivil.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Permiso(
    @SerialName("Login") val login: Short,
    @SerialName("CodPermisos") val codPermiso: Int,
)
