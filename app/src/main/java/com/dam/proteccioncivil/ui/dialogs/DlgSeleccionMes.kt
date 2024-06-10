package com.dam.proteccioncivil.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.dam.proteccioncivil.data.model.meses
import com.dam.proteccioncivil.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DlgSeleccionMes(
    onCancelarClick: () -> Unit,
    onAplicarClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var mesSeleccionado by remember { mutableStateOf(meses[0]) }
    var numeroMes by remember { mutableIntStateOf(1) }

    var expanded by remember { mutableStateOf(false) }

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
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.month_select), color = Color.Black,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    textDecoration = TextDecoration.Underline,
                )
                Spacer(modifier = modifier.height(12.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = modifier
                ) {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = AppColors.DarkBlue,
                            focusedContainerColor = AppColors.DarkBlue,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        readOnly = true,
                        value = mesSeleccionado,
                        onValueChange = { },
                        label = { Text("Mes") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.heightIn(max = 200.dp)
                    ) {
                        meses.forEachIndexed { index, mes ->
                            DropdownMenuItem(
                                onClick = {
                                    mesSeleccionado = mes
                                    numeroMes = index + 1
                                    expanded = false
                                },
                                text = { Text(text = mes) }
                            )
                        }
                    }
                }
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
                        onClick = { onAplicarClick(numeroMes) },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonColors(
                            AppColors.Blue,
                            AppColors.White,
                            AppColors.GreyDisabled,
                            AppColors.White
                        ),
                    ) {
                        Text(stringResource(id = R.string.opc_accept))
                    }

                }
            }
        }
    }
}
