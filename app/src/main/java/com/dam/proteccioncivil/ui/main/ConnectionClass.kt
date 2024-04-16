package com.dam.proteccioncivil.ui.main

import org.jetbrains.exposed.sql.Database
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


data class ConnectionClass(
    val user: String = "admin",
    val password: String = "admin",
    val db: String = "ProteccionCivil",
    val ip: String = "34.175.113.180",
    val port: String = "3306"
) {
    fun getConexion(): Database? {
        var db: Database? = null;
        try {
            db = Database.connect("jdbc:mysql://34.175.113.180:3306/ProteccionCivil", driver = "com.mysql.jdbc.Driver",
                user = "admin", password = "admin")
//            val url = "jdbc:mysql://${ip}:${port}/${db}"
//            con = DriverManager.getConnection(url, user, password)
//            con.autoCommit = false
        } catch (ex: SQLException) {
            println("Error:" + ex)
        }
        return db
    }
}