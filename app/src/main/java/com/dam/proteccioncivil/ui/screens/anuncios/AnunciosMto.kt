package com.dam.proteccioncivil.ui.screens.anuncios

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.ui.theme.AppColors


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnunciosMto(anunciosVM: AnunciosVM, onShowSnackBar: (String) -> Unit, refresh: () -> Unit, modifier: Modifier) {

    val mensage: String
    val contexto = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    when (anunciosVM.anunciosMessageState) {
        is AnunciosMessageState.Loading -> {
        }

        is AnunciosMessageState.Success -> {
            mensage = if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                ContextCompat.getString(contexto, R.string.anuncio_create_success)
            } else {
                ContextCompat.getString(contexto, R.string.anuncio_edit_success)
            }
            onShowSnackBar(mensage)
            anunciosVM.resetInfoState()
            anunciosVM.resetAnuncioMtoState()
            anunciosVM.getAll()
            refresh()
        }

        is AnunciosMessageState.Error -> {
            mensage = if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                ContextCompat.getString(contexto, R.string.anuncio_create_failure)
            } else {
                ContextCompat.getString(contexto, R.string.anuncio_edit_failure)
            }
            onShowSnackBar(mensage)
            anunciosVM.resetInfoState()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Blue)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = Modifier.fillMaxSize(),
        )
        Card(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .heightIn(180.dp, 280.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(AppColors.posit)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = anunciosVM.anunciosMtoState.texto,
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
                onClick = {
                    anunciosVM.resetAnuncioMtoState()
                    activity?.onBackPressed()
                }
            ) {
                Text(text = "Cancelar")
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(
                onClick = {
                    if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                        anunciosVM.setFechaPublicacion(FormatDate.use())
                        anunciosVM.setNew()
                    } else {
                        anunciosVM.update()
                    }
                }
            ) {
                Text(
                    text =
                    if (anunciosVM.anunciosMtoState.codAnuncio.equals("0")) {
                        "Añadir"
                    } else {
                        "Editar"
                    }
                )
            }
        }
    }
}