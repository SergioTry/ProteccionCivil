package com.dam.proteccioncivil.data

import com.dam.proteccioncivil.data.network.AnunciosApiService
import com.dam.proteccioncivil.data.repository.AnunciosRepository
import com.dam.proteccioncivil.data.repository.NetworkAnunciosRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

import retrofit2.Retrofit

interface AppContainer {
    val anunciosRepository: AnunciosRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://192.168.56.1:5500/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: AnunciosApiService by lazy {
        retrofit.create(AnunciosApiService::class.java)
    }

    override val anunciosRepository: AnunciosRepository by lazy {
        NetworkAnunciosRepository(retrofitService)
    }
}