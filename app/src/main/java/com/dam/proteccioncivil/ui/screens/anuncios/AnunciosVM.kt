package com.dam.proteccioncivil.ui.screens.anuncios

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dam.proteccioncivil.MainApplication
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.Anuncios
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.repository.AnunciosRepository
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import retrofit2.HttpException
import java.io.IOException

sealed interface AnunciosUiState {
    data class Success(val anuncios: List<Anuncio>) : AnunciosUiState
    object Error : AnunciosUiState
    object Loading : AnunciosUiState
}

class AnunciosVM(private val anunciosRepository: AnunciosRepository) : CRUD<Anuncio>, ViewModel() {

    var anunciosUiState: AnunciosUiState by mutableStateOf(AnunciosUiState.Loading)
        private set

    init {
        getAll2()
    }

    //val listaAnuncios
    fun printAnuncios() {
        transaction {
            Anuncios.selectAll().forEach {
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

    override fun getAll(): List<Anuncio> {
        TODO("Not yet implemented")
    }

    fun getAll2() {
        viewModelScope.launch {
            anunciosUiState = AnunciosUiState.Loading
            anunciosUiState = try {
                AnunciosUiState.Success(anunciosRepository.getAnuncios())
            } catch (e: IOException) {
                println(e)
                AnunciosUiState.Error
            } catch (e: HttpException) {
                AnunciosUiState.Error
            }
        }
    }

    override fun getAllBy(fieldname: String, value: String): List<Anuncio> {
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                val anunciosRepository = application.container.anunciosRepository
                AnunciosVM(anunciosRepository = anunciosRepository)
            }
        }
    }
}