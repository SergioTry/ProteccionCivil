package com.dam.proteccioncivil.ui.screens.anuncios

import com.dam.proteccioncivil.data.model.Anuncio

data class AnunciosMtoState(
    val codAnuncio: String = "",
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
