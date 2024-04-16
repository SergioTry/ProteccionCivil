package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Publicaciones : Table() {
    val CodPreventivo = integer("CodPreventivo").autoIncrement()
    val Titulo = varchar("Titulo", length = 100)
    val Riesgo = short("Riesgo")
    val FechaInicio = datetime("FechaInicio")
    val FechaFin = datetime("FechaFin")

    override val primaryKey = PrimaryKey(CodPreventivo)
}