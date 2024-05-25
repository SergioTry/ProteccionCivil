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
import com.dam.proteccioncivil.data.model.ShortToBoolean
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.UsuariosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import java.io.IOException

class UsuariosVM(private val usuariosRepository: UsuariosRepository) : CRUD<Usuario>, ViewModel() {

    var usuariosUiState: UsuariosUiState by mutableStateOf(UsuariosUiState.Loading)
        private set

    var usuariosMessageState: UsuariosMessageState by mutableStateOf(UsuariosMessageState.Loading)
        private set
    var usuariosMtoState by mutableStateOf(UsuariosMtoState())
        private set

    var usuariosBusState by mutableStateOf(UsuariosBusState())

    fun resetInfoState() {
        usuariosMessageState = UsuariosMessageState.Loading
    }

    fun setShowDlgBorrar(showDlgBorrar: Boolean){
        usuariosBusState = usuariosBusState.copy(
            showDlgConfirmation = showDlgBorrar,
            showDlgDate = usuariosBusState.showDlgDate
        )
    }

    fun setShowDlgDate(showDlgDate: Boolean){
        usuariosBusState = usuariosBusState.copy(
            showDlgConfirmation = usuariosBusState.showDlgConfirmation,
            showDlgDate = showDlgDate
        )
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

//    fun setNewPassword(password: String) {
//        usuarioNewState = usuarioNewState.copy(
//            password = password,
//            contraseñasCorrectass = usuarioNewState.confirmPassword.equals(password)
//        )
//    }
//
//    fun setConfirmPassword(password: String) {
//        usuarioNewState = usuarioNewState.copy(
//            confirmPassword = password,
//            contraseñasCorrectass = usuarioNewState.password.equals(password)
//        )
//    }

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
            "Nuevo",
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
            "",
            usuario.nombre,
            usuario.apellidos,
            usuario.fechaNacimiento,
            usuario.correoElectronico,
            usuario.rango,
            if (usuario.telefono != null) usuario.telefono.toString() else "",
            ShortToBoolean.use(usuario.conductor),
            true
        )
    }

    fun setConductor(conductor: Boolean) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            conductor = conductor,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setPassword(password: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            password = password,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            conductor = usuariosMtoState.conductor,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    password != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setConfirmPassword(password: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            password = usuariosMtoState.password,
            confirmPassword = password,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            conductor = usuariosMtoState.conductor,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    password != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun passwordCorrect(): Boolean {
        return usuariosMtoState.password == usuariosMtoState.confirmPassword
    }

    fun setDni(dni: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    dni != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setUsername(username: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = username,
            nombre = usuariosMtoState.nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setNombre(nombre: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    usuariosMtoState.username != "" &&
                    nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setApellidos(apellidos: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            apellidos = apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setFechaNacimiento(fechaNacimiento: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setCorreoElectronico(correoElectronico: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = usuariosMtoState.telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun setRango(rango: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = rango,
            telefono = usuariosMtoState.telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    rango != "")
        )
    }

    fun setTelefono(telefono: String) {
        usuariosMtoState = usuariosMtoState.copy(
            codUsuario = usuariosMtoState.codUsuario,
            dni = usuariosMtoState.dni,
            username = usuariosMtoState.username,
            nombre = usuariosMtoState.nombre,
            apellidos = usuariosMtoState.apellidos,
            fechaNacimiento = usuariosMtoState.fechaNacimiento,
            correoElectronico = usuariosMtoState.correoElectronico,
            rango = usuariosMtoState.rango,
            telefono = telefono,
            datosObligatorios = (usuariosMtoState.codUsuario != "" &&
                    usuariosMtoState.dni != "" &&
                    usuariosMtoState.username != "" &&
                    usuariosMtoState.nombre != "" &&
                    usuariosMtoState.apellidos != "" &&
                    usuariosMtoState.fechaNacimiento != "" &&
                    usuariosMtoState.correoElectronico != "" &&
                    usuariosMtoState.rango != "")
        )
    }

    fun changePassword() {
        viewModelScope.launch {
            usuariosMessageState =
                UsuariosMessageState.Loading
            usuariosMessageState = try {
                val result = withTimeoutOrNull(timeoutMillis) {
                    usuariosRepository.updateUsuario(
                        Token.codUsuario!!,
                        mapOf("Password" to usuariosMtoState.password)
                    )
                }
                if (result == null) {
                    // Si el tiempo de espera se excede, result será null
                    UsuariosMessageState.Error("Error, no se ha recibido respuesta del servidor", true)
                } else {
                    // Si result no es null, la operación se completó dentro del tiempo de espera
                    UsuariosMessageState.Success
                }
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
            } catch (ex: TimeoutCancellationException) {
                UsuariosMessageState.Error("Error, no se ha recibido respuesta del servidor", true)
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