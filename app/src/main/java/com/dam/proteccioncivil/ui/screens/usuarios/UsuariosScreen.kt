package com.dam.proteccioncivil.ui.screens.usuarios

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun UsuariosScreen(
    usuariosUiState: UsuariosUiState,
    usuariosVM: UsuariosVM,
    retryAction: () -> Unit,
    onNavUp: () -> Unit,
    refresh: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (usuariosUiState) {
        is UsuariosUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is UsuariosUiState.Success -> {
            UsuariosBus(
                usuariosUiState.usuarios,
                usuariosVM,
                onShowSnackBar,
                modifier,
                onNavUp,
                refresh
            )
        }

        is UsuariosUiState.Error -> ErrorScreen(
            retryAction,
            usuariosUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}
