package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuariosApiService {

    // El parámetro conductores solo admite el valor true
    // El parámetro mayores solo admite el valor true
    @GET("usuarios")
    suspend fun getUsuarios(
        @Header("Authorization") authToken: String,
        @Query("conductores") conductores: Boolean?,
        @Query("mayores") mayores: Boolean?,
        @Query("rango") rango: String?,
    ): ApiResponse

    @GET("usuarios/{codUsuario}")
    suspend fun getUsuarioByCodUsuario(
        @Header("Authorization") authToken: String,
        @Path("codUsuario") codUsuario: Int
    ): ApiResponse

    @GET("preventivos/{codPreventivo}/usuarios")
    suspend fun getUsuariosPreventivo(
        @Header("Authorization") authToken: String,
        @Path("codPreventivo") codPreventivo: Int
    ): ApiResponse

    @POST("usuarios")
    suspend fun altaUsuario(
        @Header("Authorization") authToken: String,
        @Body body: Map<String, String>
    ): ApiResponse

    @DELETE("usuarios/{codUsuario}")
    suspend fun deleteUsuario(
        @Header("Authorization") authToken: String,
        @Path("codUsuario") id: Int
    ): ApiResponse

    @PUT("usuarios/{codUsuario}")
    suspend fun updateUsuario(
        @Header("Authorization") authToken: String,
        @Path("codUsuario") id: Int,
        @Body body: Map<String, String>
    ): ApiResponse
}

