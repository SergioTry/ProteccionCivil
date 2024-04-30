package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.repository.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    suspend fun login(@Body body: Map<String, String>): ApiResponse
}