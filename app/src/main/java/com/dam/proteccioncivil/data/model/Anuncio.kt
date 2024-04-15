package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Anuncio : Table() {
    val fecha: Column<LocalDate> = date("fecha")
    val texto: Column<String> = text("texto")
    override val primaryKey = PrimaryKey(fecha, name = "ID")
}
