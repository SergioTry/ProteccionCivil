package com.dam.proteccioncivil.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.LabelledSwitch
import com.dam.proteccioncivil.ui.theme.AppColors


@Composable
fun DlgRangos(
    onCancelarClick: () -> Unit,
    onAplicarClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var rango by remember { mutableStateOf("") }
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
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.range_select), color = Color.Black,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    textDecoration = TextDecoration.Underline,
                )
                Spacer(modifier = modifier.height(28.dp))
                LabelledSwitch(
                    checked = rango == stringResource(id = R.string.range_vol),
                    label = stringResource(id = R.string.range_vol),
                    onCheckedChange = { rango = "Voluntario" },
                    roundedInt = 12,
                    backgroundColor =  AppColors.DarkBlue,
                    textColor = Color.White
                )
                LabelledSwitch(
                    checked = rango == stringResource(id = R.string.range_jef_db),
                    label = stringResource(id = R.string.range_jef),
                    onCheckedChange = { rango = "JefeServicio" },
                    roundedInt = 12,
                    modifier = modifier.padding(top = 8.dp),
                    backgroundColor =  AppColors.DarkBlue,
                    textColor = Color.White
                )
                LabelledSwitch(
                    checked = rango == stringResource(id = R.string.range_adm),
                    label = stringResource(id = R.string.range_adm),
                    onCheckedChange = { rango = "Admin" },
                    roundedInt = 12,
                    modifier = modifier.padding(top = 8.dp),
                    backgroundColor =  AppColors.DarkBlue,
                    textColor = Color.White

                )
                Spacer(modifier = modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    TextButton(
                        onClick = onCancelarClick,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonColors(
                            AppColors.RojoError,
                            AppColors.White,
                            AppColors.GreyDisabled,
                            AppColors.White
                        )
                    ) {
                        Text(stringResource(id = R.string.opc_cancel))
                    }

                    TextButton(
                        onClick = { onAplicarClick(rango) },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonColors(
                            AppColors.Blue,
                            AppColors.White,
                            AppColors.GreyDisabled,
                            AppColors.White
                        ),
                        enabled = rango.isNotEmpty()
                    ) {
                        Text(stringResource(id = R.string.opc_accept))
                    }

                }
            }
        }
    }
}
