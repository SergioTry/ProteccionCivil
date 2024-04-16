package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Anuncios : Table() {
    val CodAnuncio = integer("CodAnuncio").autoIncrement()
    val FechaPublicacion = datetime("FechaPublicacion")
    val Texto = text("Texto")
    override val primaryKey = PrimaryKey(CodAnuncio)
}

data class Anuncio(
    val CodAnuncio: Int,
    val FechaPublicacion: LocalDateTime,
    val Texto: String
)