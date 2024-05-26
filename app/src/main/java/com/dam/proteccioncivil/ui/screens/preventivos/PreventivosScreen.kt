package com.dam.proteccioncivil.ui.screens.preventivos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun PreventivosScreen(
    preventivosUiState: PreventivosUiState,
    preventivosVM: PreventivosVM,
    retryAction: () -> Unit,
    onNavUp: () -> Unit,
    refresh: () -> Unit,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (preventivosUiState) {
        is PreventivosUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PreventivosUiState.Success -> {
            PreventivosBus(
                preventivosUiState.preventivos,
                preventivosVM,
                onShowSnackBar,
                modifier,
                onNavUp,
                refresh
            )
        }

        is PreventivosUiState.Error -> ErrorScreen(
            retryAction,
            preventivosUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}
