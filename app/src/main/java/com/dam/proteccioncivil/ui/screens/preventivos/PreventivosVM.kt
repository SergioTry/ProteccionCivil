package com.dam.proteccioncivil.ui.screens.preventivos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam.proteccioncivil.data.model.CRUD
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.model.Vehiculo
import com.dam.proteccioncivil.data.repository.PreventivosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PreventivosVM(
    private val preventivoRepository: PreventivosRepository
) : CRUD<Preventivo>, ViewModel() {
    var uiPreventivoState: StateFlow<PrevState> = MutableStateFlow(PrevState())
        private set

    var selectedOption by mutableStateOf("")

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

//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application =
//                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
//                val preventivoRepository = application.container.r
//                PreventivosVM(preventivoRepository = preventivoRepository)
//            }
//        }
//    }
}