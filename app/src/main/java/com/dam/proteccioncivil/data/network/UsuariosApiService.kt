package com.dam.proteccioncivil.data.network

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuariosApiService {
    @PUT("anuncios/{codAnuncio}")
    suspend fun updateAnuncio(
        @Header("Authorization") authToken: String,
        @Path("codUsuario") id: Int,
        @Body body: Map<String, String>
    )
}