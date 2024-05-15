package com.dam.proteccioncivil.ui.screens.guardia

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GuardiaMto(
    guardiasVM: GuardiasVM,
    onShowSnackBar: (String) -> Unit,
    refresh: () -> Unit
) {

    val mensage: String
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    var expandedUser1 by remember { mutableStateOf(false) }
    var expandedUser2 by remember { mutableStateOf(false) }
    val users: List<Usuario> = listOf()

    when (guardiasVM.guardiasMessageState) {
        is GuardiasMessageState.Loading -> {
        }

        is GuardiasMessageState.Success -> {
            mensage = if (guardiasVM.guardiasMtoState.codGuardia.equals("0")) {
                ContextCompat.getString(contexto, R.string.guardia_create_success)
            } else {
                ContextCompat.getString(contexto, R.string.guardia_edit_success)
            }
            onShowSnackBar(mensage)
            guardiasVM.resetInfoState()
            guardiasVM.resetGuardiaMtoState()
            guardiasVM.getAll()
            refresh()
        }

        is GuardiasMessageState.Error -> {
            mensage = if (guardiasVM.guardiasMtoState.codGuardia.equals("0")) {
                ContextCompat.getString(contexto, R.string.guardia_create_failure)
            } else {
                ContextCompat.getString(contexto, R.string.guardia_edit_failure)
            }
            onShowSnackBar(mensage)
            guardiasVM.resetInfoState()
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
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
//                    Row {
//                        Text(
//                            text = "Guardia ",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp,
//                            modifier = Modifier
//                                .padding(start = 8.dp, top = 18.dp, end = 8.dp)
//                        )
//                        Box(
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            OutlinedTextField(
//                                value = LocalDate.now().format(formatter),
//                                onValueChange = { },
//                                modifier = Modifier.fillMaxWidth()
//                            )
//                            IconButton(
//                                onClick = {
//                                },
//                                modifier = Modifier.align(Alignment.CenterEnd)
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Filled.DateRange,
//                                    contentDescription = "Calendar Month Icon"
//                                )
//                            }
//                        }
//                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = guardiasVM.guardiasMtoState.descripcion,
                        label = { Text("Descripción") },
                        onValueChange = {
                            guardiasVM.setDescripcion(it)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedUser1,
                        onExpandedChange = { expandedUser1 = !expandedUser1 },
                    ) {
                        OutlinedTextField(
                            value = guardiasVM.guardiasMtoState.codUsuario1.toString(),
                            onValueChange = { },
                            label = { Text("Usuario1") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = { expandedUser1 = true }
                                ) {
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        DropdownMenu(
                            expanded = expandedUser1,
                            onDismissRequest = { expandedUser1 = false }
                        ) {
                            users.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item.nombre) },
                                    onClick = {
                                        guardiasVM.setUsuarios(
                                            item.codUsuario.toString(),
                                            guardiasVM.guardiasMtoState.codUsuario2
                                        )
                                        expandedUser1 = false
                                    })
                            }
                            DropdownMenuItem(
                                text = { Text("Ande vas tonto") },
                                onClick = {
                                    guardiasVM.setUsuarios("6", "7")
                                    expandedUser1 = false
                                })
                        }
                    }
                    ExposedDropdownMenuBox(
                        expanded = expandedUser2,
                        onExpandedChange = { expandedUser2 = !expandedUser2 },
                    ) {
                        OutlinedTextField(
                            value = guardiasVM.guardiasMtoState.codUsuario2.toString(),
                            onValueChange = {},
                            label = { Text("Usuario2") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = { expandedUser2 = true }
                                ) {
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        DropdownMenu(
                            expanded = expandedUser2,
                            onDismissRequest = { expandedUser2 = false }
                        ) {
                            users.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item.nombre) },
                                    onClick = {
                                        guardiasVM.setUsuarios(
                                            guardiasVM.guardiasMtoState.codUsuario1,
                                            item.codUsuario.toString()
                                        )
                                        expandedUser2 = false
                                    })
                            }
                            DropdownMenuItem(
                                text = { Text("Ande vas tonto") },
                                onClick = {
                                    guardiasVM.setUsuarios("6", "7")
                                    expandedUser2 = false
                                })
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    guardiasVM.resetGuardiaMtoState()
                    activity?.onBackPressed()
                }
            ) {
                Text(text = "Cancelar")
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(
                onClick = {
                    if (guardiasVM.guardiasMtoState.codGuardia.equals("0")) {
                        guardiasVM.setNew()
                    } else {
                        guardiasVM.update()
                    }
                }
            ) {
                Text(
                    text =
                    if (guardiasVM.guardiasMtoState.codGuardia.equals("0")) {
                        "Añadir"
                    } else {
                        "Editar"
                    }
                )
            }
        }
    }
}