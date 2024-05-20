package com.dam.proteccioncivil.data.model

import java.time.LocalDate

data class Servicio(
    val fecha: LocalDate,
    val guardia: Guardia?,
    val infomur: Infomur?,
    val preventivo: Preventivo?
)