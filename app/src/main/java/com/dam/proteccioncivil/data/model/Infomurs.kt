package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Infomurs : Table() {
    val FechaInfomur = datetime("FechaServicio")
    val CodInfomur = integer("CodServicio").autoIncrement()
    val Descripcion = text("Descripcion")
    val CodUsuario1 = integer("CodUsuario1")
    val CodUsuario2 = integer("CodUsuario2")
    override val primaryKey = PrimaryKey(CodInfomur)
}

data class Infomur(
    val FechaInfomur: LocalDateTime,
    val CodInfomur: Int,
    val Descripcion: String,
    val CodUsuario1: Int,
    val CodUsuario2: Int
)