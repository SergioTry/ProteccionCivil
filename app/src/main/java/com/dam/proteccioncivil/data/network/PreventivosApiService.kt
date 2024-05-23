package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PreventivosApiService {
    @GET("preventivos")
    suspend fun getPreventivos(@Header("Authorization") authToken: String): ApiResponse

    @GET("usuarios/{codUsuario}/preventivos")
    suspend fun getPreventivosUsuario(
        @Header("Authorization") authToken: String,
        @Path("codUsuario") codUsuario: Int
    ): ApiResponse


}