package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.Table

object UsuariosPreventivos : Table() {
    val CodUsuario = integer("CodUsuario").autoIncrement()
    val CodPreventivo = integer("CodPreventivo").autoIncrement()
}

data class UsuarioPreventivo(
    val CodUsuario: Int,
    val CodPreventivo: Int
)
