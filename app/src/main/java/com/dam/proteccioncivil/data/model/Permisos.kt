package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table

object Permisos : Table() {
    val CodUsuario = integer("CodUsuario").autoIncrement()
    val CodPermisos = integer("CodPermisos").autoIncrement()
    val Login = short("Login")
}