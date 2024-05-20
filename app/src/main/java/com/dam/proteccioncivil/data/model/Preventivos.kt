package com.dam.proteccioncivil.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Preventivo(
    @SerialName("CodPreventivo") val codPreventivo: Int,
    @SerialName("Titulo") val titulo: String,
    @SerialName("Riesgo") val riesgo: Short,
    @SerialName("FechaDia1") val fechaDia1: String,
    @SerialName("FechaDia2") val fechaDia2: String?,
    @SerialName("FechaDia3") val fechaDia3: String?,
    @SerialName("FechaDia4") val fechaDia4: String?,
    @SerialName("FechaDia5") val fechaDia5: String?,
    @SerialName("FechaDia6") val fechaDia6: String?,
    @SerialName("FechaDia7") val fechaDia7: String?,
    val usuarios: List<Usuario>? = null,
    val vehiculos: List<Vehiculo>? = null,
)

