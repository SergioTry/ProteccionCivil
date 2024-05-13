package com.dam.proteccioncivil.ui.screens.preventivos

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Usuario
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun preventivoMtoScreen() {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var multidia = false
    val user = Usuario(
        codUsuario = 1,
        dni = "12345678A",
        username = "usuario1",
        password = "password123",
        nombre = "Juan",
        apellidos = "Pérez",
        fechaNacimiento = "1993-01-01",
        correoElectronico = "juan@example.com",
        telefono = "123456789",
        rango = "Usuario regular",
        conductor = 1 // Cambiar a 0 si el usuario no es conductor
    )
    val usuarios = listOf<Usuario>(user)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = Modifier.fillMaxSize(),
        )
        Card(
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Titulo")
                    OutlinedTextField(
                        value = "", onValueChange = {}, modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Row {
                        Checkbox(checked = multidia, onCheckedChange = { multidia = !multidia })
                        Text(text = "Varios dias", modifier = Modifier.align(Alignment.CenterVertically))
                    }
                    Text(text = "Fecha Inicio")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (multidia) {
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(text = "Fecha Fin")
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Voluntarios"
                    )
                    Box(modifier = Modifier.border(BorderStroke(1.dp, Color.Black),shape = RoundedCornerShape(8.dp)).fillMaxWidth()) {
                        LazyRow {
                            if(usuarios.size > 0) {
                                items(usuarios.size) { index ->
                                    IconButton(
                                        onClick = { },
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccountBox,
                                            contentDescription = "Añadir usuario"
                                        )
                                    }
                                }
                            }
                            item {
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Añadir usuario"
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Modelo")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Row {
                        Switch(
                            checked = false,
                            onCheckedChange = {}
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Riesgo",modifier = Modifier.align(Alignment.CenterVertically))
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Descripción"
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { }
                    ) {
                        Text(text = "Cancelar")
                    }
                    Spacer(modifier = Modifier.width(100.dp))
                    Button(
                        onClick = {}
                    ) {
                        Text(text = "Añadir")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun DummyScreenPreview() {
    preventivoMtoScreen()
}