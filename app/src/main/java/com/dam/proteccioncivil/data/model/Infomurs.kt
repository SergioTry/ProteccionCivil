package com.dam.proteccioncivil.data.model

import java.time.LocalDateTime


data class Infomur(
    val FechaInfomur: LocalDateTime,
    val CodInfomur: Int,
    val Descripcion: String,
    val CodUsuario1: Int,
    val CodUsuario2: Int
)