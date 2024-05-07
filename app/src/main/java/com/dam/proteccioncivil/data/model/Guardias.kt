package com.dam.proteccioncivil.data.model

import java.time.LocalDateTime


data class Guardia(
    val FechaGuardia: LocalDateTime,
    val CodGuardia: Int,
    val Descripcion: String,
    val CodUsuario1: Int,
    val CodUsuario2: Int
)