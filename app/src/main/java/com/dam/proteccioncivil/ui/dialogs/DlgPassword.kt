package com.dam.proteccioncivil.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosMessageState
import com.dam.proteccioncivil.ui.screens.usuarios.UsuariosVM
import com.dam.proteccioncivil.ui.theme.AppColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DlgPassword(
    usuariosVM: UsuariosVM,
    mainVM: MainVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    backToLogin: () -> Unit,
    goHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    // Esto es necesario porque sino selecciona controles de fuera del dialog
    val (passwordFocusRequester, confirmPasswordFocusRequester) = FocusRequester.createRefs()

    val focusManager = LocalFocusManager.current
    var password1Visible by remember { mutableStateOf(false) }
    var password2Visible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    when (usuariosVM.usuariosMessageState) {
        is UsuariosMessageState.Loading -> {
        }

        is UsuariosMessageState.Success -> {
            mainVM.setCredentials(
                mapOf(
                    "Username" to Token.username!!,
                    "Password" to usuariosVM.usuariosMtoState.password
                )
            )
            mainVM.setPasswordForUi(usuariosVM.usuariosMtoState.password)
            Token.password = usuariosVM.usuariosMtoState.password
            onShowSnackBar(getString(context, R.string.password_establecido), true)
            usuariosVM.resetInfoState()
            usuariosVM.resetUsuarioMtoState()
            goHome()
        }

        is UsuariosMessageState.Error -> {
            onShowSnackBar(
                (usuariosVM.usuariosMessageState as UsuariosMessageState.Error).err,
                false
            )
            usuariosVM.resetUsuarioMtoState()
            usuariosVM.resetInfoState()
            if ((usuariosVM.usuariosMessageState as UsuariosMessageState.Error).backToLogin) {
                backToLogin()
            }

        }
    }

    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(AppColors.DialogColors)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.new_password),
                    textDecoration = TextDecoration.Underline,
                    color = Color.Black
                )
                Spacer(modifier = modifier.height(16.dp))
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        errorTextColor = Color.Red,
                        errorBorderColor = Color.Red,
                        focusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        errorLabelColor = Color.Red,
                        unfocusedLabelColor = Color.Blue,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .focusRequester(passwordFocusRequester),
                    value = usuariosVM.usuariosMtoState.password,
                    onValueChange = { usuariosVM.setPassword(it) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { confirmPasswordFocusRequester.requestFocus() }
                    ),
                    label = {
                        Text(
                            stringResource(R.string.lbl_password),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    singleLine = true,
                    isError = usuariosVM.usuariosMtoState.password == "" ||
                            usuariosVM.usuariosMtoState.confirmPassword == "" ||
                            usuariosVM.usuariosMtoState.password != usuariosVM.usuariosMtoState.confirmPassword,
                    visualTransformation = if (password1Visible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (password1Visible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (password1Visible) getString(
                            context,
                            R.string.hide_password_icon
                        ) else getString(context, R.string.show_password_icon)
                        IconButton(onClick = { password1Visible = !password1Visible }) {
                            Icon(imageVector = image, description, tint = Color.Black)
                        }
                    }
                )
                Spacer(modifier = modifier.height(4.dp))
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        errorTextColor = Color.Red,
                        errorBorderColor = Color.Red,
                        focusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        errorLabelColor = Color.Red,
                        unfocusedLabelColor = Color.Blue,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .focusRequester(confirmPasswordFocusRequester),
                    value = usuariosVM.usuariosMtoState.confirmPassword,
                    onValueChange = { usuariosVM.setConfirmPassword(it) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (usuariosVM.usuariosMtoState.password != "" &&
                                usuariosVM.usuariosMtoState.confirmPassword != "" &&
                                usuariosVM.usuariosMtoState.password == usuariosVM.usuariosMtoState.confirmPassword
                            ) {
                                focusManager.clearFocus()
                                usuariosVM.changePassword()
                            }
                        }
                    ),
                    label = {
                        Text(
                            stringResource(R.string.confirmarContrasena_lit),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    singleLine = true,
                    isError = usuariosVM.usuariosMtoState.password == "" ||
                            usuariosVM.usuariosMtoState.confirmPassword == "" ||
                            usuariosVM.usuariosMtoState.password != usuariosVM.usuariosMtoState.confirmPassword,
                    visualTransformation = if (password2Visible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (password2Visible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (password2Visible) getString(
                            context,
                            R.string.hide_password_icon
                        ) else getString(context, R.string.show_password_icon)
                        IconButton(onClick = { password2Visible = !password2Visible }) {
                            Icon(imageVector = image, description, tint = Color.Black)
                        }
                    }
                )
                Spacer(modifier = modifier.height(8.dp))
                TextButton(
                    enabled = usuariosVM.usuariosMtoState.password != "" &&
                            usuariosVM.usuariosMtoState.confirmPassword != "" &&
                            usuariosVM.usuariosMtoState.password == usuariosVM.usuariosMtoState.confirmPassword,
                    modifier = modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp),
                    onClick = {
                        if (usuariosVM.usuariosMtoState.password != "" &&
                            usuariosVM.usuariosMtoState.confirmPassword != "" &&
                            usuariosVM.usuariosMtoState.password == usuariosVM.usuariosMtoState.confirmPassword
                        ) {
                            keyboardController?.hide()
                            usuariosVM.changePassword()
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Blue,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(stringResource(id = R.string.btn_establecer), color = Color.Black)
                }
                Spacer(modifier = modifier.height(8.dp))
            }
        }
    }

}
