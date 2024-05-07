package com.dam.proteccioncivil.data.repository

import android.util.Log
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.network.AnunciosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface AnunciosRepository {
    suspend fun getAnuncios(): List<Anuncio>
}

class NetworkAnunciosRepository(
    private val anunciosApiService: AnunciosApiService
) : AnunciosRepository {
    override suspend fun getAnuncios(): List<Anuncio> {
        val apiResponse = anunciosApiService.getAnuncios("Bearer ${Token.token}")
        return Json.decodeFromJsonElement<List<Anuncio>>(apiResponse.body)
    }
}
