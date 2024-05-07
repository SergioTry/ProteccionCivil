package com.dam.proteccioncivil.data.model

import java.time.LocalDateTime


data class Preventivo(
    val CodPreventivo: Int,
    val Titulo: String,
    val Riesgo: Short,
    val FechaInicio: LocalDateTime,
    val FechaFin: LocalDateTime
)
