package com.dam.proteccioncivil.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.theme.AppColors

@Composable
fun DlgServicios(
    onCancelarClick: () -> Unit,
    onGuardiasClick: () -> Unit,
    onInfomursClick: () -> Unit,
    onPreventivosClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onCancelarClick
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
                    .padding(8.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.servicios),
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    textDecoration = TextDecoration.Underline,
                    color = AppColors.Black
                )
                TextButton(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    onClick = onPreventivosClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonColors(
                        AppColors.Blue,
                        AppColors.White,
                        AppColors.GreyDisabled,
                        AppColors.White
                    )
                ) {
                    Text(stringResource(id = R.string.preventivos))
                }
                TextButton(
                    onClick = onInfomursClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonColors(
                        AppColors.Blue,
                        AppColors.White,
                        AppColors.GreyDisabled,
                        AppColors.White
                    ),
                    modifier = modifier
                        .width(90.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(stringResource(id = R.string.infomurs))
                }
                TextButton(
                    onClick = onGuardiasClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonColors(
                        AppColors.Blue,
                        AppColors.White,
                        AppColors.GreyDisabled,
                        AppColors.White
                    ),
                    modifier = modifier
                        .width(90.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(stringResource(id = R.string.guardias))
                }
            }
        }
    }
}

@Preview
@Composable
fun DlgServiciosScreenPreview() {
    DlgServicios({}, {}, {}, {})
}

