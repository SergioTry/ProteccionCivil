package com.dam.proteccioncivil.ui.screens.usuarios

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.data.model.Usuario
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion

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
            painter = painterResource(id = R.drawable.fondo_removebg_gimp),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = Modifier.fillMaxSize(),
        )
        Column {
            Row {
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .width(160.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(50.dp)
                    )
                    {
                        IconButton(
                            onClick = { /*TODO*/ }, modifier = Modifier
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(imageVector = Icons.Filled.Warning, contentDescription = "")
                        }
                        Text(
                            text = "Filtro Vol.",
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                        IconButton(
                            onClick = { /*TODO*/ }, modifier = Modifier
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = ""
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(50.dp)
                    )
                    {
                        Text(
                            text = "Buscar voluntarios",
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(8.dp)
                        )
                        IconButton(
                            onClick = { /*TODO*/ }, modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(8.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "")
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
                            modifier = modifier)
                    }
                }
            )
        }
        if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        usuarioVM.resetUsuarioMtoState()
                        onNavUp()
                    },
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
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
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.pcc_icono),
                contentDescription = null,
                modifier = modifier
                    .padding(6.dp)
                    .size(80.dp)
            )
            Spacer(modifier = modifier.width(28.dp))
            Column {
                Spacer(modifier = modifier.height(18.dp))
                Text(text = usuario.username)
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = usuario.nombre//+ " " + usuario.apellidos)
                )
            }
            Spacer(modifier = modifier.width(90.dp))
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                Row {
                    Spacer(modifier = modifier.height(28.dp))
                    IconButton(onClick = {
                        usuariosVM.resetUsuarioMtoState()
                        usuariosVM.cloneUsuarioMtoState(usuario)
                        usuariosVM.setShowDlgBorrar(true)
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    IconButton(onClick = {
                        usuariosVM.resetUsuarioMtoState()
                        usuariosVM.cloneUsuarioMtoState(usuario)
                        usuariosVM.setPassword("")
                        onNavUp()
                    }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
        }
    }
}
