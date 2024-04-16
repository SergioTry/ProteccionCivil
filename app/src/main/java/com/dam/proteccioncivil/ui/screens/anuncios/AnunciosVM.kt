package com.dam.proteccioncivil.ui.screens.anuncios

import com.dam.proteccioncivil.data.model.Anuncios
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

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