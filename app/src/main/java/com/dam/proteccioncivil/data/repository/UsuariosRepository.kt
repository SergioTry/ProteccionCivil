package com.dam.proteccioncivil.data.repository

import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.data.network.UsuariosApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

interface UsuariosRepository {
    suspend fun updateUsuario(codUsuario: Int, usuarioData: Map<String, String>)
    suspend fun getUsuarios(): List<Usuario>
    suspend fun getUsuarioById(codUsuario: Int): Usuario?
    suspend fun getUsuariosPreventivo(codPreventivo: Int): List<Usuario>
    suspend fun deleteUsuario(id: Int)
    suspend fun setUsuario(usuarioData: Map<String, String>)
}


class NetworkUsuariosRepository(
    private val usuariosApiService: UsuariosApiService
) : UsuariosRepository {
    override suspend fun updateUsuario(codUsuario: Int, usuarioData: Map<String, String>) {
        usuariosApiService.updateUsuario("Bearer ${Token.token}", codUsuario, usuarioData)
    }

    override suspend fun getUsuarios(): List<Usuario> {
        val apiResponse = usuariosApiService.getUsuarios("Bearer ${Token.token}")
        return Json.decodeFromJsonElement<List<Usuario>>(apiResponse.body)
    }

    override suspend fun getUsuarioById(codUsuario: Int): Usuario? {
        val apiResponse = usuariosApiService.getUsuarioByCodUsuario("Bearer ${Token.token}",codUsuario)
        return Json.decodeFromJsonElement<Usuario?>(apiResponse.body)
    }

    override suspend fun getUsuariosPreventivo(codPreventivo: Int): List<Usuario> {
        val apiResponse =
            usuariosApiService.getUsuariosPreventivo("Bearer ${Token.token}", codPreventivo)
        return Json.decodeFromJsonElement<List<Usuario>>(apiResponse.body)
    }

    override suspend fun deleteUsuario(id: Int) {
        usuariosApiService.deleteUsuario("Bearer ${Token.token}", id)
    }

    override suspend fun setUsuario(usuarioData: Map<String, String>) {
        usuariosApiService.altaUsuario("Bearer ${Token.token}", usuarioData)
    }

}