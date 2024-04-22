package com.dam.proteccioncivil.ui.screens.anuncios

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnuncioScreen() {
    val sampleMessages = listOf(
        "cxosa"
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(value = "Titulo", onValueChange ={},modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = "Cuerpo", onValueChange ={}, modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth())
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomEnd), // Alineamos el Row en la esquina inferior derecha
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* Acción al hacer clic en el botón */ }
            ) {
                Text(text = "Cancelar")
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(
                onClick = { /* Acción al hacer clic en el botón */ }
            ) {
                Text(text = "Añadir")
            }
        }
    }
}

@Preview
@Composable
fun DummyScreenPreview() {
   AnuncioScreen()
}