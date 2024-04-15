package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table;
import org.jetbrains.exposed.sql.Column;
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Anuncio : Table() {
    val fecha: Column<LocalDate> = date("fecha")
    val id: Column<Int> = integer("ID").autoIncrement().primaryKey()
    val texto: Column<String> = text("texto")
}