package com.dam.proteccioncivil.data.model

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AnunciosVM {

    //val listaAnuncios
    fun printAnuncios() {
        transaction {
            Anuncio.selectAll().forEach{
                println(it[Anuncio.texto])
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