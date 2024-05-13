package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Guardia
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.network.GuardiasApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface GuardiasRepository {
    suspend fun getGuardias(): List<Guardia>
    suspend fun deleteGuardia(id: Int)
    suspend fun setGuardia(guardiaData: Map<String, String>)
    suspend fun updateGuardia(codGuardia: Int, guardiaData: Map<String, String>)

}

class NetworkGuardiasRepository(
    private val guardiasApiService: GuardiasApiService
) : GuardiasRepository {
    override suspend fun getGuardias(): List<Guardia> {
        val apiResponse = guardiasApiService.getGuardias("Bearer ${Token.token}")
        return Json.decodeFromJsonElement<List<Guardia>>(apiResponse.body)
    }

    override suspend fun deleteGuardia(id: Int) {
        guardiasApiService.deleteGuardia("Bearer ${Token.token}", id)
    }

    override suspend fun setGuardia(guardiaData: Map<String, String>) {
        guardiasApiService.altaGuardia("Bearer ${Token.token}", guardiaData)
    }

    override suspend fun updateGuardia(codGuardia: Int, guardiaData: Map<String, String>) {
        guardiasApiService.updateGuardia("Bearer ${Token.token}", codGuardia, guardiaData)
    }

}