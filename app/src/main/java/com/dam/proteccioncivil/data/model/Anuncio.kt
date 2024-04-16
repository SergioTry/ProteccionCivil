package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.w3c.dom.Text

object Anuncios : Table() {
    val CodAnuncio = integer("CodAnuncio").autoIncrement()
    val FechaPublicacion = datetime("FechaPublicacion")
    val Texto = text("texto")
    override val primaryKey = PrimaryKey(CodAnuncio)
}
