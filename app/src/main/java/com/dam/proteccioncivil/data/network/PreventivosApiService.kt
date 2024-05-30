package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PreventivosApiService {
    @GET("preventivos")
    suspend fun getPreventivos(
        @Header("Authorization") authToken: String,
        @Query("riesgo") riesgo: Boolean?,
    ): ApiResponse

    @GET("usuarios/{codUsuario}/preventivos")
    suspend fun getPreventivosUsuario(
        @Header("Authorization") authToken: String,
        @Path("codUsuario") codUsuario: Int
    ): ApiResponse

    // El parámetro accion puede valer "add" o "del"
    // en caso de que se use alguno de estos parámetros
    // será necesario añadir en el body de la petición
    // el campo "CodUsuario".
    @PUT("preventivos/{codPreventivo}")
    suspend fun updPreventivo(
        @Header("Authorization") authToken: String,
        @Path("codPreventivo") codPreventivo: Int,
        @Body body: Map<String, String>,
        @Query("accion") accion: String?
    ): ApiResponse


}