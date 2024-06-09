package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnunciosApiService {

    @GET("anuncios")
    suspend fun getAnuncios(
        @Header("Authorization") authToken: String
    ): ApiResponse

    @POST("anuncios")
    suspend fun altaAnuncio(
        @Header("Authorization") authToken: String,
        @Body body: Map<String, String>
    ): ApiResponse

    @DELETE("anuncios/{codAnuncio}")
    suspend fun deleteAnuncio(
        @Header("Authorization") authToken: String,
        @Path("codAnuncio") id: Int
    ): ApiResponse

    @PUT("anuncios/{codAnuncio}")
    suspend fun updateAnuncio(
        @Header("Authorization") authToken: String,
        @Path("codAnuncio") id: Int,
        @Body body: Map<String, String>
    ): ApiResponse

}

