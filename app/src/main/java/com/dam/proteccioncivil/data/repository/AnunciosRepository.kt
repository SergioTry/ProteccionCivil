package com.dam.proteccioncivil.data.repository

import retrofit2.HttpException
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.network.AnunciosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import java.io.IOException

interface AnunciosRepository {
    suspend fun getAnuncios(): List<Anuncio>
}

class NetworkAnunciosRepository(
    private val anunciosApiService: AnunciosApiService
) : AnunciosRepository {
    override suspend fun getAnuncios(): List<Anuncio> {
        try {
            val apiResponse = anunciosApiService.getAnuncios()
            val status = apiResponse.status
            when (val body = apiResponse.body) {
                is JsonArray -> {
                    return Json.decodeFromJsonElement<List<Anuncio>>(body)
                }

                is JsonPrimitive -> {
                    throw ApiException(body.content, status)
                }

                else -> {
                    throw IOException()
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorBodyString = errorBody?.string()

            // Ahora puedes procesar errorBodyString que contiene el cuerpo de la respuesta
            println("Cuerpo del error: $errorBodyString")
            throw IOException();
        }
    }
}
