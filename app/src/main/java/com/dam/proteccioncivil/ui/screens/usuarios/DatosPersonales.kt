package com.dam.proteccioncivil.ui.screens.usuarios

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.ui.screens.preferencias.LabelledSwitch
import com.dam.proteccioncivil.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DatosPersonalesScreen(
    usuariosVM: UsuariosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier
) {

    val mensage: String
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    when (usuariosVM.usuariosMessageState) {
        is UsuariosMessageState.Loading -> {
        }

        is UsuariosMessageState.Success -> {
            mensage = if (usuariosVM.usuariosMtoState.codUsuario.equals("0")) {
                ContextCompat.getString(contexto, R.string.usuario_create_success)
            } else {
                ContextCompat.getString(contexto, R.string.usuario_edit_success)
            }
            onShowSnackBar(mensage, true)
            usuariosVM.getAll()
            usuariosVM.resetInfoState()
            usuariosVM.resetUsuarioMtoState()
        }

        is UsuariosMessageState.Error -> {
            mensage = if (usuariosVM.usuariosMtoState.codUsuario.equals("0")) {
                ContextCompat.getString(contexto, R.string.usuario_create_failure)
            } else {
                ContextCompat.getString(contexto, R.string.usuario_edit_failure)
            }
            onShowSnackBar(mensage, false)
            usuariosVM.resetInfoState()
        }
    }

    if (usuariosVM.usuariosMtoState.codUsuario == "0") {
        usuariosVM.setFechaNacimiento(FormatDate.use())
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo_removebg_gimp),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = modifier.fillMaxSize(),
        )
        Column(modifier = modifier.verticalScroll(scrollState)) {
            Card(
                modifier = modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(AppColors.posit)
            ) {
                Column {
                    Column(modifier = modifier.padding(12.dp)) {
                        OutlinedTextField(
                            label = { Text(text = "DNI") },
                            value = usuariosVM.usuariosMtoState.dni,
                            isError = usuariosVM.usuariosMtoState.dni == "",
                            onValueChange = { usuariosVM.setDni(it) },
                            readOnly = true,
                            modifier = modifier
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = { Text(text = "Nombre") },
                            isError = usuariosVM.usuariosMtoState.nombre == "",
                            value = usuariosVM.usuariosMtoState.nombre,
                            onValueChange = { usuariosVM.setNombre(it) },
                            modifier = modifier
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = { Text(text = "Apellidos") },
                            isError = usuariosVM.usuariosMtoState.apellidos == "",
                            value = usuariosVM.usuariosMtoState.apellidos,
                            onValueChange = { usuariosVM.setApellidos(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = { Text(text = "Correo Electronico") },
                            isError = usuariosVM.usuariosMtoState.correoElectronico == "",
                            value = usuariosVM.usuariosMtoState.correoElectronico,
                            onValueChange = { usuariosVM.setCorreoElectronico(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = { Text(text = "Identificador") },
                            isError = usuariosVM.usuariosMtoState.username == "",
                            value = usuariosVM.usuariosMtoState.username,
                            onValueChange = { usuariosVM.setUsername(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = { Text(text = "Telefono") },
                            isError = usuariosVM.usuariosMtoState.telefono == "",
                            value = usuariosVM.usuariosMtoState.telefono,
                            onValueChange = { usuariosVM.setTelefono(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            label = { Text(text = "Rango") },
                            isError = usuariosVM.usuariosMtoState.rango == "",
                            value = if (usuariosVM.usuariosMtoState.rango.lowercase() == "jefeservicio") {
                                "Jefe de Servicio"
                            } else if (usuariosVM.usuariosMtoState.rango.lowercase() == "admin") {
                                "Administrador"
                            } else {
                                usuariosVM.usuariosMtoState.rango.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                            },
                            onValueChange = { usuariosVM.setRango(it) },
                            modifier = modifier
                                .fillMaxWidth(),
                            readOnly = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            }
                        )

                        Spacer(modifier = modifier.size(16.dp))
                        Row {
                            Box(
                                modifier = modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    label = { Text(text = "Contraseña") },
                                    isError = usuariosVM.usuariosMtoState.password == "",
                                    value = password,
                                    enabled = usuariosVM.usuariosBusState.changePassword,
                                    onValueChange = { password = it },
                                    modifier = modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Blue,
                                        unfocusedBorderColor = Color.Black,
                                        focusedLabelColor = Color.Blue,
                                        unfocusedLabelColor = Color.Black
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        if (usuariosVM.usuariosBusState.changePassword) {
                                            usuariosVM.setChangePassword(false)
                                            password = ""
                                            confirmPassword = ""
                                        } else {
                                            usuariosVM.setChangePassword(true)
                                        }
                                    },
                                    modifier = modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Calendar Month Icon"
                                    )
                                }
                            }
                        }
                        if (usuariosVM.usuariosBusState.changePassword) {
                            OutlinedTextField(
                                label = { Text(text = "Confirmar contraseña") },
                                value = confirmPassword,
                                isError = usuariosVM.usuariosMtoState.confirmPassword == "",
                                enabled = usuariosVM.usuariosBusState.changePassword,
                                onValueChange = { confirmPassword = it },
                                modifier = modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Blue,
                                    unfocusedLabelColor = Color.Black
                                )
                            )
                        }
                        Spacer(modifier = modifier.size(16.dp))
                        LabelledSwitch(
                            checked = usuariosVM.usuariosMtoState.conductor,
                            label = "Conductor",
                            onCheckedChange = {
                                usuariosVM.setConductor(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Blue,
                                uncheckedThumbColor = Color.Gray
                            )
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            label = { Text(text = "Fecha Nacimiento") },
                            isError = usuariosVM.usuariosMtoState.fechaNacimiento == "",
                            value = FormatVisibleDate.use(usuariosVM.usuariosMtoState.fechaNacimiento),
                            onValueChange = {},
                            readOnly = true,
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                    }
                }
            }
            if (usuariosVM.usuariosBusState.changePassword) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            usuariosVM.resetUsuarioMtoState()
                            activity?.onBackPressed()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.errorCarmesi)
                    ) {
                        Text(text = "Cancelar")
                    }
                    Spacer(modifier = modifier.width(100.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue),

                        enabled = usuariosVM.usuariosMtoState.datosObligatorios ||
                                usuariosVM.usuariosBusState.changePassword && password.isNotEmpty() && confirmPassword.isNotEmpty(),
                        onClick = {
                            if (!usuariosVM.usuariosBusState.changePassword && !usuariosVM.usuariosMtoState.codUsuario.equals(
                                    "0"
                                )
                            ) {
                                if (usuariosVM.usuariosMtoState.codUsuario.equals("0")) {
                                    usuariosVM.setNew()
                                } else {
                                    usuariosVM.update()
                                }
                            } else {
                                usuariosVM.setPassword(password)
                                usuariosVM.setConfirmPassword(confirmPassword)
                                if (usuariosVM.passwordCorrect()) {
                                    if (usuariosVM.usuariosMtoState.codUsuario.equals("0")) {
                                        usuariosVM.setNew()
                                    } else {
                                        usuariosVM.update()
                                    }
                                }
                            }
                        }
                    ) {
                        Text(
                            text = if (usuariosVM.usuariosMtoState.codUsuario.equals("0")) {
                                "Añadir"
                            } else {
                                "Editar"
                            }
                        )
                    }
                }
            }
        }
    }
}

