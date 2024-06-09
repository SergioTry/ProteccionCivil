package com.dam.proteccioncivil.ui.screens.vehiculos

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.ComboBox
import com.dam.proteccioncivil.data.model.ShortToBoolean
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Vehiculo
import com.dam.proteccioncivil.data.model.filtrosVehiculos
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VehiculosBus(
    vehiculos: List<Vehiculo>,
    vehiculosVM: VehiculosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    onNavDetail: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var exposed by remember { mutableStateOf(false) }

    var vehiculosFiltrados by remember { mutableStateOf(vehiculos) }

    if (vehiculosVM.vehiculosBusState.textoBusqueda != "" && vehiculosVM.vehiculosBusState.lanzarBusqueda) {
        vehiculosFiltrados = vehiculos.filter {
            it.modelo.lowercase().contains(vehiculosVM.vehiculosBusState.textoBusqueda.lowercase())
                    || it.marca.lowercase()
                .contains(vehiculosVM.vehiculosBusState.textoBusqueda.lowercase())
                    || it.matricula.lowercase()
                .contains(vehiculosVM.vehiculosBusState.textoBusqueda.lowercase())
        }
        vehiculosVM.setLanzarBusqueda(false)
    } else if (vehiculosVM.vehiculosBusState.lanzarBusqueda) {
        vehiculosFiltrados = vehiculos
        vehiculosVM.setLanzarBusqueda(false)
    }

    when (vehiculosVM.vehiculosMessageState) {
        is VehiculoMessageState.Loading -> {
        }

        is VehiculoMessageState.Success -> {
            mensage = getString(
                contexto,
                R.string.vehiculo_delete_success
            )
            onShowSnackBar(mensage, true)
            vehiculosVM.resetVehiculoMtoState()
            vehiculosVM.resetInfoState()
            vehiculosVM.getAll()
            refresh()
        }

        is VehiculoMessageState.Error -> {
            mensage = getString(
                contexto,
                R.string.vehiculo_delete_failure
            ) + ": " + (vehiculosVM.vehiculosMessageState as VehiculoMessageState.Error).err
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
            contentDescription = getString(contexto, R.string.fondo_desc),
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                ComboBox(
                    onSelectedChange = {
                        vehiculosVM.setComboBoxOptionSelected(it)
                        exposed = false
                        vehiculosVM.getAll()
                        refresh()
                    },
                    onExpandedChange = { exposed = it },
                    expanded = exposed,
                    options = filtrosVehiculos,
                    optionSelected = vehiculosVM.vehiculosBusState.comboBoxOptionSelected,
                    enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                    modifier = Modifier
                        .weight(4f)
                )
                OutlinedTextField(
                    value = vehiculosVM.vehiculosBusState.textoBusqueda,
                    onValueChange = { vehiculosVM.setTextoBusqueda(it) },
                    label = {
                        Text(
                            stringResource(id = R.string.buscar_lit),
                        )
                    },
                    modifier = Modifier
                        .weight(6f),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            vehiculosVM.setLanzarBusqueda(true)
                        }
                    ),
                    enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary),
                    trailingIcon = {
                        Row {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.Blue)
                                    .clickable { vehiculosVM.setLanzarBusqueda(true) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = getString(contexto, R.string.buscar_desc),
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red)
                                    .clickable {
                                        vehiculosVM.setLanzarBusqueda(false)
                                        vehiculosVM.setTextoBusqueda("")
                                        vehiculosFiltrados = vehiculos
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = getString(contexto, R.string.buscar_desc),
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(vehiculosFiltrados) { it ->
                        vehiculoCard(
                            vehiculo = it,
                            onNavUp = { onNavUp() },
                            vehiculosVM = vehiculosVM,
                            modifier = modifier,
                            contexto = contexto,
                            onNavDetail = { onNavDetail() },
                            enabled = vehiculosVM.vehiculosUiState != VehiculosUiState.Loading
                        )
                    }
                }
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                onClick = {
                    if (vehiculosVM.vehiculosUiState != VehiculosUiState.Loading) {
                        refresh()
                    }
                },
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                containerColor = AppColors.Blue,
                modifier = modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudSync,
                    contentDescription = getString(contexto, R.string.refresh_desc),
                    tint = AppColors.White
                )
            }
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                FloatingActionButton(
                    onClick = {
                        if (vehiculosVM.vehiculosUiState != VehiculosUiState.Loading) {
                            vehiculosVM.resetVehiculoMtoState()
                            onNavUp()
                        }
                    },
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    containerColor = AppColors.Blue
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = getString(contexto, R.string.anadir_desc),
                        tint = AppColors.White
                    )
                }
            }
        }
        if (vehiculosVM.vehiculosBusState.showDlgConfirmation) {
            DlgConfirmacion(
                mensaje = R.string.vehicle_delete_confirmation,
                onCancelarClick = {
                    vehiculosVM.setShowDlgBorrar(false)
                },
                onAceptarClick = {
                    vehiculosVM.setShowDlgBorrar(false)
                    vehiculosVM.deleteBy()
                }
            )
        }
    }
}

