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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha
import com.dam.proteccioncivil.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GuardiaMto(
    guardiasVM: GuardiasVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    refresh: () -> Unit,
    users: List<Usuario>,
    modifier: Modifier
) {
    var mensage: String
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var expandedUser1 by remember { mutableStateOf(false) }
    var expandedUser2 by remember { mutableStateOf(false) }

    when (guardiasVM.guardiasMessageState) {
        is GuardiasMessageState.Loading -> {
        }

        is GuardiasMessageState.Success -> {
            mensage = if (guardiasVM.guardiasMtoState.codGuardia == "0") {
                getString(contexto, R.string.guardia_create_success)
            } else {
                getString(contexto, R.string.guardia_edit_success)
            }
            onShowSnackBar(mensage, true)
            guardiasVM.resetInfoState()
            guardiasVM.resetGuardiaMtoState()
            guardiasVM.getAll()
            refresh()
        }

        is GuardiasMessageState.Error -> {
            mensage = if (guardiasVM.guardiasMtoState.codGuardia == "0") {
                getString(contexto, R.string.guardia_create_failure)
            } else {
                getString(contexto, R.string.guardia_edit_failure)
            }
            mensage =
                mensage + ": " + (guardiasVM.guardiasMessageState as GuardiasMessageState.Error).err
            onShowSnackBar(mensage, false)
            guardiasVM.resetInfoState()
        }
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
                                label = { Text(text = stringResource(id = R.string.fechaGuardia_lit)) },
                                value = FormatVisibleDate.use(guardiasVM.guardiasMtoState.fechaGuardia),
                                onValueChange = {},
                                readOnly = true,
                                modifier = modifier.fillMaxWidth(),
                                isError = guardiasVM.guardiasMtoState.fechaGuardia == "",
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
                                textStyle = TextStyle(color = Color.Black),
                            )
                            if (!guardiasVM.guardiasBusState.isDetail) {
                                IconButton(
                                    onClick = {
                                        guardiasVM.setShowDlgDate(true)
                                    },
                                    modifier = modifier.align(Alignment.CenterEnd)
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
                    Spacer(modifier = modifier.size(4.dp))
                    OutlinedTextField(
                        value = guardiasVM.guardiasMtoState.descripcion,
                        label = { Text(stringResource(id = R.string.descripcion_lit)) },
                        onValueChange = {
                            guardiasVM.setDescripcion(it)
                        }, modifier = if (Token.rango == "Voluntario") {
                            modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                        } else {
                            modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .focusRequester(focusRequester)
                        },
                        readOnly = guardiasVM.guardiasBusState.isDetail,
                        isError = guardiasVM.guardiasMtoState.descripcion == "",
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
                        textStyle = TextStyle(color = Color.Black),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                keyboardController?.hide()
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        )
                    )
                    if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                        Spacer(modifier = modifier.size(4.dp))
                        ExposedDropdownMenuBox(
                            expanded = expandedUser1,
                            onExpandedChange = {
                                if (!guardiasVM.guardiasBusState.isDetail) expandedUser1 =
                                    !expandedUser1
                            },
                        ) {
                            OutlinedTextField(
                                value = guardiasVM.users.find { it.codUsuario.toString() == guardiasVM.guardiasMtoState.codUsuario1 }?.nombre
                                    ?: "",
                                onValueChange = { },
                                isError = guardiasVM.guardiasMtoState.codUsuario1 == "0",
                                label = { Text(stringResource(id = R.string.usuario1_lit)) },
                                readOnly = true,
                                trailingIcon = {
                                    if (!guardiasVM.guardiasBusState.isDetail) {
                                        IconButton(
                                            onClick = { expandedUser1 = true }
                                        ) {
                                            Icon(
                                                Icons.Filled.ArrowDropDown,
                                                contentDescription = getString(
                                                    contexto,
                                                    R.string.drop_down_desc
                                                ),
                                                tint = Color.Black
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
                                    unfocusedLabelColor = Color.Black,
                                    errorBorderColor = Color.Red,
                                    errorLabelColor = Color.Red,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    errorTextColor = Color.Red
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedUser1,
                                onDismissRequest = { expandedUser1 = false },
                                modifier = Modifier.heightIn(max = 200.dp)
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
                                            focusManager.moveFocus(FocusDirection.Down)
                                        })
                                }
                            }
                        }
                        Spacer(modifier = modifier.size(4.dp))
                        ExposedDropdownMenuBox(
                            expanded = expandedUser2,
                            onExpandedChange = {
                                if (!guardiasVM.guardiasBusState.isDetail) expandedUser2 =
                                    !expandedUser2
                            },
                        ) {
                            OutlinedTextField(
                                value = guardiasVM.users.find { it.codUsuario.toString() == guardiasVM.guardiasMtoState.codUsuario2 }?.nombre
                                    ?: "",
                                onValueChange = {},
                                isError = guardiasVM.guardiasMtoState.codUsuario2 == "0",
                                label = { Text(stringResource(id = R.string.usuario2_lit)) },
                                readOnly = true,
                                trailingIcon = {
                                    if (!guardiasVM.guardiasBusState.isDetail) {
                                        IconButton(
                                            onClick = { expandedUser2 = true }
                                        ) {
                                            Icon(
                                                Icons.Filled.ArrowDropDown,
                                                contentDescription = getString(
                                                    contexto,
                                                    R.string.drop_down_desc
                                                ),
                                                tint = Color.Black
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
                                    unfocusedLabelColor = Color.Black,
                                    errorBorderColor = Color.Red,
                                    errorLabelColor = Color.Red,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    errorTextColor = Color.Red
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedUser2,
                                onDismissRequest = { expandedUser2 = false },
                                modifier = Modifier.heightIn(max = 200.dp)
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
                            }
                        }
                    }
                }
            }
        }
        if (!guardiasVM.guardiasBusState.isDetail) {
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
                        guardiasVM.resetGuardiaMtoState()
                        activity?.onBackPressed()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.RojoError)
                ) {
                    Text(text = stringResource(id = R.string.opc_cancel), color = Color.Black)
                }
                Spacer(modifier = modifier.width(100.dp))
                Button(
                    enabled = guardiasVM.guardiasMtoState.datosObligatorios && guardiasVM.hasStateChanged(),
                    onClick = {
                        if (guardiasVM.guardiasMtoState.codGuardia == "0") {
                            guardiasVM.setNew()
                        } else {
                            guardiasVM.update()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue)

                ) {
                    Text(
                        text =
                        if (guardiasVM.guardiasMtoState.codGuardia == "0") {
                            stringResource(id = R.string.opc_create)
                        } else {
                            stringResource(id = R.string.opc_edit)
                        }, color = Color.Black
                    )
                }
            }
        }
        if (guardiasVM.guardiasBusState.showDlgDate) {
            DlgSeleccionFecha(
                onClick = {
                    guardiasVM.setShowDlgDate(false)
                    guardiasVM.setFechaGuardia(it)
                    focusRequester.requestFocus()
                },
                modifier = modifier,
                onDismiss = {
                    guardiasVM.setShowDlgDate(false)
                }
            )
        }
    }
}