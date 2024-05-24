package com.dam.proteccioncivil.ui.dialogs

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.theme.AppColors

@Composable
fun DlgConfirmacion(
    @StringRes mensaje: Int,
    onCancelarClick: () -> Unit,
    onAceptarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { },
        text = { Text(stringResource(mensaje)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onCancelarClick
            ) {
                Text(text = stringResource(R.string.opc_cancel), color = AppColors.OrangeColor)
            }
        },
        confirmButton = {
            TextButton(
                onClick = onAceptarClick
            ) {
                Text(text = stringResource(R.string.opc_accept), color = AppColors.OrangeColor)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDlgConfirmacion() {
    DlgConfirmacion(
        mensaje = R.string.anuncio_create_failure,
        onCancelarClick = { /* Acción de cancelar */ },
        onAceptarClick = { /* Acción de aceptar */ },
        modifier = Modifier // Para asegurar que el fondo sea visible en el preview
    )
}

