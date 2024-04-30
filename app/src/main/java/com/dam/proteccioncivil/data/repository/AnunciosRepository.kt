package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.network.AnunciosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

val token =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VybmFtZSI6ImFkbWluIiwiTm9tYnJlIjoibm9tYnJlUHJ1ZWJhIiwiQXBlbGxpZG9zIjoiYXBlbGxpZG9zUHJ1ZWJhIiwiUmFuZ28iOiJBZG1pbiIsIkNvbmR1Y3RvciI6MSwiaWF0IjoxNzEzNzM3NTg3fQ.DVBsc7Skv1enPXRBzg0d_hcJOnDoCr9gf4yhnKYv5d4"

interface AnunciosRepository {
    suspend fun getAnuncios(): List<Anuncio>
}

class NetworkAnunciosRepository(
    private val anunciosApiService: AnunciosApiService
) : AnunciosRepository {
    override suspend fun getAnuncios(): List<Anuncio> {
        val apiResponse = anunciosApiService.getAnuncios("Bearer $token")
        return Json.decodeFromJsonElement<List<Anuncio>>(apiResponse.body)
    }
}
