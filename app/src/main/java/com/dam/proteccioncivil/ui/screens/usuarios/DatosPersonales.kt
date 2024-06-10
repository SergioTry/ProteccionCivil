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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
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
import com.dam.proteccioncivil.data.model.LabelledSwitch
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DatosPersonales(
    usuariosVM: UsuariosVM,
    mainVM: MainVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier
) {
    val mensage: String
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    val scrollState = rememberScrollState()

    when (usuariosVM.usuariosMessageState) {
        is UsuariosMessageState.Loading -> {
        }

        is UsuariosMessageState.Success -> {
            mensage = if (usuariosVM.usuariosMtoState.codUsuario == "0") {
                getString(contexto, R.string.usuario_create_success)
            } else {
                getString(contexto, R.string.usuario_edit_success)
            }
            mainVM.setCredentials(
                mapOf(
                    "Username" to Token.username!!,
                    "Password" to mainVM.passwordState.UIpassword
                )
            )
            Token.password = mainVM.passwordState.UIpassword
            onShowSnackBar(mensage, true)
            usuariosVM.setChangePasswordChecker(false)
            usuariosVM.resetInfoState()
            usuariosVM.resetUsuarioMtoState()
        }

        is UsuariosMessageState.Error -> {
            mensage = if (usuariosVM.usuariosMtoState.codUsuario == "0") {
                getString(contexto, R.string.usuario_create_failure)
            } else {
                getString(contexto, R.string.usuario_edit_failure)
            }
            onShowSnackBar(mensage, false)
            usuariosVM.resetInfoState()
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
        Column(modifier = modifier.verticalScroll(scrollState)) {
            Card(
                modifier = modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(AppColors.Posit),
                border = CardDefaults.outlinedCardBorder(true)
            ) {
                Column {
                    Column(modifier = modifier.padding(12.dp)) {
                        OutlinedTextField(
                            label = {
                                Text(
                                    text = stringResource(id = R.string.dni_lit),
                                    color = Color.Black
                                )
                            },
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
                            ),
                            textStyle = TextStyle(Color.Black)
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(
                                        id = R.string.nombre_lit
                                    ),
                                    color = Color.Black
                                )
                            },
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
                            ),
                            textStyle = TextStyle(Color.Black)
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.apellidos_lit),
                                    color = Color.Black
                                )
                            },
                            isError = usuariosVM.usuariosMtoState.apellidos == "",
                            value = usuariosVM.usuariosMtoState.apellidos,
                            onValueChange = { usuariosVM.setApellidos(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            ),
                            textStyle = TextStyle(Color.Black)
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.correElectronico_lit),
                                    color = Color.Black
                                )
                            },
                            isError = usuariosVM.usuariosMtoState.correoElectronico == "",
                            value = usuariosVM.usuariosMtoState.correoElectronico,
                            onValueChange = { usuariosVM.setCorreoElectronico(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            ),
                            textStyle = TextStyle(Color.Black)
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.identificador_lit),
                                    color = Color.Black
                                )
                            },
                            isError = usuariosVM.usuariosMtoState.username == "",
                            value = usuariosVM.usuariosMtoState.username,
                            onValueChange = { usuariosVM.setUsername(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            ),
                            textStyle = TextStyle(Color.Black)
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.telefono_lit),
                                    color = Color.Black
                                )
                            },
                            isError = usuariosVM.usuariosMtoState.telefono == "",
                            value = usuariosVM.usuariosMtoState.telefono,
                            onValueChange = { usuariosVM.setTelefono(it) },
                            modifier = modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Blue,
                                unfocusedLabelColor = Color.Black
                            ),
                            textStyle = TextStyle(Color.Black)
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            label = {
                                Text(
                                    text = stringResource(id = R.string.rango_lit),
                                    color = Color.Black
                                )
                            },
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
                            textStyle = TextStyle(Color.Black)
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        Row {
                            Box(
                                modifier = modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    label = {
                                        Text(
                                            text = stringResource(id = R.string.contrasena_lit),
                                            color = Color.Black
                                        )
                                    },
                                    isError = mainVM.passwordState.UIpassword == "",
                                    value = mainVM.passwordState.UIpassword,
                                    enabled = usuariosVM.usuariosBusState.changePasswordChecker,
                                    onValueChange = {
                                        mainVM.setPasswordForUi(it)
                                    },
                                    singleLine = true,
                                    modifier = modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Blue,
                                        unfocusedBorderColor = Color.Black,
                                        focusedLabelColor = Color.Blue,
                                        unfocusedLabelColor = Color.Black,
                                        errorBorderColor = Color.Red,
                                        errorLabelColor = Color.Red,
                                        focusedTextColor = Color.Black,
                                        unfocusedTextColor = Color.Black,
                                        disabledTextColor = Color.Black,
                                        disabledBorderColor = Color.Gray
                                    ),
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                    keyboardActions = KeyboardActions(
                                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                    )
                                )
                                IconButton(
                                    onClick = {
                                        if (usuariosVM.usuariosBusState.changePasswordChecker) {
                                            usuariosVM.setChangePasswordChecker(false)
                                            usuariosVM.setConfirmPassword("")
                                        } else {
                                            usuariosVM.setChangePasswordChecker(true)
                                        }
                                    },
                                    modifier = modifier.align(Alignment.CenterEnd)
                                ) {
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
                        if (usuariosVM.usuariosBusState.changePasswordChecker) {
                            OutlinedTextField(
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.confirmarContrasena_lit),
                                        color = Color.Black
                                    )
                                },
                                singleLine = true,
                                value = usuariosVM.usuariosMtoState.confirmPassword,
                                isError = usuariosVM.usuariosMtoState.confirmPassword == "" ||
                                        usuariosVM.usuariosMtoState.confirmPassword != mainVM.passwordState.UIpassword,
                                enabled = usuariosVM.usuariosBusState.changePasswordChecker,
                                onValueChange = { usuariosVM.setConfirmPassword(it) },
                                modifier = modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Blue,
                                    unfocusedLabelColor = Color.Black,
                                    errorBorderColor = Color.Red,
                                    errorLabelColor = Color.Red,
                                    errorTextColor = Color.Red,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                ), keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                        if (usuariosVM.usuariosBusState.changePasswordChecker && mainVM.passwordState.UIpassword.isNotEmpty() && usuariosVM.usuariosMtoState.confirmPassword.isNotEmpty()
                                            && usuariosVM.usuariosMtoState.confirmPassword == mainVM.passwordState.UIpassword
                                        ) {
                                            usuariosVM.setPassword(mainVM.passwordState.UIpassword)
                                            if (usuariosVM.passwordCorrect()) {
                                                usuariosVM.changePassword()
                                            }
                                        }
                                    }
                                )
                            )
                        }
                        Spacer(modifier = modifier.size(16.dp))
                        LabelledSwitch(
                            checked = usuariosVM.usuariosMtoState.conductor,
                            label = stringResource(id = R.string.conductor_lit),
                            onCheckedChange = {
                                usuariosVM.setConductor(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Blue,
                                uncheckedThumbColor = Color.Gray
                            ),
                            roundedInt = 4,
                            backgroundColor = AppColors.Posit,
                            textColor = Color.Black
                        )
                        Spacer(modifier = modifier.size(16.dp))
                        OutlinedTextField(
                            label = {
                                Text(
                                    text = stringResource(id = R.string.fechaNacimiento_lit),
                                    color = Color.Black
                                )
                            },
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
                            ),
                            textStyle = TextStyle(Color.Black)
                        )
                    }
                }
            }
            if (usuariosVM.usuariosBusState.changePasswordChecker) {
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
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.RojoError)
                    ) {
                        Text(text = stringResource(id = R.string.opc_cancel), color = Color.Black)
                    }
                    Spacer(modifier = modifier.width(100.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Blue),

                        enabled =
                        usuariosVM.usuariosBusState.changePasswordChecker && mainVM.passwordState.UIpassword.isNotEmpty() && usuariosVM.usuariosMtoState.confirmPassword.isNotEmpty()
                                && usuariosVM.usuariosMtoState.confirmPassword == mainVM.passwordState.UIpassword,
                        onClick = {
                            usuariosVM.setPassword(mainVM.passwordState.UIpassword)
                            if (usuariosVM.passwordCorrect()) {
                                usuariosVM.changePassword()
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.opc_edit),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

