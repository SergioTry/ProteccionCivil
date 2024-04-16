package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table

object VehiculosPreventivos : Table() {
    val CodVehiculo = integer("CodVehiculo").autoIncrement()
    val CodPreventivo = integer("CodPreventivo").autoIncrement()
}