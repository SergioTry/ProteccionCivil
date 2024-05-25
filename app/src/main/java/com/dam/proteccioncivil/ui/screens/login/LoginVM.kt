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
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.LoginRepository
import com.dam.proteccioncivil.ui.main.MainVM
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
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
    suspend fun loginAysnc(mainVM: MainVM, saveToken: Boolean = false) {
        uiInfoState = LoginUiState.Loading
        uiInfoState = try {
            val credentials = mapOf(
                "Username" to uiLoginState.username,
                "Password" to uiLoginState.password
            )
            var tokenRecibido: String = ""
            withTimeout(timeoutMillis) {
                tokenRecibido = loginRepository.login(credentials)
            }
            if (!tokenRecibido.equals("")) {
                Log.d("Token: ", tokenRecibido)
                guardarToken(mainVM, tokenRecibido, saveToken, credentials)
                LoginUiState.Success
            } else {
                Log.e("Token: ", "token no recibido")
                LoginUiState.Error("Error, respuesta no esperada del servidor")
            }
        } catch (e: IOException) {
            LoginUiState.Error(e.message.toString())
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
        } catch (ex: TimeoutCancellationException) {
            LoginUiState.Error("Error, no se ha recibido respuesta del servidor")
        }

    }

    fun login(mainVM: MainVM, saveToken: Boolean = false) {
        viewModelScope.launch {
            uiInfoState = LoginUiState.Loading
            uiInfoState = try {
                val credentials = mapOf(
                    "Username" to uiLoginState.username,
                    "Password" to uiLoginState.password
                )
                var tokenRecibido: String = ""
                withTimeout(timeoutMillis) {
                    tokenRecibido = loginRepository.login(credentials)
                }

                if (!tokenRecibido.equals("")) {
                    Log.d("Token: ", tokenRecibido)
                    guardarToken(mainVM, tokenRecibido, saveToken, credentials)
                    LoginUiState.Success
                } else {
                    Log.e("Token: ", "token no recibido")
                    LoginUiState.Error("Error, respuesta no esperada del servidor")
                }
            } catch (e: IOException) {
                LoginUiState.Error(e.message.toString())
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
            } catch (ex: TimeoutCancellationException) {
                LoginUiState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
    }

    private fun guardarToken(
        mainVM: MainVM,
        token: String,
        saveToken: Boolean,
        credentials: Map<String, String>
    ) {
        mainVM.decodificarToken(token)
        Token.password = credentials["Password"]
        if (Token.rango == "Nuevo") {
            mainVM.setShowDlgPassword(true);
        }
        if (saveToken) {
            mainVM.setCredentials(credentials)
            mainVM.savePreferences()
        } else {
            mainVM.resetCredentials()
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