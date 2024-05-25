package com.dam.proteccioncivil.ui.screens.infomur

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dam.proteccioncivil.ui.screens.ErrorScreen
import com.dam.proteccioncivil.ui.screens.LoadingScreen

@Composable
fun InfomursScreen(
    infomursUiState: InfomursUiState,
    infomursVM: InfomursVM,
    retryAction: () -> Unit,
    onNavUp: () -> Unit,
    refresh: () -> Unit,
    onShowSnackBar: (String,Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (infomursUiState) {
        is InfomursUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is InfomursUiState.Success -> {
            InfomurBus(
                infomursUiState.infomurs,
                infomursVM,
                onShowSnackBar,
                modifier,
                onNavUp,
                refresh
            )
        }
        is InfomursUiState.Error -> ErrorScreen(
            retryAction,
            infomursUiState.err,
            modifier = modifier.fillMaxSize()
        )
    }
}
