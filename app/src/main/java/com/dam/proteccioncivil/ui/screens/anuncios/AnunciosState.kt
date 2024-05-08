package com.dam.proteccioncivil.ui.screens.anuncios

import com.dam.proteccioncivil.data.model.Anuncio

data class AnunciosMtoState(
    val codAnuncio: String = "",
    val fechaPublicacion: String = "",
    val texto: String = "",
    val datosObligatorios: Boolean = false,
)

fun AnunciosMtoState.toAnuncio(): Anuncio = Anuncio(
    codAnuncio = codAnuncio.toInt(),
    fechaPublicacion = fechaPublicacion,
    texto = texto
)