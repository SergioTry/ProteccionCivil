package com.dam.proteccioncivil.data.repository


import com.dam.proteccioncivil.data.model.Dia
import com.dam.proteccioncivil.data.model.Preventivo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class PreventivoRepository {

    private val preventivosFakeData = listOf(
        Preventivo(
            1,
            "Preventivo 1",
            1,
            LocalDate.now(),
            LocalDate.now(),
            usuarios = listOf(),
            vehiculos = listOf(),
            listOf(
                Dia(LocalDate.now(), true)
            )
        ),
        Preventivo(
            2,
            "Preventivo 2",
            2,
            LocalDate.now(),
            LocalDate.now(),
            usuarios = listOf(),
            vehiculos = listOf(),
            listOf(Dia(LocalDate.now(), false))
        ),
        Preventivo(
            3,
            "Preventivo 3",
            3,
            LocalDate.now(),
            LocalDate.now(),
            usuarios = listOf(),
            vehiculos = listOf(),
            listOf(Dia(LocalDate.now(), true))
        )
    )

    fun getAllPreventivos(): Flow<List<Preventivo>> = flow {
        emit(preventivosFakeData)
    }
    fun deletePreventivo(preventivo: Preventivo) {

    }

    fun updatePreventivo(preventivo: Preventivo) {

    }

    fun insertPreventivo(preventivo: Preventivo) {

    }

}
