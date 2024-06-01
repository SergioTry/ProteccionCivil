package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Vehiculo
import com.dam.proteccioncivil.data.network.VehiculosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface VehiculosRepository {
    suspend fun updateVehiculo(codVehiculo: Int, vehiculoData: Map<String, String?>)
    suspend fun getVehiculos(disponible: Boolean? = null): List<Vehiculo>
    suspend fun getVehiculosPreventivo(codPreventivo: Int): List<Vehiculo>
    suspend fun deleteVehiculo(id: Int)
    suspend fun setVehiculo(vehiculoData: Map<String, String?>)
}


class NetworkVehiculosRepository(
    private val vehiculosApiService: VehiculosApiService
) : VehiculosRepository {
    override suspend fun updateVehiculo(codVehiculo: Int, vehiculoData: Map<String, String?>) {
        vehiculosApiService.updateVehiculo("Bearer ${Token.token}", codVehiculo, vehiculoData)
    }

    override suspend fun getVehiculos(disponible: Boolean?): List<Vehiculo> {
        val apiResponse = vehiculosApiService.getVehiculos("Bearer ${Token.token}", disponible)
        return Json.decodeFromJsonElement<List<Vehiculo>>(apiResponse.body)
    }

    override suspend fun getVehiculosPreventivo(codPreventivo: Int): List<Vehiculo> {
        val apiResponse =
            vehiculosApiService.getVehiculosPreventivo("Bearer ${Token.token}", codPreventivo)
        return Json.decodeFromJsonElement<List<Vehiculo>>(apiResponse.body)
    }

    override suspend fun deleteVehiculo(id: Int) {
        vehiculosApiService.deleteVehiculo("Bearer ${Token.token}", id)
    }

    override suspend fun setVehiculo(vehiculoData: Map<String, String?>) {
        vehiculosApiService.altaVehiculo("Bearer ${Token.token}", vehiculoData)
    }

}