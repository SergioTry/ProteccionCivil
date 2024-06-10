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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dam.proteccioncivil.R
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
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(AppColors.DialogColors)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp, top = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.recursos_select), color = Color.Black,
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    textDecoration = TextDecoration.Underline,
                )
                Spacer(modifier = modifier.height(12.dp))
                TextButton(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    onClick = onVoluntariosClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonColors(
                        AppColors.Blue,
                        AppColors.White,
                        AppColors.GreyDisabled,
                        AppColors.White
                    )
                ) {
                    Text(stringResource(id = R.string.opc_voluntarios))
                }
                Spacer(modifier = modifier.height(8.dp))
                TextButton(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    onClick = onVehiculosClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonColors(
                        AppColors.Blue,
                        AppColors.White,
                        AppColors.GreyDisabled,
                        AppColors.White
                    )
                ) {
                    Text(stringResource(id = R.string.vehiculos))
                }
            }
        }
    }
}


@Preview
@Composable
fun DlgRecursosScreenPreview() {
    DlgRecursos({}, {}, {})
}