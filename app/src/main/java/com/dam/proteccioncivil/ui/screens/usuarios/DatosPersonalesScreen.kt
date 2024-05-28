package com.dam.proteccioncivil.ui.screens.usuarios

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun DatosPersonalesScreen(
    usuariosUiState: UsuariosUiState,
    usuariosVM: UsuariosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (usuariosUiState) {
        is UsuariosUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is UsuariosUiState.Success -> {
            val usuario = usuariosUiState.usuarios.find { it.codUsuario == Token.codUsuario }
            usuariosVM.cloneUsuarioMtoState(usuario!!)
            DatosPersonalesScreen(
                usuariosVM = usuariosVM,
                onShowSnackBar = onShowSnackBar, modifier = modifier
            )
        }

        is UsuariosUiState.Error -> ErrorScreen(
            { usuariosVM.getAll() },
            (usuariosVM.usuariosUiState as UsuariosUiState.Error).err,
            modifier = Modifier.fillMaxSize()
        )
    }
}
