package com.dam.proteccioncivil.data.model

import java.time.LocalDateTime


data class Vehiculo(
    val CodVehiculo: Int,
    val Matricula: String,
    val Marca: String,
    val Modelo: String,
    val Km: Float,
    val Disponible: Short,
    val FechaMantenimiento: LocalDateTime,
    val Descripcion: String
)
