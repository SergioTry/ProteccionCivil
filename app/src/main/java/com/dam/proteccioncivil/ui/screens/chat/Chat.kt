package com.dam.proteccioncivil.pantallas.chat

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
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dam.proteccioncivil.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Mensaje(val userId: String, val text: String, val dateTime: LocalDateTime)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PantallaMensajes() {
    val sampleMessages = listOf(
        Mensaje(
            "User1",
            "En este ejemplo, el composable Box tiene un ancho máximo de 200 dp debido al modificador widthIn. Si el ancho del Box es menor que 200 dp, se respetará su tamaño original. Si es mayor, se ajustará al ancho máximo especificado.",
            LocalDateTime.now()
        ),
        Mensaje("User2", "¡Buenos días!", LocalDateTime.now())
    )
    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        content = {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                        .background(Color.Black.copy(alpha = 0.5f)),
                ) {

                }

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
                                val message = sampleMessages[index]
                                MensajeCard(mensaje = message)
                            }
                        }
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("Ingrese su mensaje") },
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
    )
}

@Composable
fun MensajeCard(mensaje: Mensaje) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm  dd/MM/yyyy")
    val formattedDateTime = mensaje.dateTime.format(formatter)
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = if (mensaje.userId == "User2") {
            Alignment.CenterEnd
        } else {
            Alignment.CenterStart
        }
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .widthIn(90.dp, 300.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        if (mensaje.userId == "User2") {
                            colorResource(id = R.color.mensajeMio)
                        } else {
                            colorResource(id = R.color.mensaje)
                        }
                    )
            ) {
                Text(
                    text = mensaje.userId,
                    modifier = Modifier
                        .padding(8.dp, 8.dp, 0.dp, 0.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(8.dp, 0.dp, 8.dp, 8.dp)
                ) {
                    Text(
                        text = mensaje.text,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Text(
                    text = formattedDateTime,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MensajeScreenPreview() {
    PantallaMensajes()
}