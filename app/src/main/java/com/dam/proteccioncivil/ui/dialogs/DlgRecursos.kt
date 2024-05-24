package com.dam.proteccioncivil.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dam.proteccioncivil.ui.theme.AppColors

@Composable
fun DlgRecursos(
    onCancelarClick: () -> Unit,
    onVoluntariosClick: () -> Unit,
    onVehiculosClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onCancelarClick,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(AppColors.dialogColors)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, top = 16.dp)
            ) {
                Text(
                    text = "Seleccione un recurso",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textDecoration = TextDecoration.Underline,
                )
                Spacer(modifier = Modifier.height(32.dp))
                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = onVoluntariosClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonColors(
                        AppColors.Blue,
                        AppColors.White,
                        AppColors.GreyDisabled,
                        AppColors.White
                    )
                ) {
                    Text("Voluntarios")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = onVehiculosClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonColors(
                        AppColors.Blue,
                        AppColors.White,
                        AppColors.GreyDisabled,
                        AppColors.White
                    )
                ) {
                    Text("Vehículos")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Preview
@Composable
fun DlgRecursosScreenPreview() {
    DlgRecursos({}, {}, {})
}