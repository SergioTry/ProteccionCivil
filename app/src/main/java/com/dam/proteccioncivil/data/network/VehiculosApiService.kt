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

interface VehiculosApiService {
    @GET("vehiculos")
    suspend fun getVehiculos(
        @Header("Authorization") authToken: String,
        @Query("disponible") disponible: Boolean?,
    ): ApiResponse

    @GET("preventivos/{codPreventivo}/vehiculos")
    suspend fun getVehiculosPreventivo(
        @Header("Authorization") authToken: String,
        @Path("codPreventivo") codPreventivo: Int
    ): ApiResponse

    @POST("vehiculos")
    suspend fun altaVehiculo(
        @Header("Authorization") authToken: String,
        @Body body: Map<String, String?>
    ): ApiResponse

    @DELETE("vehiculos/{codVehiculo}")
    suspend fun deleteVehiculo(
        @Header("Authorization") authToken: String,
        @Path("codVehiculo") id: Int
    )

    @PUT("vehiculos/{codVehiculo}")
    suspend fun updateVehiculo(
        @Header("Authorization") authToken: String,
        @Path("codVehiculo") id: Int,
        @Body body: Map<String, String?>
    )

}