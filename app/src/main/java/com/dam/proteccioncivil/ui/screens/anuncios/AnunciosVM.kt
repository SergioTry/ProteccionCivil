package com.dam.proteccioncivil.ui.screens.anuncios

import android.util.Log
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
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.repository.AnunciosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AnunciosUiState {
    data class Success(val anuncios: List<Anuncio>) : AnunciosUiState
    data class Error(val err: String) : AnunciosUiState
    object Loading : AnunciosUiState
}

sealed interface AnunciosMessageState {
    data object Success : AnunciosMessageState
    data class Error(val err: String) : AnunciosMessageState
    data object Loading : AnunciosMessageState
}

class AnunciosVM(private val anunciosRepository: AnunciosRepository) : CRUD<Anuncio>, ViewModel() {

    var anunciosUiState: AnunciosUiState by mutableStateOf(AnunciosUiState.Loading)
        private set

    var anunciosMessageState: AnunciosMessageState by mutableStateOf(AnunciosMessageState.Loading)
        private set

    var uiAnuncioState by mutableStateOf(AnunciosMtoState())
        private set


    override fun getAll(): List<Anuncio> {
        TODO("Not yet implemented")
    }

    fun getAll2() {
        viewModelScope.launch {
            anunciosUiState = AnunciosUiState.Loading
            anunciosUiState = try {
                val anuncios = anunciosRepository.getAnuncios()
                AnunciosUiState.Success(anuncios)
            } catch (e: IOException) {
                AnunciosUiState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (getAll2) ", errorBodyString)
                    AnunciosUiState.Error(error)
                } else {
                    AnunciosUiState.Error("Error")
                }
            }
        }
    }


    override fun getAllBy(fieldname: String, value: String): List<Anuncio> {
        TODO("Not yet implemented")
    }

    override fun deleteBy(fieldname: String, value: String) {
        //TODO poner un pop up de confirmación
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                anunciosRepository.deleteAnuncio(value.toInt())
                AnunciosMessageState.Success
            } catch (e: IOException) {
                AnunciosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    AnunciosMessageState.Error(error)
                } else {
                    AnunciosMessageState.Error("Error")
                }
            }
        }
    }

    override fun update(instance: Anuncio, fieldname: String, value: String) {
        TODO("Not yet implemented")
    }

    fun probarAlta() {
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                uiAnuncioState = uiAnuncioState.copy(
                    "3","02/02/2022","Prueba android",true
                )
                val anuncioMap = objectToStringMap(uiAnuncioState.toAnuncio())
                anunciosRepository.setAnuncio(anuncioMap)
                AnunciosMessageState.Success
            } catch (e: IOException) {
                AnunciosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (alta) ", errorBodyString)
                    AnunciosMessageState.Error(error)
                } else {
                    AnunciosMessageState.Error("Error")
                }
            }
        }
    }


    override fun setNew(instance: Anuncio) {

    }

    // Esto si funciona hay que sacarlo y hacerlo para que se
    // pueda usar desde todas las clases
    fun objectToStringMap(obj: Any): Map<String, String> {
        val map = mutableMapOf<String, String>()

        val declaredFields = obj.javaClass.declaredFields.filter { !it.name.startsWith('$') }

        for (field in declaredFields) {
            field.isAccessible = true
            val value = field.get(obj)

            when (value) {
                is String -> map[field.name] = value
                is Int -> map[field.name] = value.toString()
                else -> throw IllegalArgumentException("Unsupported field type: ${field.type}")
            }
        }

        return map
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