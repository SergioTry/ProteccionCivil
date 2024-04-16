package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Preventivos : Table() {
    val CodPreventivo = integer("CodPreventivo").autoIncrement()
    val Titulo = varchar("Titulo", length = 100)
    val Riesgo = short("Riesgo")
    val FechaInicio = datetime("FechaInicio")
    val FechaFin = datetime("FechaFin")

    override val primaryKey = PrimaryKey(CodPreventivo)
}
data class Preventivo(
    val CodPreventivo: Int,
    val Titulo: String,
    val Riesgo: Short,
    val FechaInicio: LocalDateTime,
    val FechaFin: LocalDateTime
)
