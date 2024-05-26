package com.dam.proteccioncivil.data.model

data class Preferencias(
    var username: String,
    var password: String,
    val iv: String
)