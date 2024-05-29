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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha
import com.dam.proteccioncivil.ui.theme.AppColors
import kotlinx.coroutines.Job

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VehiculoMto(
    vehiculosVM: VehiculosVM,
    onNavDown: () -> Unit,
    onShowSnackBar: (String, Boolean) -> Job
) {
    val mensage: String
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
        vehiculosVM.setFechaMantenimiento(FormatDate.use())
    }

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
            onNavDown()
            vehiculosVM.resetInfoState()
            vehiculosVM.resetVehiculoMtoState()
        }

        is VehiculoMessageState.Error -> {
            mensage = if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                ContextCompat.getString(contexto, R.string.vehiculo_create_failure)
            } else {
                ContextCompat.getString(contexto, R.string.vehiculo_edit_failure)
            }
            onShowSnackBar(mensage, false)
            vehiculosVM.resetInfoState()
        }
    }

    if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
        vehiculosVM.setFechaMantenimiento(FormatDate.use())
    }

    val oldMtoState = vehiculosVM.vehiculosMtoState

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
            colors = CardDefaults.cardColors(AppColors.posit)
        ) {
            Column {
                Column(modifier = Modifier.padding(12.dp)) {
//                    OutlinedTextField(
//                        label = { Text(text = "Identificador") },
//                        value = vehiculosVM.vehiculosMtoState.codVehiculo,
//                        readOnly = true,
//                        onValueChange = {},
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = Color.Blue,
//                            unfocusedBorderColor = Color.Black,
//                            focusedLabelColor = Color.Blue,
//                            unfocusedLabelColor = Color.Black
//                        )
//                    )
                    // Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        label = { Text(text = stringResource(id = R.string.matricula_lit)) },
                        value = vehiculosVM.vehiculosMtoState.matricula,
                        onValueChange = { vehiculosVM.setMatricula(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black
                        ),
                        isError = vehiculosVM.vehiculosMtoState.matricula == ""
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        label = { Text(text = stringResource(id = R.string.marca_lit)) },
                        value = vehiculosVM.vehiculosMtoState.marca,
                        onValueChange = { vehiculosVM.setMarca(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black
                        ),
                        isError = vehiculosVM.vehiculosMtoState.marca == ""
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        label = { Text(text = stringResource(id = R.string.modelo_lit)) },
                        value = vehiculosVM.vehiculosMtoState.modelo,
                        onValueChange = { vehiculosVM.setModelo(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black
                        ),
                        isError = vehiculosVM.vehiculosMtoState.modelo == ""
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Row {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                label = { Text(text = stringResource(id = R.string.fecha_mantenimiento_lit)) },
                                value = FormatVisibleDate.use(vehiculosVM.vehiculosMtoState.fechaMantenimiento),
                                onValueChange = {},
                                modifier = Modifier.fillMaxWidth(),
                                isError = vehiculosVM.vehiculosMtoState.fechaMantenimiento == ""
                            )
                            IconButton(
                                onClick = {
                                    vehiculosVM.setShowDlgDate(true)
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = getString(contexto, R.string.fecha_desc)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        label = { Text(text = stringResource(id = R.string.descripcion_mantenimiento_lit)) },
                        value = vehiculosVM.vehiculosMtoState.descripcion.let { if(it != "null" && it != null) vehiculosVM.vehiculosMtoState.descripcion else "" }
                            ?: "",
                        onValueChange = { vehiculosVM.setDescripcion(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black
                        ),
                        isError = vehiculosVM.vehiculosMtoState.descripcion == ""
                    )
                }
            }
        }
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
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.errorCarmesi),
            ) {
                Text(text = stringResource(id = R.string.opc_cancel))
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(
                enabled = vehiculosVM.vehiculosMtoState.datosObligatorios,
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue),
                onClick = {
                    if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                        vehiculosVM.setNew()
                    } else {
                        vehiculosVM.update()
                    }
                }
            ) {
                Text(
                    text = if (vehiculosVM.vehiculosMtoState.codVehiculo == "0") {
                        stringResource(id = R.string.opc_create)
                    } else {
                        stringResource(id = R.string.opc_edit)
                    }
                )
            }
        }
    }
    if (vehiculosVM.vehiculosBusState.showDlgDate) {
        DlgSeleccionFecha(
            modifier = Modifier,
            onClick = {
                vehiculosVM.setShowDlgDate(false)
                vehiculosVM.setFechaMantenimiento(it)
            },
            onDismiss = {
                vehiculosVM.setShowDlgDate(false)
            }
        )
    }
}