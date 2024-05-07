package com.dam.proteccioncivil.ui.screens.login

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
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.repository.LoginRepository
import com.dam.proteccioncivil.ui.main.MainVM
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class LoginVM(
    private val loginRepository: LoginRepository
) : ViewModel() {
    var uiInfoState: LoginUiState by mutableStateOf(LoginUiState.Loading)
        private set

    var uiLoginState by mutableStateOf(LoginState())
        private set

    // Ese método recibirá el mainVM en caso de que
    // el usuario quiera activar el autoinicio de sesión.
    fun login(mainVM: MainVM, saveToken: Boolean = false) {
        viewModelScope.launch {
            uiInfoState = LoginUiState.Loading
            uiInfoState = try {
                val credentials = mapOf(
                    "Username" to uiLoginState.username,
                    "Password" to uiLoginState.password
                )
                val tokenRecibido = loginRepository.login(credentials)
                if (!tokenRecibido.equals("")) {
                    Log.d("Token: ", tokenRecibido)
                    guardarToken(mainVM, tokenRecibido, saveToken)
                    LoginUiState.Success
                } else {
                    Log.e("Token: ", "token no recibido")
                    LoginUiState.Error("Vuelva a intentarlo")
                }
            } catch (e: IOException) {
                LoginUiState.Error("e1")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("LoginVM (login) ", errorBodyString)
                    LoginUiState.Error(error)
                } else {
                    LoginUiState.Error("Error")
                }
            }
        }
    }

    private fun guardarToken(mainVM: MainVM, token: String, saveToken: Boolean) {
        mainVM.decodificarToken(token)
        if (saveToken) {
            mainVM.setToken(token)
            mainVM.savePreferences()
        } else {
            mainVM.resetToken()
            mainVM.savePreferences()
        }
    }

    fun setUsername(username: String) {
        uiLoginState = uiLoginState.copy(
            username = username,
            datosObligatorios = (uiLoginState.password.isNotBlank() && uiLoginState.password.isNotEmpty())
        )
    }

    fun setPassword(password: String) {
        uiLoginState = uiLoginState.copy(
            password = password,
            datosObligatorios = (uiLoginState.username.isNotBlank() && uiLoginState.username.isNotEmpty())
        )
    }

    fun resetLogin() {
        uiLoginState = uiLoginState.copy(
            username = "",
            password = "",
            datosObligatorios = false
        )
    }

    fun resetInfoState() {
        uiInfoState = LoginUiState.Loading
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val loginRepository = application.container.loginRepository
                LoginVM(loginRepository = loginRepository)
            }
        }
    }
}