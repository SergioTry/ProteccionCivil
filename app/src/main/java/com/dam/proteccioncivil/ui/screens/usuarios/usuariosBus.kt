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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import java.time.format.DateTimeFormatter

class Usuario(
    val id: String,
    val nombre: String
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun vehiculoBusScreen() {
    val sampleMessages = listOf(
        "cxosa", "cxosa",
        "cxosa", "cxosa"
    )
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
        Column {
            Row {
                Box(
                    modifier = Modifier
                        .padding(1.dp)
                        .border(BorderStroke(1.dp, Color.Black)).width(160.dp),
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
                            Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "")
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
                    items(sampleMessages.size) { index ->
                        usuarioCard(
                            usuario = Usuario(
                                "15-V-02",
                                "Paco"
                            )
                        )
                    }
                }
            )
        }
        if (true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {},
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
                }
            }
        }
    }
}

@Composable
fun usuarioCard(usuario: Usuario) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(28.dp))
            Column {
                Spacer(modifier = Modifier.height(18.dp))
                Text(text = usuario.id)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = usuario.nombre)
            }
            Spacer(modifier = Modifier.width(90.dp))
            if (true) {
                Row {
                    Spacer(modifier = Modifier.height(28.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun vehiculoBusScreenPreview() {
    vehiculoBusScreen()
}