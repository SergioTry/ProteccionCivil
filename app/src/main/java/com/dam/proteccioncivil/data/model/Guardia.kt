package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
object Guardia : Table() {
    val FechaGuardia = datetime("FechaServicio")
    val CodGuardia = integer("CodServicio").autoIncrement()
    val Descripcion = text("Descripcion")
    val CodUsuario1 = integer("CodUsuario1")
    val CodUsuario2 = integer("CodUsuario2")
    override val primaryKey = PrimaryKey(CodGuardia)
}