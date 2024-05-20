package com.dam.proteccioncivil.ui.screens.calendario

import com.dam.proteccioncivil.data.model.Guardia
import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Preventivo
import com.dam.proteccioncivil.data.model.Servicio
import java.time.LocalDate

sealed interface CalendarioUiState {
    data class Success(
        val servicios: Map<LocalDate, List<Servicio>>
    ) : CalendarioUiState

    data class Error(val err: String) : CalendarioUiState
    object Loading : CalendarioUiState
}
