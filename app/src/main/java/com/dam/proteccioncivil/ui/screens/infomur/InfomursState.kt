package com.dam.proteccioncivil.ui.screens.infomur

import com.dam.proteccioncivil.data.model.Infomur

data class InfomursMtoState(
    val fechaInfomur: String = "",
    val codInfomur: String = "0",
    val descripcion: String = "",
    val codUsuario1: String = "0",
    val codUsuario2: String = "0",
    val datosObligatorios: Boolean = false
)

fun InfomursMtoState.toInfomur(): Infomur = Infomur(
    codInfomur = if (codInfomur.isEmpty()) 0 else codInfomur.toInt(),
    fechaInfomur = fechaInfomur,
    descripcion = descripcion,
    codUsuario1 = if (codUsuario1.isEmpty()) 0 else codUsuario1.toInt(),
    codUsuario2 = if (codUsuario2.isEmpty()) 0 else codUsuario2.toInt()
)