package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
object Infomur : Table() {
    val FechaInfomur = datetime("FechaServicio")
    val CodInfomur = integer("CodServicio").autoIncrement()
    val Descripcion = text("Descripcion")
    val CodUsuario1 = integer("CodUsuario1")
    val CodUsuario2 = integer("CodUsuario2")
    override val primaryKey = PrimaryKey(CodInfomur)
}