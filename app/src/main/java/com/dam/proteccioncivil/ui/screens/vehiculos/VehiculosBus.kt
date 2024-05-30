package com.dam.proteccioncivil.ui.screens.vehiculos

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.ShortToBoolean
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Vehiculo
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
    val options = listOf("Opción 1", "Opción 2", "Opción 3")
    val focusRequester = remember { FocusRequester() }
    var expanded = false

    when (vehiculosVM.vehiculosMessageState) {
        is VehiculoMessageState.Loading -> {
        }

        is VehiculoMessageState.Success -> {
            mensage = ContextCompat.getString(
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
            mensage = ContextCompat.getString(
                contexto,
                R.string.vehiculo_delete_failure
            )
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
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .border(BorderStroke(1.dp, Color.Black))
                    .height(75.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = stringResource(id = R.string.filtro_lit),
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = { expanded = !expanded }
                                ) {
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1.5f)
                                .padding(8.dp)
                                .focusRequester(focusRequester)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .weight(2f)
                            .padding(8.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = { Text(stringResource(id = R.string.buscar_lit)) },
                            modifier = Modifier
                                .weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                errorBorderColor = Color.White,
                                unfocusedBorderColor = Color.White
                            )
                        )
                        IconButton(
                            onClick = { /* TODO: Implement search action */ },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(vehiculos) { it ->
                        vehiculoCard(
                            vehiculo = it,
                            onNavUp = { onNavUp() },
                            vehiculosVM = vehiculosVM,
                            modifier = modifier,
                            contexto = contexto,
                            onNavDetail = { onNavDetail() }
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
                    refresh()
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
                        vehiculosVM.resetVehiculoMtoState()
                        onNavUp()
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
                mensaje = R.string.guardia_delete_confirmation,
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
    onNavDetail: () -> Unit
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
                Row {
                    Column {
                        Spacer(modifier = modifier.height(12.dp))
                        Text(
                            text = vehiculo.km.toString(),
                            modifier = modifier.padding(start = 8.dp),
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = modifier.width(45.dp))
                    IconButton(onClick = {
                        vehiculosVM.resetVehiculoMtoState()
                        vehiculosVM.cloneVehiculoMtoState(vehiculo)
                        onNavDetail()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.RemoveRedEye,
                            contentDescription = getString(contexto, R.string.detalle_desc),
                            tint = Color.Black
                        )
                    }
                    if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                        IconButton(onClick = {
                            vehiculosVM.resetVehiculoMtoState()
                            vehiculosVM.cloneVehiculoMtoState(vehiculo)
                            vehiculosVM.setShowDlgBorrar(true)
                        }) {
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
                        IconButton(onClick = {
                            vehiculosVM.resetVehiculoMtoState()
                            vehiculosVM.cloneVehiculoMtoState(vehiculo)
                            onNavUp()
                        }) {
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
                Spacer(modifier = modifier.height(8.dp))
                if (Token.rango == "Admin" || Token.rango == "JefeServicio" || ShortToBoolean.use(
                        Token.conductor?.toShort()
                    )
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(id = R.string.asignar_lit))
                    }
                }
            }
        }
    }
}