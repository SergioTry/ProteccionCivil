package com.dam.proteccioncivil.ui.screens.preventivos

import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.model.Vehiculo


data class PrevState(
    var preventivos: List<Preventivo> = listOf(),
)

data class PreventivoBusState(
    val usuarioBorrar: Boolean = false,
    val isBorrado: Boolean = false,
    val fechaABorrar: String = "",
    val action: String? = null,
    val isDetail: Boolean = false,
    val textoBusqueda: String = "",
    val lanzarBusqueda: Boolean = false,
    val showDlgDate: Boolean = false,
    val showDlgBorrar: Boolean = false,
    val showDlgSeleccionMes: Boolean = false,
    val comboBoxOptionSelected: String = "",
)

data class PreventivoMtoState(
    val codPreventivo: Int = -1,
    val titulo: String = "",
    val riesgo: Short = 0,
    val descripcion: String = "",
    val fechaDia1: String = "",
    val fechaDia2: String? = null,
    val fechaDia3: String? = null,
    val fechaDia4: String? = null,
    val fechaDia5: String? = null,
    val fechaDia6: String? = null,
    val fechaDia7: String? = null,
    var fechas: MutableList<String?> = mutableListOf(),
    val usuarios: List<Usuario>? = null,
    var vehiculos: List<Vehiculo>? = null,
    val datosObligatorios: Boolean = false
)

fun PreventivoMtoState.toPreventivo(preventivoMtoState: PreventivoMtoState): Preventivo {
    return Preventivo(
        codPreventivo = preventivoMtoState.codPreventivo,
        titulo = preventivoMtoState.titulo,
        riesgo = preventivoMtoState.riesgo,
        descripcion = preventivoMtoState.descripcion,
        fechaDia1 = preventivoMtoState.fechaDia1,
        fechaDia2 = preventivoMtoState.fechaDia2,
        fechaDia3 = preventivoMtoState.fechaDia3,
        fechaDia4 = preventivoMtoState.fechaDia4,
        fechaDia5 = preventivoMtoState.fechaDia5,
        fechaDia6 = preventivoMtoState.fechaDia6,
        fechaDia7 = preventivoMtoState.fechaDia7,
    )
}

sealed interface PreventivosUiState {
    data class Success(val preventivos: List<Preventivo>) : PreventivosUiState
    data class Error(val err: String) : PreventivosUiState
    object Loading : PreventivosUiState
}

sealed interface PreventivosMessageState {
    data object Success : PreventivosMessageState
    data class Error(val err: String) : PreventivosMessageState
    data object Loading : PreventivosMessageState
}
