package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.network.AnunciosApiService

interface AnunciosRepository {
    suspend fun getAnuncios(): List<Anuncio>
}

class NetworkAnunciosRepository(
    private val anunciosApiService: AnunciosApiService
) : AnunciosRepository {
    override suspend fun getAnuncios(): List<Anuncio> = anunciosApiService.getAnuncios()
}
