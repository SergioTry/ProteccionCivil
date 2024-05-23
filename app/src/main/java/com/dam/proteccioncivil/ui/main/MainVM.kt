package com.dam.proteccioncivil.ui.main

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
import com.dam.proteccioncivil.data.repository.MainRepository
import com.dam.proteccioncivil.ui.screens.login.LoginUiState
import com.dam.proteccioncivil.ui.screens.login.LoginVM
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.JwtConsumerBuilder
import java.io.IOException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class MainVM(private val mainRepository: MainRepository) : ViewModel() {
    var uiMainState by mutableStateOf(MainState())
        private set
    var uiInfoState: MainInfoState by mutableStateOf(MainInfoState.Loading)
        private set
    var uiPrefState by mutableStateOf(PrefState())
        private set

    suspend fun getPreferences(loginVM: LoginVM, showLogin: (Boolean) -> Unit) {
        viewModelScope.async {
            mainRepository.getPreferences().take(1).collect {
                uiPrefState = uiPrefState.copy(
                    username = it.username,
                    password = it.password,
                    defaultTimeSplash = it.defaultTimeSplash.toString()
                )
                if (uiPrefState.username != "" && uiPrefState.password != "") {
                    loginVM.setUsername(uiPrefState.username)
                    loginVM.setPassword(uiPrefState.password)
                    loginVM.loginAysnc(
                        mainVM = this@MainVM,
                        saveToken = true
                    )
                }
                when(loginVM.uiInfoState) {
                    is LoginUiState.Loading -> {
                        showLogin(true)
                    }
                    is LoginUiState.Success -> {
                        loginVM.resetInfoState()
                        loginVM.resetLogin()
                        showLogin(false)
                    }
                    is LoginUiState.Error -> {
                        loginVM.resetInfoState()
                        resetCredentials()
                        showLogin(true)
                    }
                }
            }
        }.await()
    }

    fun savePreferences() {
        try {
            uiPrefState.defaultTimeSplash.toInt()
            viewModelScope.launch {
                uiInfoState = try {
                    mainRepository.savePreferences(uiPrefState.toPreferencias())
                    MainInfoState.Success
                } catch (e: IOException) {
                    MainInfoState.Error
                }
            }
        } catch (e: Exception) {
            uiInfoState = MainInfoState.Error
        }
    }

    fun setCredentials(credentials: Map<String, String>) {
        uiPrefState = uiPrefState.copy(
            username = credentials["Username"]!!,
            password = credentials["Password"]!!
        )
    }

    fun resetCredentials() {
        uiPrefState = uiPrefState.copy(
            username = "",
            password = ""
        )
    }

    fun decodificarToken(token: String) {
        val jwtClaims = decodeJWT(token)
        Token.token = token
        Token.codUsuario = jwtClaims.getClaimValue("CodUsuario").toString().toInt()
        Token.username = jwtClaims.getClaimValue("Username").toString()
        Token.fechaNacimiento =
            parsearFecha(jwtClaims.getClaimValue("FechaNacimiento").toString())
        Token.rango = jwtClaims.getClaimValue("Rango").toString()
        Token.conductor = jwtClaims.getClaimValue("Conductor").toString().toInt()
    }


    private fun decodeJWT(jwt: String): JwtClaims {
        val jwtConsumer = JwtConsumerBuilder()
            .setSkipAllValidators()
            .setDisableRequireSignature()
            .setSkipSignatureVerification()
            .build()

        return jwtConsumer.processToClaims(jwt)
    }

    private fun parsearFecha(fecha: String): LocalDateTime {
        val instant = Instant.parse(fecha)
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    fun setShowDlgRecursos(mostrar: Boolean) {
        uiMainState = uiMainState.copy(
            showDlgRecursos = mostrar,
        )
    }

    fun setShowDlgServicios(mostrar: Boolean) {
        uiMainState = uiMainState.copy(
            showDlgServicios = mostrar,
        )
    }

    fun setShowDlgSalir(mostrar: Boolean) {
        uiMainState = uiMainState.copy(
            showDlgSalir = mostrar,
        )
    }

    fun setShowDlgPassword(mostrar: Boolean) {
        uiMainState = uiMainState.copy(
            showDlgPassword = mostrar,
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val mainRepository = application.container.mainRepository
                MainVM(mainRepository = mainRepository)
            }
        }
    }
}