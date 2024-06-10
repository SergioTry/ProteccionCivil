package com.dam.proteccioncivil.ui.screens.calendario

import Calendario
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun CalendarioScreen(
    calendarioUiState: CalendarioUiState,
    calendarioVM: CalendarioVM,
    retryAction: () -> Unit,
    refresh: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (calendarioUiState) {
        is CalendarioUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CalendarioUiState.Success -> {
            Calendario(servicios = calendarioUiState.servicios, calendarioVM = calendarioVM)
        }

        is CalendarioUiState.Error -> ErrorScreen(
            retryAction,
            calendarioUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}
