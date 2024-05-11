package com.dam.proteccioncivil.ui.screens.anuncios

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.pantallas.anuncios.AnunciosBus
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun AnunciosScreen(
    anunciosUiState: AnunciosUiState,
    anunciosVM: AnunciosVM,
    retryAction: () -> Unit,
    onNavUp: () -> Unit,
    refresh: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (anunciosUiState) {
        is AnunciosUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AnunciosUiState.Success -> {
            AnunciosBus(
                anunciosUiState.anuncios,
                anunciosVM,
                onShowSnackBar,
                modifier,
                onNavUp,
                refresh
            )
        }

        is AnunciosUiState.Error -> ErrorScreen(
            retryAction,
            anunciosUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}
