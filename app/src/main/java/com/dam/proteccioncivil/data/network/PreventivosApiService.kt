package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface PreventivosApiService {
    @GET("preventivos")
    suspend fun getPreventivos(@Header("Authorization") authToken: String): ApiResponse

}