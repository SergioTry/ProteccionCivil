package com.dam.proteccioncivil.ui.dialogs

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DlgConfirmacion(
    @StringRes mensaje: Int,
    onCancelarClick: () -> Unit,
    onAceptarClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Borrado") },
        text = { Text("¿Estás seguro de eliminar este elemento?") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onCancelarClick
            ) {
                Text(text = "Cancelar")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onAceptarClick
            ) {
                Text(text = "Aceptar")
            }
        }
    )
}

@Preview
@Composable
fun DlgConfirmacionScreenPreview() {
    DlgConfirmacion(0,{},{})
}