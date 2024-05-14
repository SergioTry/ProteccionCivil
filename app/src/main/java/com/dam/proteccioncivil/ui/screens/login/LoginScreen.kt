package com.dam.proteccioncivil.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.main.MainVM

@Composable
fun LoginScreen(
    version: String,
    mainVM: MainVM,
    loginVM: LoginVM,
    onNavUp: () -> Unit,
    savedToken: Boolean,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val uiLoginState = loginVM.uiLoginState
    var passwordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isChecked by remember { mutableStateOf(savedToken) }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "SplashScreen image",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    focusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Blue
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                label = {
                    Text(
                        text = stringResource(R.string.lbl_username),
                        fontWeight = FontWeight.Bold
                    )
                },
                singleLine = true,
                value = uiLoginState.username,
                onValueChange = { loginVM.setUsername(it) },
                isError = !uiLoginState.datosObligatorios,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                focusedBorderColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Blue
            ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                value = uiLoginState.password,
                onValueChange = { loginVM.setPassword(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if (uiLoginState.datosObligatorios) {
                            loginVM.login(mainVM, isChecked)
                        }
                    }
                ),
                label = {
                    Text(
                        stringResource(R.string.lbl_password),
                        fontWeight = FontWeight.Bold
                    )
                },
                singleLine = true,
                isError = !uiLoginState.datosObligatorios,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                })
            Row(
                Modifier
                    .clickable(
                        onClick = { isChecked = !isChecked }
                    )
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = null
                )
                Spacer(Modifier.size(6.dp))
                Text("Recuérdame")
            }
            Button(
                onClick = {

                    loginVM.login(mainVM, isChecked)

                },
                enabled = uiLoginState.datosObligatorios,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp, end = 56.dp)
            ) {
                Text("Login")
                Icon(imageVector = Icons.Filled.Check, contentDescription = null)
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Versión: $version",
                modifier = Modifier.padding(16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
    when (loginVM.uiInfoState) {
        is LoginUiState.Loading -> {

        }

        is LoginUiState.Success -> {
            loginVM.resetInfoState()
            onNavUp()
            loginVM.resetLogin()
        }

        is LoginUiState.Error -> {
            loginVM.resetInfoState()
        }
    }

}
//No he conseguido mostrar los string con el stringResources
//                    try {
//                        keyboardController?.hide()
//                        if (mainVM.login(uiDptosState.departamentos)) {
//                            onShowSnackbar("Login correcto")
//                            onNavUp()
//                        } else {
//                            onShowSnackbar("Login incorrecto")
//                        }
//                    } catch (e: Exception) {
//                        onShowSnackbar("Campos obligatorios incompletos")
//                    }