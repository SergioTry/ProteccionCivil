package com.dam.proteccioncivil.ui.screens.preventivos

import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.model.Vehiculo


data class PrevState(
    var preventivos: List<Preventivo> = listOf(),
)

data class PreventivoBusState(
    val expanded: Boolean = false,
    val showDlgDate: Boolean = false,
    val showDlgBorrar: Boolean = false
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
    val usuarios: List<Usuario>? = null,
    var vehiculos: List<Vehiculo>? = null
)

//data class IncsFilterState(
//    val idDpto: String = "-1",
//    val fecha: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
//    val estado: String = "%",
//    val showDlgDate: Boolean = false
//)
//
//fun PreventivoMtoState.toInc(): Preventivo = Preventivo(
//    titulo = titulo,
//    riesgo = riesgo,
//    usuarios = usuarios,
//    vehiculos = vehiculos,
//    codPreventivo = codPreventivo
//)
//
//fun Preventivo.PreventivoMtoState(): PreventivoMtoState = PreventivoMtoState(
//    titulo = titulo,
//    riesgo = riesgo,
//    fechaIni = fechaIni,
//    fechaFin = fechaFin,
//    dias = dias,
//    usuarios = usuarios,
//    vehiculos = vehiculos,
//    codPreventivo = codPreventivo
//)

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
        usuarios = preventivoMtoState.usuarios,
        vehiculos = preventivoMtoState.vehiculos
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
