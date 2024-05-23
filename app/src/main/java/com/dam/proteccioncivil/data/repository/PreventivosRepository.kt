package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.network.PreventivosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface PreventivosRepository {
    suspend fun getPreventivos(): List<Preventivo>
    suspend fun getPreventivosUsuario(codPreventivo: Int): List<Preventivo>
}

class NetworkPreventivosRepository(
    private val preventivosApiService: PreventivosApiService
) : PreventivosRepository {
    override suspend fun getPreventivos(): List<Preventivo> {
        val apiResponse = preventivosApiService.getPreventivos("Bearer ${Token.token}")
        return Json.decodeFromJsonElement<List<Preventivo>>(apiResponse.body)
    }

    override suspend fun getPreventivosUsuario(codPreventivo: Int): List<Preventivo> {
        val apiResponse = preventivosApiService.getPreventivosUsuario("Bearer ${Token.token}",codPreventivo)
        return Json.decodeFromJsonElement<List<Preventivo>>(apiResponse.body)
    }
}