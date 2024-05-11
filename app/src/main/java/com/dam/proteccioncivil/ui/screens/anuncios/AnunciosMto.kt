package com.dam.proteccioncivil.ui.screens.anuncios

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnunciosMto(anunciosVM: AnunciosVM, onShowSnackBar: (String) -> Unit, refresh: () -> Unit) {

    val mensage: String
    val contexto = LocalContext.current
    val anuncio = anunciosVM.uiAnuncioState

    when (anunciosVM.anunciosMessageState) {
        is AnunciosMessageState.Loading -> {
        }

        is AnunciosMessageState.Success -> {
            mensage = "chuupalo"
            //ContextCompat.getString(contexto, "chuupalo") "
            onShowSnackBar(mensage)
            anunciosVM.resetInfoState()
            anunciosVM.getAll()
            refresh()
        }

        is AnunciosMessageState.Error -> {
            mensage = "chuupalo pero hubo errores"
            //ContextCompat.getString(contexto, "chuupalo pero hubo errores")
            onShowSnackBar(mensage)
            anunciosVM.resetInfoState()
        }
    }
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
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = anuncio.texto,
                        onValueChange = { anunciosVM.setTexto(it) },
                        label = { Text(text = "Texto") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {}
            ) {
                Text(text = "Cancelar")
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(
                onClick = {
                    if (anuncio.codAnuncio.equals("0")) {
                        anunciosVM.setFechaPublicacion(FormatDate.use())
                        anunciosVM.setNew()
                    } else {
                        anunciosVM.update()
                    }
                    anunciosVM.resetInfoState()
                }
            ) {
                Text(text = "Añadir")
            }
        }
    }
}