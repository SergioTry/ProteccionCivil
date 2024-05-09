package com.dam.proteccioncivil.data.model

import java.time.LocalDate


data class Preventivo(
    val codPreventivo: Int,
    val titulo: String,
    val riesgo: Short,
    val fechaIni: LocalDate,
    val fechaFin: LocalDate,
    val usuarios : List<Usuario>? = null,
    val vehiculos : List<Vehiculo>? = null,
    val dias : List<Dia>
)

data class Dia(
    val dia : LocalDate,
    val hayPreventivo : Boolean
)
