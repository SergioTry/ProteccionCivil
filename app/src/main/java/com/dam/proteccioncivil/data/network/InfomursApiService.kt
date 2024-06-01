package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface InfomursApiService {
    @GET("infomurs")
    suspend fun getInfomurs(@Header("Authorization") authToken: String): ApiResponse

    @GET("usuarios/{codUsuario}/infomurs")
    suspend fun getInfomursUsuario(
        @Header("Authorization") authToken: String,
        @Path("codUsuario") codUsuario: Int
    ): ApiResponse

    @POST("infomurs")
    suspend fun altaInfomur(
        @Header("Authorization") authToken: String,
        @Body body: Map<String, String?>
    ): ApiResponse

    @DELETE("infomurs/{codInfomur}")
    suspend fun deleteInfomur(
        @Header("Authorization") authToken: String,
        @Path("codInfomur") id: Int
    )

    @PUT("infomurs/{codInfomur}")
    suspend fun updateInfomur(
        @Header("Authorization") authToken: String,
        @Path("codInfomur") id: Int,
        @Body body: Map<String, String?>
    )

}
