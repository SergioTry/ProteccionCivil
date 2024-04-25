package com.dam.proteccioncivil.ui.screens.vehiculos

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun vehiculoMtoScreen() {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
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
                    Text(text = "Identificador")
                    OutlinedTextField(
                        value = "", onValueChange = {}, modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Matricula")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Marca")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(text = "Modelo")
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Row {
                        Text(text = "Kms", modifier = Modifier.align(Alignment.CenterVertically))
                        Spacer(modifier = Modifier.width(160.dp))
                        Text(
                            text = "Segunda Mano",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Switch(
                            checked = false,
                            onCheckedChange = {},
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Fecha ITV"
                    )
                    Row {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = LocalDate.now().format(formatter),
                                onValueChange = { },
                                modifier = Modifier.fillMaxWidth()
                            )
                            IconButton(
                                onClick = {
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
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Fecha Proxima Revision"
                    )
                    Row {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = LocalDate.now().format(formatter),
                                onValueChange = { },
                                modifier = Modifier.fillMaxWidth()
                            )
                            IconButton(
                                onClick = {
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
    vehiculoMtoScreen()
}