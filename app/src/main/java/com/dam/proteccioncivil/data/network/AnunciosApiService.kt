package com.dam.proteccioncivil.data.network

import com.dam.proteccioncivil.data.model.Anuncio
import retrofit2.http.GET

interface AnunciosApiService {

    @GET("anuncios")
    suspend fun getAnuncios(): List<Anuncio>
}