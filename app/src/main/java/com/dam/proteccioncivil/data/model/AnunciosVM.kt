package com.dam.proteccioncivil.data.model

import androidx.lifecycle.ViewModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class AnunciosVM {

    //val listaAnuncios
    fun printAnuncios() {
        transaction {
            Anuncios.selectAll().forEach{
                println(it[Anuncios.Texto])
            }

//            Anuncios.insert {
//                it[FechaPublicacion] = LocalDateTime.now()
//                it[Texto] = "Prueba"
//
//            }
            //Anuncios.deleteWhere { Anuncios.Texto eq "Prueba" }
        }
    }
}