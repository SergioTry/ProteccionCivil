package com.dam.proteccioncivil.ui.screens.guardia

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.pantallas.anuncios.GuardiasBus
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun GuardiasScreen(
    guardiasUiState: GuardiasUiState,
    guardiasVM: GuardiasVM,
    retryAction: () -> Unit,
    onNavUp: () -> Unit,
    refresh: () -> Unit,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (guardiasUiState) {
        is GuardiasUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is GuardiasUiState.Success -> {
            GuardiasBus(
                guardiasUiState.guardias,
                guardiasVM,
                onShowSnackBar,
                modifier,
                onNavUp,
                refresh
            )
        }

        is GuardiasUiState.Error -> ErrorScreen(
            retryAction,
            guardiasUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}
