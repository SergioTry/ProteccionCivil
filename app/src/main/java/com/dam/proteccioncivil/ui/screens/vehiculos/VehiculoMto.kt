package com.dam.proteccioncivil.ui.screens.vehiculos

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VehiculoMto(
    vehiculosVM: VehiculosVM,
    refresh: () -> Unit,
    onShowSnackBar: (String, Boolean) -> Unit
) {
    var mensage: String
    val contexto = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val activity = (LocalContext.current as? Activity)

    when (vehiculosVM.vehiculosMessageState) {
        is VehiculoMessageState.Loading -> {
        }

        is VehiculoMessageState.Success -> {
            mensage = if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                ContextCompat.getString(contexto, R.string.vehiculo_create_success)
            } else {
                ContextCompat.getString(contexto, R.string.vehiculo_edit_success)
            }
            onShowSnackBar(mensage, true)
            vehiculosVM.getAll()
            refresh()
            vehiculosVM.resetInfoState()
            vehiculosVM.resetVehiculoMtoState()
        }

        is VehiculoMessageState.Error -> {
            mensage = if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                getString(contexto, R.string.vehiculo_create_failure)
            } else {
                getString(contexto, R.string.vehiculo_edit_failure)
            }
            mensage =
                mensage + ": " + (vehiculosVM.vehiculosMessageState as VehiculoMessageState.Error).err
            onShowSnackBar(mensage, false)
            vehiculosVM.resetInfoState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = stringResource(id = R.string.fondo_desc),
            modifier = Modifier.fillMaxSize(),
        )
        Card(
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(AppColors.Posit)
        ) {
            Column {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        label = {
                            Text(
                                text = stringResource(id = R.string.matricula_lit),
                                color = Color.Black
                            )
                        },
                        readOnly = vehiculosVM.vehiculosBusState.isDetail,
                        value = vehiculosVM.vehiculosMtoState.matricula,
                        onValueChange = { vehiculosVM.setMatricula(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            errorTextColor = Color.Red
                        ),
                        isError = vehiculosVM.vehiculosMtoState.matricula == "",
                        enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        label = {
                            Text(
                                text = stringResource(id = R.string.kilometros_lit),
                                color = Color.Black
                            )
                        },
                        readOnly = vehiculosVM.vehiculosBusState.isDetail,
                        value = vehiculosVM.vehiculosMtoState.km,
                        onValueChange = { vehiculosVM.setKm(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            errorTextColor = Color.Red
                        ),
                        isError = vehiculosVM.vehiculosMtoState.km == "",
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        readOnly = vehiculosVM.vehiculosBusState.isDetail,
                        label = {
                            Text(
                                text = stringResource(id = R.string.marca_lit),
                                color = Color.Black
                            )
                        },
                        value = vehiculosVM.vehiculosMtoState.marca,
                        onValueChange = { vehiculosVM.setMarca(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            errorTextColor = Color.Red
                        ),
                        isError = vehiculosVM.vehiculosMtoState.marca == "",
                        enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        readOnly = vehiculosVM.vehiculosBusState.isDetail,
                        label = {
                            Text(
                                text = stringResource(id = R.string.modelo_lit),
                                color = Color.Black
                            )
                        },
                        value = vehiculosVM.vehiculosMtoState.modelo,
                        onValueChange = { vehiculosVM.setModelo(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            errorTextColor = Color.Red
                        ),
                        isError = vehiculosVM.vehiculosMtoState.modelo == "",
                        enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Row {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                readOnly = vehiculosVM.vehiculosBusState.isDetail,
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.fecha_mantenimiento_lit),
                                        color = Color.Black
                                    )
                                },
                                value = FormatVisibleDate.use(vehiculosVM.vehiculosMtoState.fechaMantenimiento),
                                onValueChange = {},
                                modifier = Modifier.fillMaxWidth(),
                                isError = vehiculosVM.vehiculosMtoState.fechaMantenimiento == "",
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Blue,
                                    unfocusedLabelColor = Color.Black,
                                    errorBorderColor = Color.Red,
                                    errorLabelColor = Color.Red,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    errorTextColor = Color.Red
                                ),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                            )
                            if (!vehiculosVM.vehiculosBusState.isDetail) {
                                IconButton(
                                    onClick = {
                                        vehiculosVM.setShowDlgDate(true)
                                    },
                                    enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = getString(
                                            contexto,
                                            R.string.fecha_desc
                                        ),
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        readOnly = vehiculosVM.vehiculosBusState.isDetail,
                        label = {
                            Text(
                                text = stringResource(id = R.string.descripcion_mantenimiento_lit),
                                color = Color.Black
                            )
                        },
                        value = vehiculosVM.vehiculosMtoState.descripcion.let { if (it != "null" && it != null) vehiculosVM.vehiculosMtoState.descripcion else "" }
                            ?: "",
                        onValueChange = { vehiculosVM.setDescripcion(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            errorTextColor = Color.Red
                        ),
                        isError = vehiculosVM.vehiculosMtoState.descripcion == "",
                        enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                if (vehiculosVM.vehiculosMtoState.datosObligatorios && vehiculosVM.hasStateChanged()) {
                                    if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                                        vehiculosVM.setNew()
                                    } else {
                                        vehiculosVM.update()
                                    }
                                }
                            }
                        )
                    )
                }
            }
        }
        if (!vehiculosVM.vehiculosBusState.isDetail) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        vehiculosVM.resetVehiculoMtoState()
                        activity?.onBackPressed()
                    },
                    enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.RojoError),
                ) {
                    Text(text = stringResource(id = R.string.opc_cancel), color = Color.Black)
                }
                Spacer(modifier = Modifier.width(100.dp))
                Button(
                    enabled = vehiculosVM.vehiculosMtoState.datosObligatorios && vehiculosVM.hasStateChanged(),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue),
                    onClick = {
                        if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                            vehiculosVM.setNew()
                        } else {
                            vehiculosVM.update()
                        }
                    },
                ) {
                    Text(
                        text = if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                            stringResource(id = R.string.opc_create)
                        } else {
                            stringResource(id = R.string.opc_edit)
                        }, color = Color.Black
                    )
                }
            }
        }
    }
    if (vehiculosVM.vehiculosBusState.showDlgDate) {
        DlgSeleccionFecha(
            modifier = Modifier,
            onClick = {
                if (vehiculosVM.vehiculosUiState != VehiculosUiState.Loading) {
                    vehiculosVM.setShowDlgDate(false)
                    vehiculosVM.setFechaMantenimiento(it)
                    focusRequester.requestFocus()
                }
            },
            onDismiss = {
                vehiculosVM.setShowDlgDate(false)
            }
        )
    }
}