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
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.FormatDate
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosMessageState
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnunciosBus(
    anuncios: List<Anuncio>,
    anunciosVM: AnunciosVM,
    onShowSnackBar: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current

    when (anunciosVM.anunciosMessageState) {
        is AnunciosMessageState.Loading -> {
        }

        is AnunciosMessageState.Success -> {
            mensage = ContextCompat.getString(contexto, R.string.anuncios_delete_success)

            onShowSnackBar(mensage)
            anunciosVM.resetAnuncioMtoState()
            anunciosVM.resetInfoState()
            anunciosVM.getAll()
            refresh()
        }

        is AnunciosMessageState.Error -> {
            mensage = ContextCompat.getString(contexto, R.string.anuncios_delete_failure)
            onShowSnackBar(mensage)
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
                    AnuncioCard(
                        anuncio = it,
                        onNavUp = { onNavUp() },
                        anunciosVM = anunciosVM,
                        refresh = { refresh() })
                }
            }
        )
        if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
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
                        anunciosVM.resetAnuncioMtoState()
                        onNavUp()
                    },
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Añadir")
                }
            }
        }
        if (anunciosVM.showDlgConfirmation) {
            DlgConfirmacion(
                mensaje = 0,
                onCancelarClick = {
                    anunciosVM.showDlgConfirmation = false
                    refresh()
                },
                onAceptarClick = {
                    anunciosVM.showDlgConfirmation = false
                    anunciosVM.deleteBy()
                }
            )
        }
    }
}

@Composable
fun AnuncioCard(
    anuncio: Anuncio,
    anunciosVM: AnunciosVM,
    onNavUp: () -> Unit,
    refresh: () -> Unit
) {
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
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                Column(modifier = Modifier.padding(12.dp)) {
                    IconButton(onClick = {
                        anunciosVM.resetAnuncioMtoState()
                        anunciosVM.cloneAnuncioMtoState(anuncio)
                        anunciosVM.showDlgConfirmation = true
                        refresh()
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                    IconButton(onClick = {
                        anunciosVM.resetAnuncioMtoState()
                        anunciosVM.cloneAnuncioMtoState(anuncio)
                        onNavUp()
                    }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            }
        }
    }
}

