package com.dam.proteccioncivil.ui.dialogs

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dam.proteccioncivil.R

@Composable
fun DlgConfirmacion(
    @StringRes mensaje: Int,
    onCancelarClick: () -> Unit,
    onAceptarClick: () -> Unit,
    modifier: Modifier = Modifier
){

    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.app_name)) },
        text = { Text(stringResource(mensaje, 0)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onCancelarClick
            ) {
                Text(text = "stringResource(R.string.but_cancelar)")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onAceptarClick
            ) {
                Text(text = "stringResource(R.string.but_aceptar)")
            }
        }
    )
}