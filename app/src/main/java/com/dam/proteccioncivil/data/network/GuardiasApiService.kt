package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GuardiasApiService {
    @GET("guardias")
    suspend fun getGuardias(@Header("Authorization") authToken: String): ApiResponse

    @POST("guardias")
    suspend fun altaGuardia(
        @Header("Authorization") authToken: String,
        @Body body: Map<String, String>
    ): ApiResponse

    @DELETE("guardias/{codGuardia}")
    suspend fun deleteGuardia(
        @Header("Authorization") authToken: String,
        @Path("codGuardia") id: Int
    )

    @PUT("guardias/{codGuardia}")
    suspend fun updateGuardia(
        @Header("Authorization") authToken: String,
        @Path("codGuardia") id: Int,
        @Body body: Map<String, String>
    )

}
