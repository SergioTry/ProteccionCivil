package com.dam.proteccioncivil.ui.screens.anuncios

import com.dam.proteccioncivil.data.model.Anuncio

data class AnunciosMtoState(
    val codAnuncio: String = "0",
    val fechaPublicacion: String = "",
    val texto: String = "",
    val datosObligatorios: Boolean = false,
)

data class AnunciosBusState(
    val anuncioSelected: Int = -1,
    val showDlgBorrar: Boolean = false
)

fun AnunciosMtoState.toAnuncio(): Anuncio = Anuncio(
    codAnuncio = if (codAnuncio.isEmpty()) 0 else codAnuncio.toInt(),
    fechaPublicacion = fechaPublicacion,
    texto = texto
)

sealed interface AnunciosUiState {
    data class Success(val anuncios: List<Anuncio>) : AnunciosUiState
    data class Error(val err: String) : AnunciosUiState
    object Loading : AnunciosUiState
}

sealed interface AnunciosMessageState {
    data object Success : AnunciosMessageState
    data class Error(val err: String) : AnunciosMessageState
    data object Loading : AnunciosMessageState
}
