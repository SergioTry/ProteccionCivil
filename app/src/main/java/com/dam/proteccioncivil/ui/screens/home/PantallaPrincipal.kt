package com.dam.proteccioncivil.pantallas.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            MainTopAppBar(modifier = Modifier)
        },
        bottomBar = {
            MainBottomAppBar(isLogged = true)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gris))
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo),
                contentDescription = "Escudo de Caravaca De La Cruz",
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier,
) {
    val activity = (LocalContext.current as? Activity)
    CenterAlignedTopAppBar(
        title = { Text(text = "Protección civil") },
        modifier = modifier
            .background(colorResource(id = R.color.naranja)),
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Back")
            }
        },
        actions = {
            if (true) {
                IconButton(onClick = {
                }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                }
                DropdownMenu(
                    expanded = false,
                    onDismissRequest = {}
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "stringResource(R.string.menu_login)") },
                        onClick = {
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "stringResource(R.string.title_prefs)") },
                        onClick = { }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "stringResource(R.string.menu_salir)") },
                        onClick = { }
                    )
                }
                if (false) {
                    DlgConfirmacion(
                        mensaje = R.string.app_name,
                        onCancelarClick = { },
                        onAceptarClick = {
                            activity?.finish()
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun MainBottomAppBar(
    isLogged: Boolean,
) {
    NavigationBar(modifier = Modifier.background(colorResource(id = R.color.naranja))) {
        NavigationBarItem(
            selected = false,
            onClick = {
            },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = null) },
            label = { Text(text = "Inicio") })
        NavigationBarItem(
            selected = false,
            onClick = {
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "Icono del chat"
                )
            },
            label = { Text(text = "Chat") },
            enabled = isLogged
        )
        NavigationBarItem(
            selected = false,
            onClick = {
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.preventivo),
                    contentDescription = "Icono del preventivo"
                )
            },
            label = { Text(text = "Preventivos", fontSize = 11.sp) },
            enabled = isLogged
        )
        NavigationBarItem(
            selected = false,
            onClick = {
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.vehiculo),
                    contentDescription = "Icono del vehiculo"
                )
            },
            label = { Text(text = "Vehiculos") },
            enabled = isLogged
        )
        NavigationBarItem(
            selected = false,
            onClick = {
            },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.tablon_anuncios),
                    contentDescription = "Icono del tablón de anuncios"
                )
            },
            label = { Text(text = "Anuncios") },
            enabled = isLogged
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}
