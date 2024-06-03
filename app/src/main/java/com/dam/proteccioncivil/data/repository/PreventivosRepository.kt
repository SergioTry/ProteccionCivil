package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.network.PreventivosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface PreventivosRepository {
    suspend fun getPreventivos(riesgo: Boolean? = null, mes: Int? = null): List<Preventivo>
    suspend fun getPreventivosUsuario(codPreventivo: Int): List<Preventivo>
    suspend fun deletePreventivo(codPreventivo: Int)
    suspend fun setPreventivo(preventiveData: Map<String, String>)
    suspend fun updPreventivo(
        codPreventivo: Int,
        preventiveData: Map<String, String>,
        action: String? = null
    )
}

class NetworkPreventivosRepository(
    private val preventivosApiService: PreventivosApiService
) : PreventivosRepository {
    override suspend fun getPreventivos(riesgo: Boolean?, mes: Int?): List<Preventivo> {
        val apiResponse =
            preventivosApiService.getPreventivos(
                "Bearer ${Token.token}",
                riesgo = riesgo,
                mes = mes
            )
        return Json.decodeFromJsonElement<List<Preventivo>>(apiResponse.body)
    }

    override suspend fun getPreventivosUsuario(codPreventivo: Int): List<Preventivo> {
        val apiResponse =
            preventivosApiService.getPreventivosUsuario("Bearer ${Token.token}", codPreventivo)
        return Json.decodeFromJsonElement<List<Preventivo>>(apiResponse.body)
    }

    override suspend fun deletePreventivo(codPreventivo: Int) {
        preventivosApiService.deletePreventivo("Bearer ${Token.token}", codPreventivo)
    }

    override suspend fun setPreventivo(preventiveData: Map<String, String>) {
        preventivosApiService.altaPreventivo("Bearer ${Token.token}", preventiveData)
    }

    override suspend fun updPreventivo(
        codPreventivo: Int,
        preventiveData: Map<String, String>,
        action: String?
    ) {
        preventivosApiService.updPreventivo(
            authToken = "Bearer ${Token.token}",
            codPreventivo = codPreventivo,
            body = preventiveData,
            accion = action
        )
    }

}