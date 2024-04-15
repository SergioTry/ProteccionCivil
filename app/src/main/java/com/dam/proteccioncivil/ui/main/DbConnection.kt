package com.dam.proteccioncivil.ui.main

import java.sql.*
import java.util.ArrayList

class DbConnection {
    private val conexion: Connection
    private val psGetAnuncios: PreparedStatement

    init {
        try {
            conexion = DriverManager.getConnection(
                "jdbc:mysql://34.175.113.180:3306/ProteccionCivil",
                "admin",
                "admin"
            )
            conexion.autoCommit = false

            psGetAnuncios = conexion.prepareStatement("SELECT * FROM Anuncios")

            println("Conectado a ${conexion.metaData.databaseProductName} version ${conexion.metaData.databaseMajorVersion}")
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    fun getAnuncios(): List<String> {
        return try {
            val listaAnuncios = ArrayList<String>()
            var rs: ResultSet? = null
            rs = psGetAnuncios.executeQuery()

            while (rs.next()) {
                listaAnuncios.add(
                    String.format(
                        "CodAnuncio %s, fecha: %s, texto: %s",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                    )
                )
            }
            listaAnuncios
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }
}