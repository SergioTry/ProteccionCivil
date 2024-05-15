package com.dam.proteccioncivil.data.model

import kotlinx.serialization.Serializable
@Serializable
data class Permiso(
    val nombre: String,
    val nivel: Int
)
