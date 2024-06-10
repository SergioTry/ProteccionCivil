package com.dam.proteccioncivil.ui.screens.infomur

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
import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.ObjectToStringMap
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.InfomursRepository
import com.dam.proteccioncivil.data.repository.UsuariosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException


@SuppressLint("MutableCollectionMutableState")
class InfomursVM(
    private val infomursRepository: InfomursRepository,
    private val usuarioRepository: UsuariosRepository,
) : CRUD<Infomur>, ViewModel() {

    var infomursUiState: InfomursUiState by mutableStateOf(InfomursUiState.Loading)
        private set

    var infomursMessageState: InfomursMessageState by mutableStateOf(InfomursMessageState.Loading)
        private set

    var infomursBusState by mutableStateOf(InfomursBusState())

    var infomursMtoState by mutableStateOf(InfomursMtoState())
        private set

    // Estas variables se usan para saber si ha habido cambios en el MtoState
    var originalInfomursMtoState = infomursMtoState.copy()

    fun updateOriginalState() {
        originalInfomursMtoState = infomursMtoState.copy()
    }

    // Esta funcionalidad no rompe durante la alta porque la clave primaria de
    // todas las tablas no está presente en la creación (siempre es 0),
    // por lo que siempre es diferente.
    fun hasStateChanged(): Boolean {
        val current = infomursMtoState.copy(datosObligatorios = false)
        val original = originalInfomursMtoState.copy(datosObligatorios = false)
        return current != original
    }

    fun setIsDetail(isDetail: Boolean) {
        infomursBusState = infomursBusState.copy(
            isDetail = isDetail,
            showDlgConfirmation = infomursBusState.showDlgConfirmation,
            showDlgDate = infomursBusState.showDlgDate
        )
    }

    fun setShowDlgBorrar(showDlgBorrar: Boolean) {
        infomursBusState = infomursBusState.copy(
            showDlgConfirmation = showDlgBorrar,
            showDlgDate = infomursBusState.showDlgDate
        )
    }

    fun setShowDlgDate(showDlgDate: Boolean) {
        infomursBusState = infomursBusState.copy(
            showDlgConfirmation = infomursBusState.showDlgConfirmation,
            showDlgDate = showDlgDate
        )
    }

    var users by mutableStateOf(UsuariosInfomurListState().userList)

    fun resetInfoState() {
        infomursMessageState = InfomursMessageState.Loading
    }

    override fun getAll() {
        viewModelScope.launch {
            infomursUiState = InfomursUiState.Loading
            infomursUiState = try {
                // En futuras versiones se controlará que no se pueda alcanzar
                // este código con el rango "nuevo".
                if (Token.rango == "Voluntario" || Token.rango == "Nuevo") {
                    var infomurs: List<Infomur>?
                    withTimeout(timeoutMillis) {
                        infomurs = infomursRepository.getInfomurs()
                    }
                    if (infomurs != null) {
                        InfomursUiState.Success(infomurs!!)
                    } else {
                        InfomursUiState.Error("Error, no se ha recibido respuesta del servidor")
                    }
                } else {
                    var infomurs: List<Infomur>?
                    withTimeout(timeoutMillis) {
                        users.addAll(usuarioRepository.getUsuarios())
                        infomurs = infomursRepository.getInfomurs()
                    }
                    if (infomurs != null) {
                        InfomursUiState.Success(infomurs!!)
                    } else {
                        InfomursUiState.Error("Error, no se ha recibido respuesta del servidor")
                    }
                }
            } catch (e: IOException) {
                InfomursUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("InfomursVM (getAll) ", errorBodyString)
                    InfomursUiState.Error(error)
                } else {
                    InfomursUiState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                InfomursUiState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun deleteBy() {
        viewModelScope.launch {
            infomursMessageState = InfomursMessageState.Loading
            infomursMessageState = try {
                withTimeout(timeoutMillis) {
                    infomursRepository.deleteInfomur(infomursMtoState.codInfomur.toInt())
                }
                InfomursMessageState.Success
            } catch (e: IOException) {
                InfomursMessageState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("InfomursVM (delete) ", errorBodyString)
                    InfomursMessageState.Error(error)
                } else {
                    InfomursMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                InfomursMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                InfomursMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }


    override fun update() {
        viewModelScope.launch {
            infomursMessageState = InfomursMessageState.Loading
            infomursMessageState = try {
                withTimeout(timeoutMillis) {
                    infomursRepository.updateInfomur(
                        infomursMtoState.codInfomur.toInt(),
                        ObjectToStringMap.use(infomursMtoState.toInfomur())
                    )
                }
                InfomursMessageState.Success
            } catch (e: IOException) {
                InfomursMessageState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("InfomursVM (delete) ", errorBodyString)
                    InfomursMessageState.Error(error)
                } else {
                    InfomursMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                InfomursMessageState.Success
            } catch (ex: TimeoutCancellationException) {
                InfomursMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    override fun setNew() {
        viewModelScope.launch {
            infomursMessageState = InfomursMessageState.Loading
            infomursMessageState = try {
                val infomurMap = ObjectToStringMap.use(infomursMtoState.toInfomur())
                withTimeout(timeoutMillis) {
                    infomursRepository.setInfomur(infomurMap)
                }
                InfomursMessageState.Success
            } catch (e: IOException) {
                InfomursMessageState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("InfomursVM (alta) ", errorBodyString)
                    InfomursMessageState.Error(error)
                } else {
                    InfomursMessageState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                InfomursMessageState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }


    fun resetInfomurMtoState() {
        infomursMtoState = infomursMtoState.copy(
            "",
            "0",
            "",
            "0",
            "0",
            false
        )
    }

    fun cloneInfomurMtoState(infomur: Infomur) {
        infomursMtoState = infomursMtoState.copy(
            infomur.fechaInfomur,
            infomur.codInfomur.toString(),
            infomur.descripcion,
            infomur.codUsuario1.toString(),
            infomur.codUsuario2.toString(),
            true
        )
    }

    fun setDescripcion(descripcion: String) {
        infomursMtoState = infomursMtoState.copy(
            codInfomur = infomursMtoState.codInfomur,
            fechaInfomur = infomursMtoState.fechaInfomur,
            descripcion = descripcion,
            codUsuario1 = infomursMtoState.codUsuario1,
            codUsuario2 = infomursMtoState.codUsuario2,
            datosObligatorios = (infomursMtoState.codInfomur != "" &&
                    infomursMtoState.fechaInfomur != "" &&
                    descripcion != "" &&
                    infomursMtoState.codUsuario1 != "" &&
                    infomursMtoState.codUsuario2 != "")
        )
    }

    fun setFechaInfomur(fecha: String) {
        infomursMtoState = infomursMtoState.copy(
            codInfomur = infomursMtoState.codInfomur,
            fechaInfomur = fecha,
            descripcion = infomursMtoState.descripcion,
            codUsuario1 = infomursMtoState.codUsuario1,
            codUsuario2 = infomursMtoState.codUsuario2,
            datosObligatorios = (infomursMtoState.codInfomur != "" &&
                    fecha != "" &&
                    infomursMtoState.descripcion != "" &&
                    infomursMtoState.codUsuario1 != "" &&
                    infomursMtoState.codUsuario2 != "")
        )
    }

    fun setUsuarios(codUsuario1: String, codUsuario2: String) {
        infomursMtoState = infomursMtoState.copy(
            codInfomur = infomursMtoState.codInfomur,
            fechaInfomur = infomursMtoState.fechaInfomur,
            descripcion = infomursMtoState.descripcion,
            codUsuario1 = codUsuario1,
            codUsuario2 = codUsuario2,
            datosObligatorios = (infomursMtoState.codInfomur != "" &&
                    infomursMtoState.fechaInfomur != "" &&
                    infomursMtoState.descripcion != "" &&
                    codUsuario1 != "" &&
                    codUsuario2 != "")
        )
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val infomursRepository = application.container.infomursRepository
                val usuarioRepository = application.container.usuariosRepository
                InfomursVM(
                    infomursRepository = infomursRepository,
                    usuarioRepository = usuarioRepository
                )
            }
        }
    }
}