@Composable
fun vehiculoCard(
    vehiculo: Vehiculo,
    onNavUp: () -> Unit,
    vehiculosVM: VehiculosVM,
    modifier: Modifier,
    contexto: Context,
    onNavDetail: () -> Unit,
    enabled: Boolean
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(AppColors.Posit)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = vehiculo.matricula,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .align(Alignment.Start)
                        .padding(16.dp),
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.pcc_icono),
                    contentDescription = null,
                    modifier = modifier
                        .padding(6.dp)
                        .align(Alignment.Start)
                )
            }
            Column {
                Spacer(modifier = modifier.height(8.dp))
                Row(modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.weight(1f)) {
                        Spacer(modifier = modifier.height(12.dp))
                        Text(
                            text = vehiculo.km.toString(),
                            modifier = modifier.padding(start = 8.dp),
                            color = Color.Black
                        )
                    }
                    Row(horizontalArrangement = Arrangement.End) {
                        IconButton(
                            onClick = {
                                vehiculosVM.resetVehiculoMtoState()
                                vehiculosVM.cloneVehiculoMtoState(vehiculo)
                                onNavDetail()
                            }, enabled = enabled
                        ) {
                            Icon(
                                imageVector = Icons.Filled.RemoveRedEye,
                                contentDescription = getString(contexto, R.string.detalle_desc),
                                tint = Color.Black
                            )
                        }
                        if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                            IconButton(
                                onClick = {
                                    vehiculosVM.resetVehiculoMtoState()
                                    vehiculosVM.cloneVehiculoMtoState(vehiculo)
                                    vehiculosVM.setShowDlgBorrar(true)
                                }, enabled = enabled
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = getString(
                                        contexto,
                                        R.string.eliminar_desc
                                    ),
                                    tint = Color.Black
                                )
                            }
                            Spacer(modifier = modifier.height(28.dp))
                            IconButton(
                                onClick = {
                                    vehiculosVM.resetVehiculoMtoState()
                                    vehiculosVM.cloneVehiculoMtoState(vehiculo)
                                    vehiculosVM.updateOriginalState()
                                    onNavUp()
                                }, enabled = enabled
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = getString(
                                        contexto,
                                        R.string.editar_desc
                                    ),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = if (ShortToBoolean.use(vehiculo.disponible)) stringResource(id = R.string.disponible_lit) else stringResource(
                        id = R.string.no_disponible_lit
                    ),
                    modifier = modifier.padding(start = 8.dp),
                    color = Color.Black
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = vehiculo.marca + " " + vehiculo.modelo,
                    modifier = modifier.padding(start = 8.dp),
                    color = Color.Black
                )
            }
        }
    }
}