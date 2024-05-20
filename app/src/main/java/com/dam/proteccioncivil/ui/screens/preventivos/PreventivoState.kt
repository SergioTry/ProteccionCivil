package com.dam.proteccioncivil.ui.screens.preventivos

//import com.dam.proteccioncivil.data.model.Dia
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.model.Vehiculo
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class PrevState(
    var preventivos: List<Preventivo> = listOf(),
)

data class PrevBusState(
    val prevSelected: Int = -1,
    var isEditing: Boolean = false,
    val showDlgBorrar: Boolean = false
)

//data class PreventivoMtoState(
//    val codPreventivo: Int = -1,
//    val titulo: String = "",
//    val riesgo: Short = 0,
//    val fechaFin: LocalDate = LocalDate.now(),
//    val fechaIni: LocalDate = LocalDate.now(),
//    val usuarios: List<Usuario>? = null,
//    var vehiculos: List<Vehiculo>? = null
//)
//
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

sealed interface PrevsInfoState {
    data object Loading : PrevsInfoState
    data object Success : PrevsInfoState
    data object Error : PrevsInfoState
}