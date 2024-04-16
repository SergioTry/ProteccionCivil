package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Vehiculos : Table() {
    val CodVehiculo = integer("Km")
    val Matricula = varchar("Matricula", length = 20)
    val Marca = varchar("Marca", length = 45)
    val Modelo = varchar("Modelo", length = 50)
    val Km = float("Km")
    val Disponible = short("Disponible")
    val FechaMantenimiento = datetime("FechaMantenimiento")
    val Descripcion = text("Descripcion")

    override val primaryKey = PrimaryKey(Matricula)
}

data class Vehiculo(
    val CodVehiculo: Int,
    val Matricula: String,
    val Marca: String,
    val Modelo: String,
    val Km: Float,
    val Disponible: Short,
    val FechaMantenimiento: LocalDateTime,
    val Descripcion: String
)
