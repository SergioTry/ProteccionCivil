package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AnunciosApiService {

    @GET("anuncios")
    suspend fun getAnuncios(@Header("Authorization") authToken: String): ApiResponse
}