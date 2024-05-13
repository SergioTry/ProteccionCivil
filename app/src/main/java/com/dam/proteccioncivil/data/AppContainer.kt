package com.dam.proteccioncivil.data

import android.content.Context
import com.dam.proteccioncivil.data.model.AppDatastore
import com.dam.proteccioncivil.data.network.AnunciosApiService
import com.dam.proteccioncivil.data.network.GuardiasApiService
import com.dam.proteccioncivil.data.network.InfomursApiService
import com.dam.proteccioncivil.data.network.LoginApiService
import com.dam.proteccioncivil.data.network.UsuariosApiService
import com.dam.proteccioncivil.data.repository.AnunciosRepository
import com.dam.proteccioncivil.data.repository.GuardiasRepository
import com.dam.proteccioncivil.data.repository.InfomursRepository
import com.dam.proteccioncivil.data.repository.LoginRepository
import com.dam.proteccioncivil.data.repository.MainRepository
import com.dam.proteccioncivil.data.repository.NetworkAnunciosRepository
import com.dam.proteccioncivil.data.repository.NetworkGuardiasRepository
import com.dam.proteccioncivil.data.repository.NetworkInfomursRepository
import com.dam.proteccioncivil.data.repository.NetworkLoginRepository
import com.dam.proteccioncivil.data.repository.NetworkUsuariosRepository
import com.dam.proteccioncivil.data.repository.UsuariosRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

import retrofit2.Retrofit

interface AppContainer {
    val mainRepository: MainRepository
    val loginRepository: LoginRepository
    val anunciosRepository: AnunciosRepository
    val usuariosRepository: UsuariosRepository
    val guardiasRepository: GuardiasRepository
    val infomursRepository: InfomursRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    private val baseUrl = "http://192.168.56.1:49999/api/"
    //private val baseUrl = "http://192.168.68.50:49999/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitAnunciosService: AnunciosApiService by lazy {
        retrofit.create(AnunciosApiService::class.java)
    }

    private val retrofitGuardiasService: GuardiasApiService by lazy {
        retrofit.create(GuardiasApiService::class.java)
    }

    private val retrofitInfomursService: InfomursApiService by lazy {
        retrofit.create(InfomursApiService::class.java)
    }

    private val retrofitLoginService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    private val retrofitUsuariosService: UsuariosApiService by lazy {
        retrofit.create(UsuariosApiService::class.java)
    }

    override val mainRepository: MainRepository by lazy {
        MainRepository(context, AppDatastore(context).getDataStore())
    }

    override val anunciosRepository: AnunciosRepository by lazy {
        NetworkAnunciosRepository(retrofitAnunciosService)
    }

    override val guardiasRepository: GuardiasRepository by lazy {
        NetworkGuardiasRepository(retrofitGuardiasService)
    }

    override val infomursRepository: InfomursRepository by lazy {
        NetworkInfomursRepository(retrofitInfomursService)
    }

    override val loginRepository: LoginRepository by lazy {
        NetworkLoginRepository(retrofitLoginService)
    }
    override val usuariosRepository: UsuariosRepository by lazy {
        NetworkUsuariosRepository(retrofitUsuariosService)
    }
}