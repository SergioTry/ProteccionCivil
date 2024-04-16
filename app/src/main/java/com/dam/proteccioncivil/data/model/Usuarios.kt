package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Usuarios : Table() {
    val CodUsuario = integer("CodUsuario").autoIncrement()
    val DNI = varchar("DNI", length = 20)
    val Username = varchar("Username", length = 50).uniqueIndex().nullable()
    val Password = varchar("Password", length = 50)
    val Nombre = varchar("Nombre", length = 50)
    val Apellidos = varchar("Apellidos", length = 50)
    val FechaNacimiento = datetime("FechaNacimiento")
    val CorreoElectronico = varchar("CorreoElectronico", length = 100).uniqueIndex()
    val Telefono = varchar("Telefono", length = 15).uniqueIndex().nullable()
    val Rango = varchar("Rango", length = 20)
    val Conductor = short("Conductor")

    override val primaryKey = PrimaryKey(DNI)
}
data class Usuario(
    val CodUsuario: Int,
    val DNI: String,
    val Username: String?,
    val Password: String,
    val Nombre: String,
    val Apellidos: String,
    val FechaNacimiento: LocalDateTime,
    val CorreoElectronico: String,
    val Telefono: String?,
    val Rango: String,
    val Conductor: Short
)
