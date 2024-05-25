package com.dam.proteccioncivil.ui.screens.vehiculos

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.ShortToBoolean
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Vehiculo
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VehiculosBus(
    vehiculos: List<Vehiculo>,
    vehiculosVM: VehiculosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current

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
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = Modifier.fillMaxSize(),
        )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .border(BorderStroke(1.dp, Color.Black))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Filtrar vehículos",
                        color = Color.Black,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(8.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "")
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
                            modifier = modifier
                        )
                    }
                }
            )
        }
        if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        vehiculosVM.resetVehiculoMtoState()
                        onNavUp()
                    },
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
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
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = vehiculo.matricula,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .align(Alignment.Start)
                        .padding(16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null,
                    modifier = modifier
                        .padding(6.dp)
                        .align(Alignment.Start)
                )
            }
            Spacer(modifier = modifier.width(28.dp))
            Column {
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = vehiculo.km.toString(),
                    modifier = modifier.padding(start = 8.dp)
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = if (ShortToBoolean.use(vehiculo.disponible)) "Disponible" else "No Disponible",
                    modifier = modifier.padding(start = 8.dp)
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = vehiculo.marca + " " + vehiculo.modelo,
                    modifier = modifier.padding(start = 8.dp)
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
                        Text("Asignar")
                    }
                }
            }
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                Row {
                    Spacer(modifier = modifier.height(28.dp))
                    IconButton(onClick = {
                        vehiculosVM.resetVehiculoMtoState()
                        vehiculosVM.cloneVehiculoMtoState(vehiculo)
                        vehiculosVM.setShowDlgBorrar(true)
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    Spacer(modifier = modifier.height(28.dp))
                    IconButton(onClick = {
                        vehiculosVM.resetVehiculoMtoState()
                        vehiculosVM.cloneVehiculoMtoState(vehiculo)
                        onNavUp()
                    }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
        }
    }
}