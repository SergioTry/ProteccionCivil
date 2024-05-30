package com.dam.proteccioncivil.ui.screens.vehiculos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun VehiculosScreen(
    vehiculosUiState: VehiculosUiState,
    vehiculosVM: VehiculosVM,
    retryAction: () -> Unit,
    onNavUp: () -> Unit,
    onNavDetail: () -> Unit,
    refresh: () -> Unit,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (vehiculosUiState) {
        is VehiculosUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is VehiculosUiState.Success -> {
            VehiculosBus(
                vehiculosUiState.vehiculos,
                vehiculosVM,
                onShowSnackBar,
                modifier,
                onNavUp,
                onNavDetail,
                refresh
            )
        }

        is VehiculosUiState.Error -> ErrorScreen(
            retryAction,
            vehiculosUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}
