package com.dam.proteccioncivil.data.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Anuncios : Table() {
    val CodAnuncio = integer("CodAnuncio").autoIncrement()
    val FechaPublicacion = datetime("FechaPublicacion")
    val Texto = text("Texto")
    override val primaryKey = PrimaryKey(CodAnuncio)
}

@Serializable
data class Anuncio(
    val CodAnuncio: Int,
    val FechaPublicacion: String,
    val Texto: String
)