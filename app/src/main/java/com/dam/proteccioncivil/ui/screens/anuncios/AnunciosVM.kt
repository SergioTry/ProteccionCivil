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
import com.dam.proteccioncivil.data.model.ObjectToStringMap
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

    var anunciosMtoState by mutableStateOf(AnunciosMtoState())
        private set

    var showDlgConfirmation = false

    fun resetInfoState() {
        anunciosMessageState = AnunciosMessageState.Loading
    }

    override fun getAll() {
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
                    Log.e("AnunciosVM (getAll) ", errorBodyString)
                    AnunciosUiState.Error(error)
                } else {
                    AnunciosUiState.Error("Error")
                }
            }
        }
    }

    override fun deleteBy() {
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                anunciosRepository.deleteAnuncio(anunciosMtoState.codAnuncio.toInt())
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
            } catch (e: KotlinNullPointerException) {
                AnunciosMessageState.Success
            }
        }
    }

    override fun update() {
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                anunciosRepository.updateAnuncio(
                    anunciosMtoState.codAnuncio.toInt(),
                    ObjectToStringMap.use(anunciosMtoState.toAnuncio())
                )
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
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                AnunciosMessageState.Success
            }
        }
    }

    override fun setNew() {
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                val anuncioMap = ObjectToStringMap.use(anunciosMtoState.toAnuncio())
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

    fun resetAnuncioMtoState() {
        anunciosMtoState = anunciosMtoState.copy(
            "0",
            "",
            "",
            false
        )
    }

    fun cloneAnuncioMtoState(anuncio: Anuncio) {
        anunciosMtoState = anunciosMtoState.copy(
            anuncio.codAnuncio.toString(),
            anuncio.fechaPublicacion,
            anuncio.texto,
            true
        )
    }

    fun setTexto(texto: String) {
        anunciosMtoState = anunciosMtoState.copy(
            texto = texto,
            datosObligatorios = (texto != "" && anunciosMtoState.fechaPublicacion != "")
        )
    }

    fun setFechaPublicacion(fecha: String) {
        anunciosMtoState = anunciosMtoState.copy(
            fechaPublicacion = fecha,
            datosObligatorios = (fecha != "" && anunciosMtoState.texto != "")
        )
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