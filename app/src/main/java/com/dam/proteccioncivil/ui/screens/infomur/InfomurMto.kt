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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha
import com.dam.proteccioncivil.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfomurMto(
    infomursVM: InfomursVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    refresh: () -> Unit,
    users: List<Usuario>,
    modifier: Modifier
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
            mensage = if (infomursVM.infomursMtoState.codInfomur == "0") {
                ContextCompat.getString(contexto, R.string.infomur_create_success)
            } else {
                ContextCompat.getString(contexto, R.string.infomur_edit_success)
            }
            onShowSnackBar(mensage, true)
            infomursVM.resetInfoState()
            infomursVM.resetInfomurMtoState()
            infomursVM.getAll()
            refresh()
        }

        is InfomursMessageState.Error -> {
            mensage = if (infomursVM.infomursMtoState.codInfomur == "0") {
                ContextCompat.getString(contexto, R.string.infomur_create_failure)
            } else {
                ContextCompat.getString(contexto, R.string.infomur_edit_failure)
            }
            onShowSnackBar(mensage, false)
            infomursVM.resetInfoState()
        }
    }

    if (infomursVM.infomursMtoState.codInfomur == "0") {
        infomursVM.setFechaInfomur(FormatDate.use())
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(contexto, R.string.fondo_desc),
            modifier = modifier.fillMaxSize(),
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(380.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(AppColors.Posit)
        ) {
            Row(modifier = modifier.fillMaxWidth()) {
                Column(modifier = modifier.padding(12.dp)) {
                    Row {
                        Box(
                            modifier = modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                readOnly = infomursVM.infomursBusState.isDetail,
                                label = { Text(text = stringResource(id = R.string.fechaInfomur_lit)) },
                                value = FormatVisibleDate.use(infomursVM.infomursMtoState.fechaInfomur),
                                isError = infomursVM.infomursMtoState.fechaInfomur == "",
                                onValueChange = {},
                                modifier = modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Blue,
                                    unfocusedLabelColor = Color.Black
                                ),
                                textStyle = TextStyle(color = AppColors.Black)
                            )
                            if (!infomursVM.infomursBusState.isDetail) {
                                IconButton(
                                    onClick = {
                                        infomursVM.setShowDlgDate(true)
                                    },
                                    modifier = modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = getString(
                                            contexto,
                                            R.string.fecha_desc
                                        ),
                                        tint = AppColors.Black
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.size(8.dp))
                    OutlinedTextField(
                        readOnly = infomursVM.infomursBusState.isDetail,
                        value = infomursVM.infomursMtoState.descripcion,
                        onValueChange = { infomursVM.setDescripcion(it) },
                        label = {
                            Text(
                                text = stringResource(id = R.string.descripcion_lit),
                                color = AppColors.Black
                            )
                        },
                        isError = infomursVM.infomursMtoState.descripcion == "",
                        modifier = modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black
                        ), textStyle = TextStyle(color = AppColors.Black)
                    )
                    Spacer(modifier = modifier.size(16.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedUser1 && !infomursVM.infomursBusState.isDetail,
                        onExpandedChange = { if (!infomursVM.infomursBusState.isDetail) expandedUser1 = !expandedUser1 },
                    ) {
                        OutlinedTextField(
                            value = infomursVM.users.find { it.codUsuario.toString() == infomursVM.infomursMtoState.codUsuario1 }?.nombre
                                ?: "",
                            onValueChange = { },
                            isError = infomursVM.infomursMtoState.codUsuario1 == "0",
                            label = {
                                Text(
                                    stringResource(id = R.string.usuario1_lit),
                                    color = AppColors.Black
                                )
                            },
                            readOnly = true,
                            trailingIcon = {
                                if (!infomursVM.infomursBusState.isDetail) {
                                    IconButton(
                                        onClick = { expandedUser1 = true }
                                    ) {
                                        Icon(
                                            Icons.Filled.ArrowDropDown,
                                            contentDescription = getString(
                                                contexto,
                                                R.string.drop_down_desc
                                            )
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            modifier = modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            ),
                            textStyle = TextStyle(color = AppColors.Black)
                        )
                        DropdownMenu(
                            expanded = expandedUser1 && !infomursVM.infomursBusState.isDetail,
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
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = modifier.size(4.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedUser2,
                        onExpandedChange = { if (!infomursVM.infomursBusState.isDetail) expandedUser2 = !expandedUser2 },
                    ) {
                        OutlinedTextField(
                            value = infomursVM.users.find { it.codUsuario.toString() == infomursVM.infomursMtoState.codUsuario2 }?.nombre
                                ?: "",
                            onValueChange = { },
                            isError = infomursVM.infomursMtoState.codUsuario2 == "0",
                            label = {
                                Text(
                                    stringResource(id = R.string.usuario2_lit),
                                    color = AppColors.Black
                                )
                            },
                            readOnly = true,
                            trailingIcon = {
                                if (!infomursVM.infomursBusState.isDetail) {
                                    IconButton(
                                        onClick = { expandedUser2 = true }
                                    ) {
                                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                    }
                                }
                            },
                            singleLine = true,
                            modifier = modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            ),
                            textStyle = TextStyle(color = AppColors.Black)
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
        if (!infomursVM.infomursBusState.isDetail) {
            Row(
                modifier = modifier
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
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.RojoError)
                ) {
                    Text(text = "Cancelar")
                }
                Spacer(modifier = modifier.width(100.dp))
                Button(
                    onClick = {
                        if (infomursVM.infomursMtoState.codInfomur == "0") {
                            infomursVM.setNew()
                        } else {
                            infomursVM.update()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue),
                    enabled = infomursVM.infomursMtoState.datosObligatorios
                ) {
                    Text(
                        text =
                        if (infomursVM.infomursMtoState.codInfomur == "0") {
                            "Añadir"
                        } else {
                            "Editar"
                        }
                    )
                }
            }
            if (infomursVM.infomursBusState.showDlgDate) {
                DlgSeleccionFecha(
                    onClick = {
                        infomursVM.setShowDlgDate(false)
                        infomursVM.setFechaInfomur(FormatDate.use(it))
                    },
                    modifier = modifier,
                    onDismiss = {
                        infomursVM.setShowDlgDate(false)
                    }
                )
            }
        }
    }
}
