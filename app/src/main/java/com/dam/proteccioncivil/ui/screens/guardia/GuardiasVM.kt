package com.dam.proteccioncivil.ui.screens.guardia

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dam.proteccioncivil.MainApplication
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Guardia
import com.dam.proteccioncivil.data.model.ObjectToStringMap
import com.dam.proteccioncivil.data.repository.GuardiasRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface GuardiasUiState {
    data class Success(val guardias: List<Guardia>) : GuardiasUiState
    data class Error(val err: String) : GuardiasUiState
    object Loading : GuardiasUiState
}

sealed interface GuardiasMessageState {
    data object Success : GuardiasMessageState
    data class Error(val err: String) : GuardiasMessageState
    data object Loading : GuardiasMessageState
}

class GuardiasVM(private val guardiasRepository: GuardiasRepository) : CRUD<Guardia>, ViewModel() {

    var guardiasUiState: GuardiasUiState by mutableStateOf(GuardiasUiState.Loading)
        private set

    var guardiasMessageState: GuardiasMessageState by mutableStateOf(GuardiasMessageState.Loading)
        private set

    var guardiasMtoState by mutableStateOf(GuardiasMtoState())
        private set

    var showDlgConfirmation = false

    fun resetInfoState() {
        guardiasMessageState = GuardiasMessageState.Loading
    }

    override fun getAll() {
        viewModelScope.launch {
            guardiasUiState = GuardiasUiState.Loading
            guardiasUiState = try {
                val guardias = guardiasRepository.getGuardias()
                GuardiasUiState.Success(guardias)
            } catch (e: IOException) {
                GuardiasUiState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("GuardiasVM (getAll) ", errorBodyString)
                    GuardiasUiState.Error(error)
                } else {
                    GuardiasUiState.Error("Error")
                }
            }
        }
    }

    override fun deleteBy() {
        viewModelScope.launch {
            guardiasMessageState = GuardiasMessageState.Loading
            guardiasMessageState = try {
                guardiasRepository.deleteGuardia(guardiasMtoState.codGuardia.toInt())
                GuardiasMessageState.Success
            } catch (e: IOException) {
                GuardiasMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    GuardiasMessageState.Error(error)
                } else {
                    GuardiasMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                GuardiasMessageState.Success
            }
        }
    }

    override fun update() {
        viewModelScope.launch {
            guardiasMessageState = GuardiasMessageState.Loading
            guardiasMessageState = try {
                guardiasRepository.updateGuardia(
                    guardiasMtoState.codGuardia.toInt(),
                    ObjectToStringMap.use(guardiasMtoState.toGuardia())
                )
                GuardiasMessageState.Success
            } catch (e: IOException) {
                GuardiasMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    GuardiasMessageState.Error(error)
                } else {
                    GuardiasMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                GuardiasMessageState.Success
            }
        }
    }

    override fun setNew() {
        viewModelScope.launch {
            guardiasMessageState = GuardiasMessageState.Loading
            guardiasMessageState = try {
                val guardiaMap = ObjectToStringMap.use(guardiasMtoState.toGuardia())
                guardiasRepository.setGuardia(guardiaMap)
                GuardiasMessageState.Success
            } catch (e: IOException) {
                GuardiasMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (alta) ", errorBodyString)
                    GuardiasMessageState.Error(error)
                } else {
                    GuardiasMessageState.Error("Error")
                }
            }
        }
    }

    fun resetGuardiaMtoState() {
        guardiasMtoState = guardiasMtoState.copy(
            "",
            "0",
            "",
            "0",
            "0",
            false
        )
    }

    fun cloneGuardiaMtoState(guardia: Guardia) {
        guardiasMtoState = guardiasMtoState.copy(
            guardia.fechaGuardia,
            guardia.codGuardia.toString(),
            guardia.descripcion,
            guardia.codUsuario1.toString(),
            guardia.codUsuario2.toString(),
            true
        )
    }

    fun setDescripcion(descripcion: String) {
        guardiasMtoState = guardiasMtoState.copy(
            codGuardia = guardiasMtoState.codGuardia,
            fechaGuardia = guardiasMtoState.fechaGuardia,
            descripcion = descripcion,
            codUsuario1 = guardiasMtoState.codUsuario1,
            codUsuario2 = guardiasMtoState.codUsuario2,
            datosObligatorios = (guardiasMtoState.codGuardia != "" &&
                    guardiasMtoState.fechaGuardia != "" &&
                    descripcion != "" &&
                    guardiasMtoState.codUsuario1 != "" &&
                    guardiasMtoState.codUsuario2 != "")
        )
    }

    fun setFechaGuardia(fecha: String) {
        guardiasMtoState = guardiasMtoState.copy(
            codGuardia = guardiasMtoState.codGuardia,
            fechaGuardia = fecha,
            descripcion = guardiasMtoState.descripcion,
            codUsuario1 = guardiasMtoState.codUsuario1,
            codUsuario2 = guardiasMtoState.codUsuario2,
            datosObligatorios = (guardiasMtoState.codGuardia != "" &&
                    fecha != "" &&
                    guardiasMtoState.descripcion != "" &&
                    guardiasMtoState.codUsuario1 != "" &&
                    guardiasMtoState.codUsuario2 != "")
        )
    }

    fun setUsuarios(codUsuario1: String?, codUsuario2: String?) {
        guardiasMtoState = guardiasMtoState.copy(
            codGuardia = guardiasMtoState.codGuardia,
            fechaGuardia = guardiasMtoState.fechaGuardia,
            descripcion = guardiasMtoState.descripcion,
            codUsuario1 = codUsuario1,
            codUsuario2 = codUsuario2,
            datosObligatorios = (guardiasMtoState.codGuardia != "" &&
                    guardiasMtoState.fechaGuardia != "" &&
                    guardiasMtoState.descripcion != "" &&
                    codUsuario1 != "" &&
                    codUsuario2 != "")
        )
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val guardiasRepository = application.container.guardiasRepository
                GuardiasVM(guardiasRepository = guardiasRepository)
            }
        }
    }
}