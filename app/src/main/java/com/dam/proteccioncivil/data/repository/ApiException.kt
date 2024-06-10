package com.dam.proteccioncivil.data.repository

class ApiException(mensaje: String, val codigo: Int) : Exception(mensaje) {
}