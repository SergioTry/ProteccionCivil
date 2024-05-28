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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.data.model.Loading
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosMessageState
import com.dam.proteccioncivil.ui.screens.anuncios.AnunciosVM
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnunciosBus(
    anuncios: List<Anuncio>,
    anunciosVM: AnunciosVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onNavUp: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current

    if (anunciosVM.anunciosBusState.loading) {
        Loading()
    }

    when (anunciosVM.anunciosMessageState) {
        is AnunciosMessageState.Loading -> {
        }

        is AnunciosMessageState.Success -> {
            mensage = ContextCompat.getString(contexto, R.string.anuncios_delete_success)
            onShowSnackBar(mensage, true)
            anunciosVM.resetAnuncioMtoState()
            anunciosVM.resetInfoState()
            anunciosVM.getAll()
            refresh()
            anunciosVM.setLoading(false)
        }

        is AnunciosMessageState.Error -> {
            mensage = ContextCompat.getString(contexto, R.string.anuncios_delete_failure)
            onShowSnackBar(mensage, false)
            anunciosVM.resetInfoState()
            anunciosVM.setLoading(false)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo_removebg_gimp),
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
                        refresh = { refresh() },
                        modifier = modifier
                    )
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
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    containerColor = AppColors.Blue
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Añadir",
                        tint = AppColors.White
                    )
                }
            }
        }
        if (anunciosVM.anunciosBusState.showDlgDate) {
            DlgConfirmacion(
                mensaje = R.string.anuncios_delete_confirmation,
                onCancelarClick = {
                    anunciosVM.setShowDlgDate(false)
                },
                onAceptarClick = {
                    anunciosVM.setShowDlgDate(false)
                    anunciosVM.setLoading(true)
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
    refresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(180.dp, 280.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(AppColors.posit)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Anuncio " + FormatVisibleDate.use(anuncio.fechaPublicacion),
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 18.sp
                    )
                    if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                        Row {
                            IconButton(
                                enabled = !anunciosVM.anunciosBusState.loading,
                                onClick = {
                                    anunciosVM.resetAnuncioMtoState()
                                    anunciosVM.cloneAnuncioMtoState(anuncio)
                                    anunciosVM.deleteBy()
                                    anunciosVM.setLoading(true)
                                    refresh()
                                }) {
                                Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                            }
                            IconButton(
                                enabled = !anunciosVM.anunciosBusState.loading,
                                onClick = {
                                    anunciosVM.resetAnuncioMtoState()
                                    anunciosVM.cloneAnuncioMtoState(anuncio)
                                    onNavUp()
                                }) {
                                Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = anuncio.texto,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}
