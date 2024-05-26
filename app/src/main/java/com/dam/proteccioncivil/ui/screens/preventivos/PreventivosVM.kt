package com.dam.proteccioncivil.ui.screens.preventivos

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
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.model.Vehiculo
import com.dam.proteccioncivil.data.model.timeoutMillis
import com.dam.proteccioncivil.data.repository.PreventivosRepository
import com.dam.proteccioncivil.data.repository.UsuariosRepository
import com.dam.proteccioncivil.data.repository.VehiculosRepository
import com.google.gson.JsonParser
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

class PreventivosVM(
    private val preventivoRepository: PreventivosRepository,
    private val usuariosRepository: UsuariosRepository,
    private val vehiculosRepository: VehiculosRepository
) : CRUD<Preventivo>, ViewModel() {
    var uiPreventivoState: StateFlow<PrevState> = MutableStateFlow(PrevState())
        private set

    var preventivosUiState: PreventivosUiState by mutableStateOf(PreventivosUiState.Loading)
        private set

    var preventivosMessageState: PreventivosMessageState by mutableStateOf(PreventivosMessageState.Loading)
        private set

    // var selectedOption by mutableStateOf("")

//    fun obtenerPreventivos() {
//        viewModelScope.launch {
//            uiPreventivoState = preventivoRepository.getAllPreventivos()
//                .map { PrevState(it) }
//                .stateIn(
//                    scope = viewModelScope,
//                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                    initialValue = PrevState()
//                )
//        }
//    }

    fun filtrarPreventivos() {
//        viewModelScope.launch {
//            val filtro = uiPreventivoState.value.uiFiltroState
//            if (filtro.codPreventivo.isEmpty() && filtro.fechaIni == null && filtro.fechaFin == null) {
//                obtenerPreventivos()
//            } else {
//                uiPreventivoState = preventivoRepository.getAllPreventivosByFiltro(
//                    filtro.codPreventivo,
//                    filtro.fechaIni,
//                    filtro.fechaFin
//                )
//                    .map { PreventivoState(it) }
//                    .stateIn(
//                        scope = viewModelScope,
//                        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                        initialValue = PreventivoState()
//                    )
//            }
//        }
    }

//    fun setFiltroPreventivo(codPreventivo: String, fechaIni: LocalDate?, fechaFin: LocalDate?) {
//    }

    fun resetFiltroPreventivo() {
        //obtenerPreventivos()
    }

    fun setPreventivoSelected(pos: Int) {
        val selectedPreventivo = uiPreventivoState.value.preventivos[pos]
    }

    fun resetPreventivoState() {
    }

//    fun altaPreventivo(preventivo: Preventivo) {
//        viewModelScope.launch {
//            try {
//                preventivoRepository.insertPreventivo(preventivo)
//            } catch (e: Exception) {
//            }
//        }
//    }
//
//    fun editarPreventivo(preventivo: Preventivo) {
//        viewModelScope.launch {
//            try {
//                preventivoRepository.updatePreventivo(preventivo)
//            } catch (e: Exception) {
//            }
//        }
//    }
//
//    fun eliminarPreventivo(preventivo: Preventivo) {
//        viewModelScope.launch {
//            try {
//                preventivoRepository.deletePreventivo(preventivo)
//            } catch (e: Exception) {
//            }
//        }
//    }

//    fun asignarPersona(usuario: Usuario, posPreventivo: Int) {
//        val preventivo = uiPreventivoState.value.preventivos[posPreventivo]
//        val nuevaListaUsuarios = preventivo.usuarios?.toMutableList() ?: mutableListOf()
//        nuevaListaUsuarios.add(usuario)
//        val nuevoPreventivo = preventivo.copy(usuarios = nuevaListaUsuarios)
//        editarPreventivo(nuevoPreventivo)
//    }
//
//    fun desasignarPersona(usuario: Usuario, posPreventivo: Int) {
//        val preventivo = uiPreventivoState.value.preventivos[posPreventivo]
//        val nuevaListaUsuarios = preventivo.usuarios?.toMutableList() ?: mutableListOf()
//        nuevaListaUsuarios.remove(usuario)
//        val nuevoPreventivo = preventivo.copy(usuarios = nuevaListaUsuarios)
//        editarPreventivo(nuevoPreventivo)
//    }
//
//    fun asignarVehiculo(vehiculo: Vehiculo, posPreventivo: Int) {
//        val preventivo = uiPreventivoState.value.preventivos[posPreventivo]
//        val nuevaListaVehiculos = preventivo.vehiculos?.toMutableList() ?: mutableListOf()
//        nuevaListaVehiculos.add(vehiculo)
//        val nuevoPreventivo = preventivo.copy(vehiculos = nuevaListaVehiculos)
//        editarPreventivo(nuevoPreventivo)
//    }
//
//    fun desasignarVehiculo(vehiculo: Vehiculo, posPreventivo: Int) {
//        val preventivo = uiPreventivoState.value.preventivos[posPreventivo]
//        val nuevaListaVehiculos = preventivo.vehiculos?.toMutableList() ?: mutableListOf()
//        nuevaListaVehiculos.remove(vehiculo)
//        val nuevoPreventivo = preventivo.copy(vehiculos = nuevaListaVehiculos)
//        editarPreventivo(nuevoPreventivo)
//    }

    override fun getAll() {
        viewModelScope.launch {
            preventivosUiState = PreventivosUiState.Loading
            preventivosUiState = try {
                var preventivos: List<Preventivo>?
                withTimeout(timeoutMillis * 2) {
                    preventivos = preventivoRepository.getPreventivosUsuario(Token.codUsuario!!)
                    if (preventivos!!.isNotEmpty()) {
                        preventivos!!.forEach {
                            val usuarios: List<Usuario>?
                            val vehiculos: List<Vehiculo>?
                            usuarios = usuariosRepository.getUsuariosPreventivo(it.codPreventivo)
                            vehiculos = vehiculosRepository.getVehiculosPreventivo(it.codPreventivo)
                            it.usuarios = usuarios
                            it.vehiculos = vehiculos
                        }
                    }
                }
                if (preventivos != null) {
                    PreventivosUiState.Success(preventivos = preventivos!!)
                } else {
                    PreventivosUiState.Error("Error, no se ha recibido respuesta del servidor")
                }
            } catch (e: IOException) {
                PreventivosUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()
                val errorBodyString = errorBody?.string()
                if (errorBodyString != null) {
                    val jsonObject = JsonParser.parseString(errorBodyString).asJsonObject
                    val error = jsonObject["body"]?.asString ?: ""
                    Log.e("PreventivosVM (get) ", errorBodyString)
                    PreventivosUiState.Error(error)
                } else {
                    PreventivosUiState.Error("Error")
                }
            } catch (ex: TimeoutCancellationException) {
                PreventivosUiState.Error("Error, no se ha recibido respuesta del servidor")
            }
        }
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val preventivosRepository = application.container.preventivosRepository
                val usuariosRepository = application.container.usuariosRepository
                val vehiculosRepository = application.container.vehiculosRepository
                PreventivosVM(
                    preventivoRepository = preventivosRepository,
                    usuariosRepository = usuariosRepository,
                    vehiculosRepository = vehiculosRepository
                )
            }
        }
    }
}