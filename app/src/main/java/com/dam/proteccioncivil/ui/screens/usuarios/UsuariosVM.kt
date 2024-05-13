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
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM
import com.dam.proteccioncivil.ui.screens.anuncios.toAnuncio
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

    override fun getAll() {
        TODO("Not yet implemented")
    }

    override fun deleteBy() {
        TODO("Not yet implemented")
    }

    override fun setNew() {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
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
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val usuariosRepository = application.container.usuariosRepository
                UsuariosVM(usuariosRepository = usuariosRepository)
            }
        }
    }
}