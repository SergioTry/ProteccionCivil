package com.dam.proteccioncivil.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.ui.main.KeystoreHelper
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.theme.AppColors
import com.spr.jetpack_loading.components.indicators.BallClipRotateMultipleIndicator

@Composable
fun LoginScreen(
    version: String,
    mainVM: MainVM,
    loginVM: LoginVM,
    onNavUp: () -> Unit,
    onShowSnackBar: (String, Boolean) -> Unit,
    savedToken: Boolean,
    modifier: Modifier = Modifier
) {
    val uiLoginState = loginVM.uiLoginState
    var passwordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isChecked by remember { mutableStateOf(savedToken) }

    var loading by remember { mutableStateOf(false) }

    when (loginVM.uiInfoState) {
        is LoginUiState.Loading -> {
        }

        is LoginUiState.Success -> {
            onShowSnackBar("Login correcto", true)
            loading = false
            loginVM.resetInfoState()
            onNavUp()
            loginVM.resetLogin()
        }

        is LoginUiState.Error -> {
            loading = false
            onShowSnackBar((loginVM.uiInfoState as LoginUiState.Error).err, false)
            loginVM.resetInfoState()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (loading) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .zIndex(1f)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                BallClipRotateMultipleIndicator(
                    color = Color(255, 165, 0),
                    canvasSize = 170F,
                    penThickness = 8.dp
                )
            }
        }
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo_removebg_gimp),
            contentDescription = "Escudo de Caravaca De La Cruz",
            modifier = modifier.fillMaxSize(),
        )
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = modifier.height(24.dp))
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    errorBorderColor = AppColors.errorCarmesi,
                    errorLabelColor = Color.Black,
                    unfocusedContainerColor = Color.Green,
                    focusedContainerColor = Color.Green,
                    focusedTextColor = Color.Blue,
                    unfocusedTextColor = Color.Blue
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
                enabled = !loading,
                singleLine = true,
                value = uiLoginState.username,
                onValueChange = { loginVM.setUsername(it) },
                isError = uiLoginState.username == "",
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    //.background(Color.Transparent)
            )
            OutlinedTextField(colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                errorBorderColor = AppColors.errorCarmesi,
                errorLabelColor = Color.Black
            ),
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .background(Color.Transparent),
                value = uiLoginState.password,
                onValueChange = { loginVM.setPassword(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        if (uiLoginState.datosObligatorios && !loading) {
                            loading = true
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
                enabled = !loading,
                singleLine = true,
                isError = uiLoginState.password == "",
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { if (!loading) passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description, tint = Color.White)
                    }
                })
            Row(
                modifier
                    .clickable(
                        onClick = { if (!loading) isChecked = !isChecked }
                    )
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = null,
                    enabled = !loading,
                    modifier = modifier.background(Color.White)
                )
                Spacer(modifier.size(6.dp))
                Text("Recuérdame")
            }
            Button(
                onClick = {
                    loading = true
                    loginVM.login(mainVM, isChecked)
                },
                enabled = uiLoginState.datosObligatorios && !loading,
                shape = RoundedCornerShape(5.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp, end = 56.dp),
                colors = ButtonColors(
                    contentColor = AppColors.White,
                    containerColor = AppColors.OrangeColor,
                    disabledContentColor = AppColors.White,
                    disabledContainerColor = AppColors.GreyDisabled
                )
            ) {
                Text("Login")
                Icon(imageVector = Icons.Filled.Check, contentDescription = null)
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = "Versión: $version",
                modifier = modifier
                    .padding(16.dp),
                fontSize = 10.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

    }
}