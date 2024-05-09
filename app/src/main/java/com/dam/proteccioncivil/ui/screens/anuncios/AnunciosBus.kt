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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosMessageState
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnunciosBus(
    anuncios: List<Anuncio>,
    anunciosVM: AnunciosVM,
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNavUp: (isRefresh: Boolean) -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current

    when (anunciosVM.anunciosMessageState) {
        is AnunciosMessageState.Loading -> {
        }

        is AnunciosMessageState.Success -> {
            mensage = "chuupalo"
            //ContextCompat.getString(contexto, "chuupalo") "
            onShowSnackbar(mensage)
            anunciosVM.setCodAnuncio(-1)
            anunciosVM.resetInfoState()
        }

        is AnunciosMessageState.Error -> {
            mensage = "chuupalo pero hubo errores"
            //ContextCompat.getString(contexto, "chuupalo pero hubo errores")
            onShowSnackbar(mensage)
            anunciosVM.resetInfoState()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Escudo caravaca de la cruz",
            modifier = modifier.fillMaxSize(),
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            content = {
                items(anuncios) { it ->
                    AnuncioCard(anuncio = it, onNavUp = { onNavUp(false) }, anunciosVM = anunciosVM)
                }
            }
        )
        if (true) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                    },
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
fun AnuncioCard(anuncio: Anuncio, anunciosVM: AnunciosVM, onNavUp: () -> Unit) {
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
                    text = "Anuncio " + FormatDate.use(anuncio.fechaPublicacion),
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text(
                    text = anuncio.texto,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            if (true) {
                Column(modifier = Modifier.padding(12.dp)) {
                    IconButton(onClick = {
                        anunciosVM.setCodAnuncio(anuncio.codAnuncio)
                        anunciosVM.deleteBy()
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    IconButton(onClick = {
                        anunciosVM.setCodAnuncio(anuncio.codAnuncio)
                        onNavUp() }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
        }
    }
}

