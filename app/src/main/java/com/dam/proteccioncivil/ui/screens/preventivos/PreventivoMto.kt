package com.dam.proteccioncivil.ui.screens.preventivos

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.data.model.LabelledSwitch
import com.dam.proteccioncivil.data.model.ShortToBoolean
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosVM
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreventivoMto(
    preventivosVM: PreventivosVM,
    usuariosVM: UsuariosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    onNavUsuarioDetail: () -> Unit,
    modifier: Modifier,
    refresh: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var mensage: String
    val activity = (LocalContext.current as? Activity)

    when (preventivosVM.preventivosMessageState) {
        is PreventivosMessageState.Loading -> {
        }

        is PreventivosMessageState.Success -> {
            mensage = if (preventivosVM.preventivoMtoState.codPreventivo == -1) {
                getString(context, R.string.preventivo_create_success)
            } else {
                getString(context, R.string.preventivo_edit_success)
            }
            onShowSnackBar(mensage, true)
            preventivosVM.resetInfoState()
            preventivosVM.resetPreventivoState()
            preventivosVM.getAll()
            refresh()
        }

        is PreventivosMessageState.Error -> {
            mensage = if (preventivosVM.preventivoMtoState.codPreventivo == -1) {
                getString(context, R.string.preventivo_create_failure)
            } else {
                getString(context, R.string.preventivo_edit_failure)
            }
            mensage =
                mensage + ": " + (preventivosVM.preventivosMessageState as PreventivosMessageState.Error).err
            onShowSnackBar(mensage, false)
            preventivosVM.resetInfoState()
        }
    }

    if (preventivosVM.preventivoBusState.fechaABorrar != "") {
        preventivosVM.delFecha()
    }

    if (preventivosVM.preventivoMtoState.codPreventivo != -1) {
        preventivosVM.setFechas()
    }

    if (preventivosVM.preventivoBusState.usuarioBorrar) {
        preventivosVM.setAction(
            "delete"
        )
        preventivosVM.setCodPreventivo(preventivosVM.preventivoMtoState.codPreventivo)
        preventivosVM.update()
        preventivosVM.setAction(null)
        preventivosVM.setUsuarioBorrar(false)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(context, R.string.fondo_desc),
            modifier = modifier.fillMaxSize(),
        )
        Column(modifier = modifier.verticalScroll(scrollState)) {
            Card(
                modifier = modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(AppColors.Posit)
            ) {
                Column(modifier = modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = preventivosVM.preventivoMtoState.titulo,
                        onValueChange = { preventivosVM.setTitulo(it) },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text(text = stringResource(id = R.string.titulo_lit)) },
                        readOnly = preventivosVM.preventivoBusState.isDetail,
                        isError = preventivosVM.preventivoMtoState.titulo == "",
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
                    Spacer(modifier = modifier.size(16.dp))

                    OutlinedTextField(
                        value = preventivosVM.preventivoMtoState.descripcion,
                        onValueChange = { preventivosVM.setDescripcion(it) },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(160.dp),
                        label = { Text(text = stringResource(id = R.string.descripcion_lit)) },
                        readOnly = preventivosVM.preventivoBusState.isDetail,
                        isError = preventivosVM.preventivoMtoState.descripcion == "",
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
                    Spacer(modifier = modifier.size(16.dp))
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        LabelledSwitch(
                            checked = ShortToBoolean.use(preventivosVM.preventivoMtoState.riesgo),
                            label = stringResource(id = R.string.riesgo_lit),
                            onCheckedChange = {
                                if (!preventivosVM.preventivoBusState.isDetail) {
                                    preventivosVM.setRiesgo(it)
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Blue,
                                uncheckedThumbColor = Color.Gray
                            ),
                            roundedInt = 4,
                            backgroundColor = AppColors.Posit,
                            textColor = AppColors.Black
                        )
                    }
                    Spacer(modifier = modifier.size(16.dp))
                    Text(
                        text = stringResource(id = R.string.fechas_lit),
                        style = TextStyle(color = Color.Black)
                    )
                    Box(
                        modifier = modifier
                            .border(
                                BorderStroke(
                                    1.dp,
                                    if (preventivosVM.preventivoMtoState.fechas.isEmpty()) Color.Red else Color.Black
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth(),
                    ) {
                        LazyRow {
                            items(preventivosVM.preventivoMtoState.fechas) { it ->
                                Row {
                                    IconButton(
                                        onClick = {
                                            if (!preventivosVM.preventivoBusState.isDetail) {
                                                preventivosVM.setFechaBorrar(it!!)
                                            }
                                        },
                                        modifier = modifier
                                            .padding(8.dp)
                                    ) {
                                        if (it != null) {
                                            Text(
                                                text = FormatVisibleDate.use(it).substring(0, 5),
                                                style = TextStyle(
                                                    fontSize = 12.sp,
                                                    color = Color.Black
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                            if (preventivosVM.preventivoMtoState.fechas.size < 7 && !preventivosVM.preventivoBusState.isDetail) {
                                item {
                                    IconButton(
                                        onClick = {
                                            if (!preventivosVM.preventivoBusState.isDetail) {
                                                preventivosVM.setShowDlgDate(true)
                                            }
                                        },
                                        modifier = modifier
                                            .padding(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = getString(
                                                context,
                                                R.string.anadir_desc
                                            ),
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = modifier.size(16.dp))
                    if (preventivosVM.preventivoMtoState.codPreventivo != -1) {
                        Text(
                            text = stringResource(id = R.string.usuarios_lit),
                            style = TextStyle(color = Color.Black)
                        )
                        Box(
                            modifier = modifier
                                .border(
                                    BorderStroke(1.dp, Color.Black),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .fillMaxWidth()
                        ) {
                            if (preventivosVM.preventivoMtoState.usuarios.isNullOrEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.no_usuarios),
                                    modifier = modifier
                                        .padding(8.dp)
                                        .height(30.dp),
                                    style = TextStyle(color = Color.Black)
                                )
                            } else {
                                LazyRow {
                                    items(preventivosVM.preventivoMtoState.usuarios!!) { it ->
                                        Row(
                                            modifier = modifier
                                                .border(
                                                    1.dp,
                                                    Color.Black,
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .background(
                                                    if (it.codUsuario == Token.codUsuario) {
                                                        Color.LightGray
                                                    } else {
                                                        Color.White
                                                    },
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .padding(4.dp)
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    usuariosVM.cloneUsuarioMtoState(it)
                                                    onNavUsuarioDetail()
                                                },
                                                modifier = modifier
                                                    .padding(8.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.AccountBox,
                                                    contentDescription = getString(
                                                        context,
                                                        R.string.detalle_desc
                                                    ),
                                                    tint = if (it.codUsuario == Token.codUsuario) {
                                                        Color.White
                                                    } else {
                                                        Color.Black
                                                    }
                                                )
                                            }
//                                            IconButton(
//                                                onClick = {
//                                                    preventivosVM.setUsuarioBorrar(true)
//                                                },
//                                                modifier = modifier
//                                                    .padding(8.dp)
//                                            ) {
//                                                Icon(
//                                                    imageVector = Icons.Default.Delete,
//                                                    contentDescription = getString(
//                                                        context,
//                                                        R.string.anadir_desc
//                                                    ),
//                                                    tint = if (it.codUsuario == Token.codUsuario) {
//                                                        Color.White
//                                                    } else {
//                                                        Color.Black
//                                                    }
//                                                )
//                                            }
                                        }
                                        Spacer(modifier = modifier.size(8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!preventivosVM.preventivoBusState.isDetail) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            preventivosVM.resetPreventivoState()
                            activity?.onBackPressed()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.RojoError)
                    ) {
                        Text(text = stringResource(id = R.string.opc_cancel), color = Color.Black)
                    }
                    Spacer(modifier = modifier.width(100.dp))
                    Button(
                        onClick = {
                            preventivosVM.convertFechas()
                            if (preventivosVM.preventivoMtoState.codPreventivo == -1) {
                                preventivosVM.setNew()
                            } else {
                                preventivosVM.update()
                            }
                        },
                        enabled = preventivosVM.datosObligatorios() &&
                                preventivosVM.hasStateChanged(),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue)
                    ) {
                        Text(
                            text = if (preventivosVM.preventivoMtoState.codPreventivo == -1) {
                                stringResource(id = R.string.opc_create)
                            } else {
                                stringResource(id = R.string.opc_edit)
                            }, color = Color.Black
                        )
                    }
                }
            }
            if (preventivosVM.preventivoBusState.showDlgDate) {
                DlgSeleccionFecha(
                    onClick = {
                        preventivosVM.setShowDlgDate(false)
                        preventivosVM.setNewFecha(it)
                    },
                    modifier = modifier,
                    onDismiss = {
                        preventivosVM.setShowDlgDate(false)
                    }
                )
            }
        }
    }
}
