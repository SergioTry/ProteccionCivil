package com.dam.proteccioncivil.pantallas.anuncios

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Anuncio(val text: String, val dateTime: LocalDateTime)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnuncioScreen() {
    val sampleMessages = listOf(
        "cxosa"
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                items(sampleMessages.size) { index ->
                    AnuncioCard(anuncio = Anuncio("Hola", LocalDateTime.now()))
                }
            }
        )
        if (true) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd), // Alineamos el Row en la esquina inferior derecha
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = { /* Acción al hacer clic en el botón */ },
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
fun AnuncioCard(anuncio: Anuncio) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm  dd/MM/yyyy")
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
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
            if (true) {
                Spacer(modifier = Modifier.width(100.dp))
                Column(modifier = Modifier.padding(12.dp)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
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
fun DummyScreenPreview() {
    AnuncioScreen()
}