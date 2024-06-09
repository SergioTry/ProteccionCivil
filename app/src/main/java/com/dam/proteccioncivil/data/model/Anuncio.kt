package com.dam.proteccioncivil.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Anuncio(
    @SerialName("CodAnuncio") val codAnuncio: Int,
    @SerialName("FechaPublicacion") val fechaPublicacion: String,
    @SerialName("Texto") var texto: String
)


