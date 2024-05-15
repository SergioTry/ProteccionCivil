package com.dam.proteccioncivil.ui.screens.usuarios

import android.annotation.SuppressLint
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsuariosMto(refresh: () -> Unit, usuariosVM: UsuariosVM, onShowSnackBar: (String) -> Unit) {

    val mensage: String
    val contexto = LocalContext.current

    when (usuariosVM.usuariosMessageState) {
        is UsuariosMessageState.Loading -> {
        }

        is UsuariosMessageState.Success -> {
            mensage = ContextCompat.getString(
                contexto,
                R.string.guardia_delete_success
            )
            onShowSnackBar(mensage)
            usuariosVM.resetUsuarioMtoState()
            usuariosVM.resetInfoState()
            usuariosVM.getAll()
            refresh()
        }

        is UsuariosMessageState.Error -> {
            mensage = ContextCompat.getString(
                contexto,
                R.string.guardia_delete_failure
            )
            onShowSnackBar(mensage)
            usuariosVM.resetInfoState()
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
        Card(
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Nombre")
                    OutlinedTextField(
                        value = "", onValueChange = {}, modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Apellidos")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Correo electronico")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Identificador")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Telefono")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Rango"
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Fecha Nacimiento"
                    )
                    Row {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = FormatDate.use(),
                                onValueChange = { },
                                modifier = Modifier.fillMaxWidth()
                            )
                            IconButton(
                                onClick = {
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Calendar Month Icon"
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { }
                    ) {
                        Text(text = "Cancelar")
                    }
                    Spacer(modifier = Modifier.width(100.dp))
                    Button(
                        onClick = {}
                    ) {
                        Text(text = "Añadir")
                    }
                }
            }
        }
    }
}