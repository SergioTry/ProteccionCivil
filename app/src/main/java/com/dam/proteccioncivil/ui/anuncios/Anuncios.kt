package com.dam.proteccioncivil.pantallas.anuncios

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Anuncio(val text: String, val dateTime: LocalDateTime)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DummyScreen() {
    val sampleMessages = listOf(
        "cxosa"
    )
    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fondo),
                    contentDescription = "Escudo caravaca de la cruz",
                    modifier = Modifier.fillMaxSize(),
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        content = {
                            items(sampleMessages.size) { index ->
                                AnuncioCard(anuncio = Anuncio("Hola", LocalDateTime.now()))
                            }
                        }
                    )
                    if (true) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = "",
                                onValueChange = {},
                                label = { Text("Ingrese nuevo anuncio") },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Button(
                                onClick = { /* Acción al hacer clic */ },
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Icono de enviar mensaje"
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun AnuncioCard(anuncio: Anuncio) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm  dd/MM/yyyy")
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(300.dp, 300.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Text(
                    text = "Anuncio " + anuncio.dateTime.format(formatter),
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text(
                    text = anuncio.text,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DummyScreenPreview() {
    DummyScreen()
}