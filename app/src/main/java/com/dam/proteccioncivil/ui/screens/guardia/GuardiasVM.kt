package com.dam.proteccioncivil.ui.screens.guardia

import android.annotation.SuppressLint
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
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.GuardiasRepository
import com.dam.proteccioncivil.data.repository.UsuariosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

data class UsuariosGuardiaListState(
    val userList: MutableList<Usuario> = mutableListOf()
)

@SuppressLint("MutableCollectionMutableState")
class GuardiasVM(
    private val guardiasRepository: GuardiasRepository,
    private val usuariosRepository: UsuariosRepository
) : CRUD<Guardia>, ViewModel() {

    var guardiasUiState: GuardiasUiState by mutableStateOf(GuardiasUiState.Loading)
        private set

    var guardiasMessageState: GuardiasMessageState by mutableStateOf(GuardiasMessageState.Loading)
        private set

    var guardiasBusState by mutableStateOf(GuardiasBusState())

    var users by mutableStateOf(UsuariosGuardiaListState().userList)

    var guardiasMtoState by mutableStateOf(GuardiasMtoState())
        private set

    // Estas variables se usan para saber si ha habido cambios en el MtoState
    var originalGuardiasMtoState = guardiasMtoState.copy()

    fun updateOriginalState() {
        originalGuardiasMtoState = guardiasMtoState.copy()
    }

    fun hasStateChanged(): Boolean {
        val current = guardiasMtoState.copy(datosObligatorios = false)
        val original = originalGuardiasMtoState.copy(datosObligatorios = false)
        return current != original
    }

    fun setIsDetail(isDetail: Boolean) {
        guardiasBusState = guardiasBusState.copy(
            isDetail = isDetail,
            showDlgConfirmation = guardiasBusState.showDlgConfirmation,
            showDlgDate = guardiasBusState.showDlgDate
        )
    }

    fun setShowDlgBorrar(showDlgBorrar: Boolean) {
        guardiasBusState = guardiasBusState.copy(
            showDlgConfirmation = showDlgBorrar,
            showDlgDate = guardiasBusState.showDlgDate
        )
    }

    fun setShowDlgDate(showDlgDate: Boolean) {
        guardiasBusState = guardiasBusState.copy(
            showDlgConfirmation = guardiasBusState.showDlgConfirmation,
            showDlgDate = showDlgDate
        )
    }

    fun resetInfoState() {
        guardiasMessageState = GuardiasMessageState.Loading
    }

    override fun getAll() {
        viewModelScope.launch {
            guardiasUiState = GuardiasUiState.Loading
            guardiasUiState = try {
                if(Token.rango == "Voluntario"){
                    var guardias: List<Guardia>?
                    withTimeout(timeoutMillis) {
                        guardias = guardiasRepository.getGuardias()
                    }
                    if (guardias != null) {
                        GuardiasUiState.Success(guardias!!)
                    } else {
                        GuardiasUiState.Error("Error, no se ha recibido respuesta del servidor")
                    }
                }else{
                    var guardias: List<Guardia>?
                    var usuarios: List<Usuario>?
                    withTimeout(timeoutMillis) {
                        usuarios = usuariosRepository.getUsuarios()
                        guardias = guardiasRepository.getGuardias()
                    }
                    if (guardias != null && usuarios!= null) {
                        users.addAll(usuarios!!)
                        GuardiasUiState.Success(guardias!!)
                    } else {
                        GuardiasUiState.Error("Error, no se ha recibido respuesta del servidor")
                    }
                }
            } catch (e: IOException) {
                GuardiasUiState.Error(e.message.toString())
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
            } catch (ex: TimeoutCancellationException) {
                GuardiasUiState.Error("Error, no se ha recibido respuesta del servidor")
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
            } catch (ex: TimeoutCancellationException) {
                GuardiasMessageState.Error("Error, no se ha recibido respuesta del servidor")
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
                GuardiasMessageState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("GuardiasVM (delete) ", errorBodyString)
                    GuardiasMessageState.Error(error)
                } else {
                    GuardiasMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                GuardiasMessageState.Success
            }catch (ex: TimeoutCancellationException) {
                GuardiasMessageState.Error("Error, no se ha recibido respuesta del servidor")
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
                GuardiasMessageState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("GuardiasVM (alta) ", errorBodyString)
                    GuardiasMessageState.Error(error)
                } else {
                    GuardiasMessageState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                GuardiasMessageState.Error("Error, no se ha recibido respuesta del servidor")
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
                val usuariosRepository = application.container.usuariosRepository
                GuardiasVM(guardiasRepository = guardiasRepository,usuariosRepository=usuariosRepository)
            }
        }
    }
}