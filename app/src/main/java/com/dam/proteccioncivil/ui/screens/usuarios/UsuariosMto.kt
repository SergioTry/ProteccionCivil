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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.rememberUpdatedState
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
import com.dam.proteccioncivil.ui.dialogs.DlgSeleccionFecha
import com.dam.proteccioncivil.ui.screens.preferencias.LabelledSwitch
import com.dam.proteccioncivil.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsuariosMto(
    onNavDown: () -> Unit,
    usuariosVM: UsuariosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
) {

    val mensage: String
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val scrollState = rememberScrollState()
    var changePassword by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("administrador", "jefeservicio", "voluntario", "nuevo")
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val oldMto by remember { mutableStateOf(usuariosVM.usuariosMtoState) }
    val currentState by rememberUpdatedState { (usuariosVM.usuariosMtoState) }
    var hayCambios: Boolean = true

    if (oldMto.equals(currentState)) {
        hayCambios = false
    }

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
            onNavDown()
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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo_removebg_gimp),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = Modifier.fillMaxSize(),
        )
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(AppColors.posit)
            ) {
                Column {
                    Column(modifier = Modifier.padding(12.dp)) {
                        OutlinedTextField(
                            label = { Text(text = "DNI") },
                            value = usuariosVM.usuariosMtoState.dni,
                            isError = usuariosVM.usuariosMtoState.dni == "",
                            onValueChange = { usuariosVM.setDni(it) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            label = { Text(text = "Nombre") },
                            isError = usuariosVM.usuariosMtoState.nombre == "",
                            value = usuariosVM.usuariosMtoState.nombre,
                            onValueChange = { usuariosVM.setNombre(it) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            label = { Text(text = "Apellidos") },
                            isError = usuariosVM.usuariosMtoState.apellidos == "",
                            value = usuariosVM.usuariosMtoState.apellidos,
                            onValueChange = { usuariosVM.setApellidos(it) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            label = { Text(text = "Correo Electronico") },
                            isError = usuariosVM.usuariosMtoState.correoElectronico == "",
                            value = usuariosVM.usuariosMtoState.correoElectronico,
                            onValueChange = { usuariosVM.setCorreoElectronico(it) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            label = { Text(text = "Identificador") },
                            isError = usuariosVM.usuariosMtoState.username == "",
                            value = usuariosVM.usuariosMtoState.username,
                            onValueChange = { usuariosVM.setUsername(it) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            label = { Text(text = "Telefono") },
                            isError = usuariosVM.usuariosMtoState.telefono == "",
                            value = usuariosVM.usuariosMtoState.telefono,
                            onValueChange = { usuariosVM.setTelefono(it) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
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
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opciones.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                if (opcion == "jefeservicio") {
                                                    "Jefe de Servicio"
                                                } else if (opcion == "administrador") {
                                                    "Administrador"
                                                } else {
                                                    opcion.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                                                }
                                            )
                                        },
                                        onClick = {
                                            usuariosVM.setRango(
                                                if (opcion == "jefeservicio") {
                                                    "JefeServicio"
                                                } else if (opcion == "administrador") {
                                                    "Admin"
                                                } else {
                                                    opcion.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                                                }
                                            )
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        Row {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    label = { Text(text = "Contraseña") },
                                    isError = usuariosVM.usuariosMtoState.password == "",
                                    value = password,
                                    enabled = if (usuariosVM.usuariosMtoState.codUsuario == "0") true else changePassword,
                                    onValueChange = { password = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Blue,
                                        unfocusedBorderColor = Color.Black,
                                        focusedLabelColor = Color.Blue,
                                        unfocusedLabelColor = Color.Black
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        changePassword = !changePassword
                                        if (!changePassword) {
                                            password = ""
                                            confirmPassword = ""
                                        }
                                    },
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Calendar Month Icon"
                                    )
                                }
                            }
                        }
                        OutlinedTextField(
                            label = { Text(text = "Confirmar contraseña") },
                            value = confirmPassword,
                            isError = usuariosVM.usuariosMtoState.confirmPassword == "",
                            enabled = if (usuariosVM.usuariosMtoState.codUsuario == "0") true else changePassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
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
                        Spacer(modifier = Modifier.size(16.dp))
                        Row {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    label = { Text(text = "Fecha Nacimiento") },
                                    isError = usuariosVM.usuariosMtoState.fechaNacimiento == "",
                                    value = FormatVisibleDate.use(usuariosVM.usuariosMtoState.fechaNacimiento),
                                    onValueChange = {},
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Blue,
                                        unfocusedBorderColor = Color.Black,
                                        focusedLabelColor = Color.Blue,
                                        unfocusedLabelColor = Color.Black
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        usuariosVM.setShowDlgDate(true)
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

                    if (usuariosVM.usuariosBusState.showDlgDate) {
                        DlgSeleccionFecha(
                            onClick = {
                                usuariosVM.setShowDlgDate(false)
                                usuariosVM.setFechaNacimiento(it)
                            },
                            modifier = Modifier,
                            onDismiss = {
                                usuariosVM.setShowDlgDate(false)
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
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
                Spacer(modifier = Modifier.width(100.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue),

                    enabled = usuariosVM.usuariosMtoState.datosObligatorios && hayCambios ||
                            changePassword && password.isNotEmpty() && confirmPassword.isNotEmpty(),
                    onClick = {
                        if (!changePassword && !usuariosVM.usuariosMtoState.codUsuario.equals("0")) {
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
