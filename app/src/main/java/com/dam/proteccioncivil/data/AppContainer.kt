package com.dam.proteccioncivil.data

import android.content.Context
import com.dam.proteccioncivil.data.model.AppDatastore
import com.dam.proteccioncivil.data.network.AnunciosApiService
import com.dam.proteccioncivil.data.network.LoginApiService
import com.dam.proteccioncivil.data.repository.AnunciosRepository
import com.dam.proteccioncivil.data.repository.LoginRepository
import com.dam.proteccioncivil.data.repository.MainRepository
import com.dam.proteccioncivil.data.repository.NetworkAnunciosRepository
import com.dam.proteccioncivil.data.repository.NetworkLoginRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

import retrofit2.Retrofit

interface AppContainer {
    val mainRepository: MainRepository
    val loginRepository: LoginRepository
    val anunciosRepository: AnunciosRepository
}
class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    private val baseUrl = "http://192.168.56.1:5500/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitAnunciosService: AnunciosApiService by lazy {
        retrofit.create(AnunciosApiService::class.java)
    }

    private val retrofitLoginService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    override val mainRepository: MainRepository by lazy {
        MainRepository(context, AppDatastore(context).getDataStore())
    }

    override val anunciosRepository: AnunciosRepository by lazy {
        NetworkAnunciosRepository(retrofitAnunciosService)
    }
    override val loginRepository: LoginRepository by lazy {
        NetworkLoginRepository(retrofitLoginService)
    }
}