package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.network.InfomursApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface InfomursRepository {
    suspend fun getInfomurs(): List<Infomur>
    suspend fun getInfomursUsuario(codInfomur: Int): List<Infomur>

    suspend fun deleteInfomur(id: Int)
    suspend fun setInfomur(infomurData: Map<String, String?>)
    suspend fun updateInfomur(codInfomur: Int, infomurData: Map<String, String?>)

}

class NetworkInfomursRepository(
    private val infomursApiService: InfomursApiService
) : InfomursRepository {
    override suspend fun getInfomurs(): List<Infomur> {
        val apiResponse = infomursApiService.getInfomurs("Bearer ${Token.token}")
        return Json.decodeFromJsonElement<List<Infomur>>(apiResponse.body)
    }

    override suspend fun getInfomursUsuario(codInfomur: Int): List<Infomur> {
        val apiResponse = infomursApiService.getInfomursUsuario("Bearer ${Token.token}", codInfomur)
        return Json.decodeFromJsonElement<List<Infomur>>(apiResponse.body)
    }

    override suspend fun deleteInfomur(id: Int) {
        infomursApiService.deleteInfomur("Bearer ${Token.token}", id)
    }

    override suspend fun setInfomur(infomurData: Map<String, String?>) {
        infomursApiService.altaInfomur("Bearer ${Token.token}", infomurData)
    }

    override suspend fun updateInfomur(codInfomur: Int, infomurData: Map<String, String?>) {
        infomursApiService.updateInfomur("Bearer ${Token.token}", codInfomur, infomurData)
    }

}
