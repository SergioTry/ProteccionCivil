package com.dam.proteccioncivil.ui.screens.anuncios

import androidx.lifecycle.ViewModel
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.Anuncios
import com.dam.proteccioncivil.data.model.CRUD
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AnunciosVM : CRUD<Anuncios, Anuncio>, ViewModel() {

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

    override fun getAll(): List<Anuncios> {
        TODO("Not yet implemented")
    }

    override fun getAllBy(fieldname: String, value: String): List<Anuncios> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun update(instance: Anuncio, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun setNew(instance: Anuncio) {
        TODO("Not yet implemented")
    }
}