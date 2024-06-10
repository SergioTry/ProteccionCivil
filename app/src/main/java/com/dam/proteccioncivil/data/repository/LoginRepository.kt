package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.network.LoginApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface LoginRepository {
    suspend fun login(credentials: Map<String, String>): String
}

class NetworkLoginRepository(
    private val loginApiService: LoginApiService
) : LoginRepository {
    override suspend fun login(credentials: Map<String, String>): String {
        val apiResponse = loginApiService.login(credentials)
        return Json.decodeFromJsonElement<String>(apiResponse.body)
    }
}