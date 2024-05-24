package com.dam.proteccioncivil.ui.screens.infomur

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
import androidx.compose.material.icons.filled.DateRange
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
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfomurMto(
    infomursVM: InfomursVM,
    onShowSnackBar: (String) -> Unit,
    refresh: () -> Unit,
    users: List<Usuario>
) {

    val mensage: String
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    var expandedUser1 by remember { mutableStateOf(false) }
    var expandedUser2 by remember { mutableStateOf(false) }

    when (infomursVM.infomursMessageState) {
        is InfomursMessageState.Loading -> {
        }

        is InfomursMessageState.Success -> {
            mensage = if (infomursVM.infomursMtoState.codInfomur.equals("0")) {
                ContextCompat.getString(contexto, R.string.infomur_create_success)
            } else {
                ContextCompat.getString(contexto, R.string.infomur_edit_success)
            }
            onShowSnackBar(mensage)
            infomursVM.resetInfoState()
            infomursVM.resetInfomurMtoState()
            infomursVM.getAll()
            refresh()
        }

        is InfomursMessageState.Error -> {
            mensage = if (infomursVM.infomursMtoState.codInfomur.equals("0")) {
                ContextCompat.getString(contexto, R.string.infomur_create_failure)
            } else {
                ContextCompat.getString(contexto, R.string.infomur_edit_failure)
            }
            onShowSnackBar(mensage)
            infomursVM.resetInfoState()
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
                .height(380.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                label = { Text(text = "Fecha Infomur") },
                                value = FormatDate.use(infomursVM.infomursMtoState.fechaInfomur),
                                onValueChange = {},
                                modifier = Modifier.fillMaxWidth()
                            )
                            IconButton(
                                onClick = {
                                    infomursVM.showDlgDate = true
                                    refresh()
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
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = infomursVM.infomursMtoState.descripcion,
                        onValueChange = { infomursVM.setDescripcion(it) },
                        label = { Text(text = "Descripcion") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedUser1,
                        onExpandedChange = { expandedUser1 = !expandedUser1 },
                    ) {
                        OutlinedTextField(
                            value = infomursVM.users.find { it.codUsuario.toString() == infomursVM.infomursMtoState.codUsuario1 }?.nombre ?: "",
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
                                        infomursVM.setUsuarios(
                                            item.codUsuario.toString(),
                                            infomursVM.infomursMtoState.codUsuario2
                                        )
                                        expandedUser1 = false
                                    })
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedUser2,
                        onExpandedChange = { expandedUser2 = !expandedUser2 },
                    ) {
                        OutlinedTextField(
                            value = infomursVM.users.find { it.codUsuario.toString() == infomursVM.infomursMtoState.codUsuario2 }?.nombre ?: "",
                            onValueChange = { },
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
                                        infomursVM.setUsuarios(
                                            infomursVM.infomursMtoState.codUsuario1,
                                            item.codUsuario.toString()
                                        )
                                        expandedUser2 = false
                                    })
                            }
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
                    infomursVM.resetInfomurMtoState()
                    activity?.onBackPressed()
                }
            ) {
                Text(text = "Cancelar")
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(
                onClick = {
                    if (infomursVM.infomursMtoState.codInfomur.equals("0")) {
                        infomursVM.setNew()
                    } else {
                        infomursVM.update()
                    }
                }
            ) {
                Text(
                    text =
                    if (infomursVM.infomursMtoState.codInfomur.equals("0")) {
                        "Añadir"
                    } else {
                        "Editar"
                    }
                )
            }
            if (infomursVM.showDlgDate) {
                DlgSeleccionFecha(
                    modifier = Modifier,
                    onClick = {
                        infomursVM.showDlgDate = false
                        infomursVM.setFechaInfomur(FormatDate.use(it))
                    }
                )
            }
        }
    }
}
