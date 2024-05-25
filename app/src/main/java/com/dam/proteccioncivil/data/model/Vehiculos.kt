package com.dam.proteccioncivil.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vehiculo(
    @SerialName("CodVehiculo") val codVehiculo: Int,
    @SerialName("Matricula") val matricula: String,
    @SerialName("Marca") val marca: String,
    @SerialName("Modelo") val modelo: String,
    @SerialName("Km") val km: Float,
    @SerialName("Disponible") val disponible: Short,
    @SerialName("FechaMantenimiento") val fechaMantenimiento: String?,
    @SerialName("DescripcionMantenimiento") val descripcionMantenimiento: String?,
    @SerialName("CodPreventivo") val codPreveintvo: Int?
)
