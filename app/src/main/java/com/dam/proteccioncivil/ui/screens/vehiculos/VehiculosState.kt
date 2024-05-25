package com.dam.proteccioncivil.ui.screens.vehiculos

import com.dam.proteccioncivil.data.model.Vehiculo

sealed interface VehiculosUiState {
    data class Success(val vehiculos: List<Vehiculo>) : VehiculosUiState
    data class Error(val err: String) : VehiculosUiState
    object Loading : VehiculosUiState
}

sealed interface VehiculoMessageState {
    data object Success : VehiculoMessageState
    data class Error(val err: String) : VehiculoMessageState
    data object Loading : VehiculoMessageState
}

data class VehiculoMtoState(
    val codVehiculo: String = "0",
    val matricula: String = "",
    val marca: String = "",
    val modelo: String = "",
    val km: String = "0",
    val disponible: Boolean = false,
    val fechaMantenimiento: String? = null,
    val descripcion: String? = null,
    val codPreventivo: Int? = null,
    val datosObligatorios: Boolean = false
)

data class VehiculosBusState(
    val showDlgDate: Boolean = false,
    val showDlgConfirmation: Boolean = false)

fun VehiculoMtoState.toVehiculo(): Vehiculo = Vehiculo(
    codVehiculo = if (codVehiculo.isEmpty()) 0 else codVehiculo.toInt(),
    matricula = matricula,
    marca = marca,
    modelo = modelo,
    km = if (km.isEmpty()) 0.toFloat() else km.toFloat(),
    disponible = if (disponible) 1.toShort() else 0.toShort(),
    fechaMantenimiento = if (fechaMantenimiento.isNullOrEmpty()) "" else fechaMantenimiento,
    descripcionMantenimiento = descripcion,
    codPreveintvo = codPreventivo
)