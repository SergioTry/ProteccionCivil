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
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.repository.LoginRepository
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosUiState
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface LoginUiState {
    object Success : LoginUiState
    data class Error(val err: String): LoginUiState
    object Loading : LoginUiState
}
var tokenRecibido: String = ""

class LoginVM(private val loginRepository: LoginRepository) : ViewModel() {
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Loading)
        private set

    init{
        login()
    }

    fun login() {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val credentials = mapOf(
                    "Username" to "Juan",
                    "Password" to "admin2"
                )
                tokenRecibido = loginRepository.login(credentials)
                if (!tokenRecibido.equals("")) {
                    Log.e("Token: ",tokenRecibido)
                    LoginUiState.Success
                } else {
                    Log.e("Token: ","token no recibido")
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val loginRepository = application.container.loginRepository
                LoginVM(loginRepository = loginRepository)
            }
        }
    }
}