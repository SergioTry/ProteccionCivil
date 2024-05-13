package com.dam.proteccioncivil.data.model

import java.time.LocalDateTime

object Token {
    var token: String? = null
    var codUsuario: Int? = null
    var username: String? = null
    var nombre: String? = null
    var fechaNacimiento: LocalDateTime? = null
    var rango: String? = null
    var conductor: Int? = null
    // La contraseña no es recibida en el token,
    // se guarda encriptada tras hacer un login con éxito
    // para poder almacenarla en las preferencias
    // en caso de activar la opción de login automático.
    var password: String? = null
}