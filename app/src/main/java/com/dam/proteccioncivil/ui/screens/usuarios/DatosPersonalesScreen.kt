package com.dam.proteccioncivil.ui.screens.usuarios

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun DatosPersonales(
    usuariosUiState: UsuariosUiState,
    usuariosVM: UsuariosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    mainVM: MainVM
) {
    when (usuariosUiState) {
        is UsuariosUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is UsuariosUiState.Success -> {
            //Cuando se llama a un único recurso, se recibe una lista con un único objeto, que es el que queremos tratar.
            usuariosVM.cloneUsuarioMtoState(usuariosUiState.usuarios[0])
            DatosPersonales(
                mainVM = mainVM,
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
