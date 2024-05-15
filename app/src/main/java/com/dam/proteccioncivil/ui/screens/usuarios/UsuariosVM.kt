package com.dam.proteccioncivil.ui.screens.usuarios

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
import com.dam.proteccioncivil.data.model.ObjectToStringMap
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.repository.UsuariosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UsuariosVM(private val usuariosRepository: UsuariosRepository) : CRUD<Usuario>, ViewModel() {

    var usuariosUiState: UsuariosUiState by mutableStateOf(UsuariosUiState.Loading)
        private set

    var usuariosMessageState: UsuariosMessageState by mutableStateOf(UsuariosMessageState.Loading)
        private set

    var usuariosMtoState by mutableStateOf(UsuariosMtoState())
        private set

    var usuarioNewState by mutableStateOf(NewUsuarioState())
        private set

    var showDlgConfirmation = false

    fun resetInfoState() {
        usuariosMessageState = UsuariosMessageState.Loading
    }

    override fun getAll() {
        viewModelScope.launch {
            usuariosUiState = UsuariosUiState.Loading
            usuariosUiState = try {
                val usuarios = usuariosRepository.getUsuarios()
                UsuariosUiState.Success(usuarios)
            } catch (e: IOException) {
                UsuariosUiState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("infomursVM (getAll) ", errorBodyString)
                    UsuariosUiState.Error(error)
                } else {
                    UsuariosUiState.Error("Error")
                }
            }
        }
    }

    override fun deleteBy() {
        viewModelScope.launch {
            usuariosMessageState = UsuariosMessageState.Loading
            usuariosMessageState = try {
                usuariosRepository.deleteUsuario(usuariosMtoState.codUsuario.toInt())
                UsuariosMessageState.Success
            } catch (e: IOException) {
                UsuariosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    UsuariosMessageState.Error(error)
                } else {
                    UsuariosMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                UsuariosMessageState.Success
            }
        }
    }

    override fun update() {
        viewModelScope.launch {
            usuariosMessageState = UsuariosMessageState.Loading
            usuariosMessageState = try {
                usuariosRepository.updateUsuario(
                    usuariosMtoState.codUsuario.toInt(),
                    ObjectToStringMap.use(usuariosMtoState.toUsuario())
                )
                UsuariosMessageState.Success
            } catch (e: IOException) {
                UsuariosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (delete) ", errorBodyString)
                    UsuariosMessageState.Error(error)
                } else {
                    UsuariosMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                UsuariosMessageState.Success
            }
        }
    }

    override fun setNew() {
        viewModelScope.launch {
            usuariosMessageState = UsuariosMessageState.Loading
            usuariosMessageState = try {
                val usuarioMap = ObjectToStringMap.use(usuariosMtoState.toUsuario())
                usuariosRepository.setUsuario(usuarioMap)
                UsuariosMessageState.Success
            } catch (e: IOException) {
                UsuariosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("AnunciosVM (alta) ", errorBodyString)
                    UsuariosMessageState.Error(error)
                } else {
                    UsuariosMessageState.Error("Error")
                }
            }
        }
    }

    fun setNewPassword(password: String) {
        usuarioNewState = usuarioNewState.copy(
            password = password,
            contraseñasCorrectass = usuarioNewState.confirmPassword.equals(password)
        )
    }

    fun setConfirmPassword(password: String) {
        usuarioNewState = usuarioNewState.copy(
            confirmPassword = password,
            contraseñasCorrectass = usuarioNewState.password.equals(password)
        )
    }

    fun resetUsuarioMtoState() {
        usuariosMtoState = usuariosMtoState.copy(
            "0",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            false,
            false
        )
    }

    fun cloneUsuarioMtoState(usuario: Usuario) {
        usuariosMtoState = usuariosMtoState.copy(
            usuario.codUsuario.toString(),
            usuario.dni,
            usuario.username,
            usuario.password,
            usuario.nombre,
            usuario.apellidos,
            usuario.fechaNacimiento,
            usuario.correoElectronico,
            usuario.rango,
            if (usuario.telefono != null) usuario.telefono.toString() else "",
            false,
            true
        )
    }

    fun changePassword() {
        viewModelScope.launch {
            usuariosMessageState =
                UsuariosMessageState.Loading
            usuariosMessageState = try {
                usuariosRepository.updateUsuario(
                    Token.codUsuario!!,
                    mapOf("Password" to usuarioNewState.password)
                )
                UsuariosMessageState.Success
            } catch (e: IOException) {
                UsuariosMessageState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("UsuariosVM (updatePass) ", errorBodyString)
                    UsuariosMessageState.Error(error)
                } else {
                    UsuariosMessageState.Error("Error")
                }
            } catch (e: KotlinNullPointerException) {
                //Esta excepcion se lanza cuando recibimos el 204, ya que este al no tener body provoca
                //error aunque el comportamiento es el que queremos, de ahi que al tratarla se maneje
                //como success
                UsuariosMessageState.Success
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val usuariosRepository = application.container.usuariosRepository
                UsuariosVM(usuariosRepository = usuariosRepository)
            }
        }
    }
}