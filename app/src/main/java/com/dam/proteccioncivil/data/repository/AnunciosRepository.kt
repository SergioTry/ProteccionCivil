package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.network.AnunciosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface AnunciosRepository {
    suspend fun getAnuncios(): List<Anuncio>
    suspend fun deleteAnuncio(id: Int)
    suspend fun setAnuncio(anuncioData: Map<String, String>)
    suspend fun updateAnuncio(codAnuncio: Int, anuncioData: Map<String, String>)

}

class NetworkAnunciosRepository(
    private val anunciosApiService: AnunciosApiService
) : AnunciosRepository {
    override suspend fun getAnuncios(): List<Anuncio> {
        val apiResponse = anunciosApiService.getAnuncios("Bearer ${Token.token}")
        return Json.decodeFromJsonElement<List<Anuncio>>(apiResponse.body)
    }

    override suspend fun deleteAnuncio(id: Int) {
        anunciosApiService.deleteAnuncio("Bearer ${Token.token}", id)
    }

    override suspend fun setAnuncio(anuncioData: Map<String, String>) {
        anunciosApiService.altaAnuncio("Bearer ${Token.token}", anuncioData)
    }

    override suspend fun updateAnuncio(codAnuncio: Int, anuncioData: Map<String, String>) {
        anunciosApiService.updateAnuncio("Bearer ${Token.token}", codAnuncio, anuncioData)
    }

}
