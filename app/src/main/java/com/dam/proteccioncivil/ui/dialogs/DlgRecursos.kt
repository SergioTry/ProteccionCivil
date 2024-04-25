package com.dam.proteccioncivil.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DlgConfirmacion(
    onCancelarClick: () -> Unit,
    onAceptarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Recursos") },
        text = { Text("Seleccione el recurso a gestionar") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onCancelarClick
            ) {
                Text("Voluntarios")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onAceptarClick
            ) {
                Text("Vehiculos")
            }
        }
    )
}


@Preview
@Composable
fun ScreenPreview() {
    DlgConfirmacion({}, {})
}