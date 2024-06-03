package com.dam.proteccioncivil.ui.main

import android.util.Base64
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
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosVM
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

    var passwordState by mutableStateOf(PasswordState())
        private set

    suspend fun getPreferences(loginVM: LoginVM, showLogin: (Boolean) -> Unit,usuariosVM: UsuariosVM) {
        viewModelScope.async {
            mainRepository.getPreferences().take(1).collect {
                uiPrefState = uiPrefState.copy(
                    username = it.username,
                    password = it.password,
                    iv = it.iv
                )
                if (uiPrefState.username != "" && uiPrefState.password != "" && uiPrefState.iv != "") {
                    KeystoreHelper.generateKey()
                    val key = KeystoreHelper.getKey()
                    loginVM.setUsername(uiPrefState.username)
                    loginVM.setPassword(
                        KeystoreHelper.desencriptar(
                            datosEncriptados = uiPrefState.password,
                            secretKey = key,
                            iv = Base64.decode(uiPrefState.iv, Base64.DEFAULT)
                        )
                    )
                    loginVM.loginAysnc(
                        mainVM = this@MainVM,
                        saveToken = true
                    )
                }
                when (loginVM.uiInfoState) {
                    is LoginUiState.Loading -> {
                        showLogin(true)
                    }

                    is LoginUiState.Success -> {
                        loginVM.resetInfoState()
                        loginVM.resetLogin()
                        showLogin(false)
                    }

                    is LoginUiState.Error -> {
                        showLogin(true)
                    }
                }
            }
        }.await()
    }

    fun savePreferences() {
        try {
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

    fun setPasswordForUi(password: String) {
        passwordState = passwordState.copy(
            UIpassword = password
        )
    }

    fun setCredentials(credentials: Map<String, String>) {
        KeystoreHelper.generateKey()
        val key = KeystoreHelper.getKey()
        // El método encriptar devuelve un Pair
        val encryptedPassword = KeystoreHelper.encriptar(credentials["Password"]!!, key)
        uiPrefState = uiPrefState.copy(
            username = credentials["Username"]!!,
            password = encryptedPassword.first,
            iv = Base64.encodeToString(encryptedPassword.second, Base64.DEFAULT)
        )

    }

    fun resetCredentials() {
        uiPrefState = uiPrefState.copy(
            username = "",
            password = "",
            iv = ""
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