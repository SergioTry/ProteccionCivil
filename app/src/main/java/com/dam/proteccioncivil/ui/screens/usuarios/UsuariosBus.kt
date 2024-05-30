package com.dam.proteccioncivil.ui.screens.usuarios

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsuariosBus(
    usuarios: List<Usuario>,
    usuarioVM: UsuariosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current
    val options = listOf("Opción 1", "Opción 2", "Opción 3")
    val focusRequester = remember { FocusRequester() }

    when (usuarioVM.usuariosMessageState) {
        is UsuariosMessageState.Loading -> {
        }

        is UsuariosMessageState.Success -> {
            mensage = ContextCompat.getString(
                contexto,
                R.string.usuario_delete_success
            )
            onShowSnackBar(mensage, true)
            usuarioVM.resetUsuarioMtoState()
            usuarioVM.resetInfoState()
            usuarioVM.getAll()
            refresh()
        }

        is UsuariosMessageState.Error -> {
            mensage = ContextCompat.getString(
                contexto,
                R.string.usuario_delete_failure
            )
            onShowSnackBar(mensage, false)
            usuarioVM.resetInfoState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(contexto, R.string.fondo_desc),
            modifier = Modifier.fillMaxSize(),
        )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .border(BorderStroke(1.dp, Color.Black))
                    .height(75.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = "", // Aquí puedes poner el valor del campo de texto
                            onValueChange = { /* Aquí puedes manejar el cambio del valor */ },
                            label = {
                                Text(
                                    stringResource(id = R.string.filtro_lit),
                                    color = Color.Black
                                )
                            },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = { usuarioVM.setExpanded(true) }
                                ) {
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = getString(contexto, R.string.drop_down_desc))
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1.5f)
                                .padding(8.dp)
                                .focusRequester(focusRequester),
                            textStyle = TextStyle(color = AppColors.Black)
                        )
                        DropdownMenu(
                            expanded = usuarioVM.usuariosBusState.expanded,
                            onDismissRequest = { usuarioVM.setExpanded(false) }
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        usuarioVM.setExpanded(false)
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .weight(2f)
                            .padding(8.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = { },
                            label = {
                                Text(
                                    stringResource(id = R.string.buscar_lit),
                                    color = Color.Black
                                )
                            },
                            modifier = Modifier
                                .weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                errorBorderColor = Color.White,
                                unfocusedBorderColor = Color.White
                            ), textStyle = TextStyle(color = AppColors.Black)
                        )
                        IconButton(
                            onClick = { /* TODO: Implement search action */ },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = getString(contexto, R.string.buscar_desc),
                                tint = AppColors.Black
                            )
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(usuarios) { it ->
                        usuarioCard(
                            usuario = it,
                            onNavUp = { onNavUp() },
                            usuariosVM = usuarioVM,
                            modifier = modifier,
                            contexto = contexto
                        )
                    }
                }
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                onClick = {
                    refresh()
                },
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                containerColor = AppColors.Blue,
                modifier = modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudSync,
                    contentDescription = getString(contexto, R.string.refresh_desc),
                    tint = AppColors.White
                )
            }
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                FloatingActionButton(
                    onClick = {
                        usuarioVM.resetUsuarioMtoState()
                        onNavUp()
                    },
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    containerColor = AppColors.Blue
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = getString(contexto, R.string.anadir_desc),
                        tint = AppColors.White
                    )
                }
            }
        }
        if (usuarioVM.usuariosBusState.showDlgConfirmation) {
            DlgConfirmacion(
                mensaje = R.string.guardia_delete_confirmation,
                onCancelarClick = {
                    usuarioVM.setShowDlgBorrar(false)
                },
                onAceptarClick = {
                    usuarioVM.setShowDlgBorrar(false)
                    usuarioVM.deleteBy()
                }
            )
        }
    }
}

@Composable
fun usuarioCard(
    usuario: Usuario,
    onNavUp: () -> Unit,
    usuariosVM: UsuariosVM,
    modifier: Modifier,
    contexto: Context
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(AppColors.Posit)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.homer),
                contentDescription = null,
                modifier = modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .background(Color.White, shape = RoundedCornerShape(50))
            )
            Spacer(modifier = modifier.width(28.dp))
            Column {
                Row(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = usuario.username,
                        modifier = modifier.align(Alignment.CenterVertically),
                        color = Color.Black
                    )
                    if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = {
                                usuariosVM.resetUsuarioMtoState()
                                usuariosVM.cloneUsuarioMtoState(usuario)
                                usuariosVM.setShowDlgBorrar(true)
                            }) {
                                Icon(imageVector = Icons.Filled.Delete, contentDescription = getString(contexto, R.string.eliminar_desc),tint = Color.Black)
                            }
                            IconButton(onClick = {
                                usuariosVM.resetUsuarioMtoState()
                                usuariosVM.cloneUsuarioMtoState(usuario)
                                usuariosVM.setPassword("")
                                onNavUp()
                            }) {
                                Icon(imageVector = Icons.Filled.Edit, contentDescription = getString(contexto, R.string.editar_desc),tint = Color.Black)
                            }
                        }
                    }
                }
                Text(
                    text = usuario.nombre + " " + formatApellidos(usuario.apellidos),
                    color = Color.Black
                )
            }
        }
    }
}

fun formatApellidos(apellidos: String): String {
    val position = apellidos.indexOfFirst { it.isUpperCase() && apellidos.indexOf(it) != 0 }
    return if (position != -1) {
        apellidos.substring(0, position) + " " + apellidos.substring(position)
    } else {
        apellidos
    }
}
