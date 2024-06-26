package com.dam.proteccioncivil.ui.screens.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
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
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Loading
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.main.MainVM
import com.dam.proteccioncivil.ui.theme.AppColors

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
    val contexto = LocalContext.current

    when (loginVM.uiInfoState) {
        is LoginUiState.Loading -> {
        }

        is LoginUiState.Success -> {
            onShowSnackBar(getString(contexto, R.string.login_success), true)
            loginVM.setIsLoading(false)
            loginVM.resetInfoState()
            onNavUp()
            loginVM.resetLogin()
        }

        is LoginUiState.Error -> {
            loginVM.setIsLoading(false)
            onShowSnackBar((loginVM.uiInfoState as LoginUiState.Error).err, false)
            loginVM.resetInfoState()
        }
    }

    if (Token.username.isNullOrBlank()) {
        BackHandler(enabled = true, onBack = {
            mainVM.setShowDlgSalir(true)
        })
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (loginVM.uiLoginState.isLoading) {
            Loading()
        }
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(contexto, R.string.fondo_desc),
            modifier = modifier.fillMaxSize(),
        )
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (!isSystemInDarkTheme()) MaterialTheme.colorScheme.background else AppColors.GreyDisabled),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        errorBorderColor = AppColors.RojoError,
                        errorLabelColor = Color.Black,
                        errorTextColor = Color.Red
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
                    enabled = !loginVM.uiLoginState.isLoading,
                    singleLine = true,
                    value = uiLoginState.username,
                    onValueChange = {
                        if (it.length <= 10) {
                            loginVM.setUsername(it)
                        }
                    },
                    isError = uiLoginState.username == "",
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Color.Transparent)
                        .padding(16.dp, 16.dp, 16.dp, 8.dp)
                        .width(280.dp),
                )
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        errorBorderColor = AppColors.RojoError,
                        errorLabelColor = Color.Black,
                        errorTextColor = Color.Red
                    ),
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Color.Transparent)
                        .padding(16.dp, 8.dp, 16.dp, 16.dp)
                        .width(280.dp),
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
                            if (uiLoginState.datosObligatorios && !loginVM.uiLoginState.isLoading) {
                                loginVM.setIsLoading(true)
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
                    enabled = !loginVM.uiLoginState.isLoading,
                    singleLine = true,
                    isError = uiLoginState.password == "",
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) getString(
                            contexto,
                            R.string.hide_password_icon
                        ) else getString(contexto, R.string.show_password_icon)
                        IconButton(onClick = {
                            if (!loginVM.uiLoginState.isLoading) passwordVisible = !passwordVisible
                        }) {
                            Icon(
                                imageVector = image,
                                description,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    })
                Row(
                    modifier
                        .clickable(
                            onClick = {
                                if (!loginVM.uiLoginState.isLoading) isChecked = !isChecked
                            }
                        )
                        .padding(16.dp, 8.dp, 16.dp, 16.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = null,
                        enabled = !loginVM.uiLoginState.isLoading,
                        modifier = modifier.background(Color.White)
                    )
                    Spacer(modifier.size(6.dp))
                    Text(stringResource(R.string.lbl_remember_me))
                }
            }
            Button(
                onClick = {
                    loginVM.setIsLoading(true)
                    loginVM.login(mainVM, isChecked)
                },
                enabled = uiLoginState.datosObligatorios && !loginVM.uiLoginState.isLoading,
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
                Text(stringResource(R.string.lbl_login))
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