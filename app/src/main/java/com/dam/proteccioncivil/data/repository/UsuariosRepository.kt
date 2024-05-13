package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.network.UsuariosApiService

interface UsuariosRepository {
    suspend fun updateUsuario(codUsuario: Int, usuarioData: Map<String, String>)
}


class NetworkUsuariosRepository(
    private val usuariosApiService: UsuariosApiService
) : UsuariosRepository {
    override suspend fun updateUsuario(codUsuario: Int, usuarioData: Map<String, String>) {
        usuariosApiService.updateAnuncio("Bearer ${Token.token}", codUsuario, usuarioData)
    }

}