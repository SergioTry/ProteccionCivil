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
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.AnunciosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException


class AnunciosVM(private val anunciosRepository: AnunciosRepository) : CRUD<Anuncio>, ViewModel() {

    var anunciosUiState: AnunciosUiState by mutableStateOf(AnunciosUiState.Loading)
        private set

    var anunciosMessageState: AnunciosMessageState by mutableStateOf(AnunciosMessageState.Loading)
        private set

    var anunciosMtoState by mutableStateOf(AnunciosMtoState())
        private set

    var anunciosBusState by mutableStateOf(AnunciosBusState())

    fun resetInfoState() {
        anunciosMessageState = AnunciosMessageState.Loading
    }

    fun setLoading(loading: Boolean){
        anunciosBusState = anunciosBusState.copy(
            loading = loading,
            showDlgBorrar = anunciosBusState.showDlgBorrar,
            showDlgDate = anunciosBusState.showDlgDate
        )
    }

    fun setShowDlgBorrar(showDlgBorrar: Boolean){
        anunciosBusState = anunciosBusState.copy(
            loading = anunciosBusState.loading,
            showDlgBorrar = showDlgBorrar,
            showDlgDate = anunciosBusState.showDlgDate
        )
    }

    fun setShowDlgDate(showDlgDate: Boolean){
        anunciosBusState = anunciosBusState.copy(
            loading = anunciosBusState.loading,
            showDlgBorrar = anunciosBusState.showDlgBorrar,
            showDlgDate = showDlgDate
        )
    }

    override fun getAll() {
        viewModelScope.launch {
            anunciosUiState = AnunciosUiState.Loading
            anunciosUiState = try {
                var anuncios: List<Anuncio>?
                withTimeout(timeoutMillis) {
                    anuncios = anunciosRepository.getAnuncios()
                }
                if (anuncios != null) {
                    AnunciosUiState.Success(anuncios!!)
                } else {
                    AnunciosUiState.Error("Error, no se ha recibido respuesta del servidor")
                }
            } catch (e: IOException) {
                AnunciosUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (get) ", errorBodyString)
                    AnunciosUiState.Error(error)
                } else {
                    AnunciosUiState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                AnunciosUiState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun deleteBy() {
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                withTimeout(timeoutMillis) {
                    anunciosRepository.deleteAnuncio(anunciosMtoState.codAnuncio.toInt())
                }
                AnunciosMessageState.Success
            } catch (e: IOException) {
                AnunciosMessageState.Error(e.message.toString())
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
            } catch (ex: TimeoutCancellationException) {
                AnunciosMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun update() {
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                withTimeout(timeoutMillis) {
                    anunciosRepository.updateAnuncio(
                        anunciosMtoState.codAnuncio.toInt(),
                        ObjectToStringMap.use(anunciosMtoState.toAnuncio())
                    )
                }
                AnunciosMessageState.Success
            } catch (e: IOException) {
                AnunciosMessageState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (update) ", errorBodyString)
                    AnunciosMessageState.Error(error)
                } else {
                    AnunciosMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                AnunciosMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                AnunciosMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun setNew() {
        viewModelScope.launch {
            anunciosMessageState = AnunciosMessageState.Loading
            anunciosMessageState = try {
                val anuncioMap = ObjectToStringMap.use(anunciosMtoState.toAnuncio())
                withTimeout(timeoutMillis) {
                    anunciosRepository.setAnuncio(anuncioMap)
                }
                AnunciosMessageState.Success
            } catch (e: IOException) {
                AnunciosMessageState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (set) ", errorBodyString)
                    AnunciosMessageState.Error(error)
                } else {
                    AnunciosMessageState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                AnunciosMessageState.Error("Error, no se ha recibido respuesta del servidor")
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