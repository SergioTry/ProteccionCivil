package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Anuncios : Table() {
    val CodAnuncio = integer("CodAnuncio").autoIncrement()
    val FechaPublicacion = datetime("FechaPublicacion")
    val Texto = text("Texto")
    override val primaryKey = PrimaryKey(CodAnuncio)
}