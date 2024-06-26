package com.dam.proteccioncivil.pantallas.anuncios

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.Anuncio
import com.dam.proteccioncivil.data.model.FormatVisibleDate
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

    when (anunciosVM.anunciosMessageState) {
        is AnunciosMessageState.Loading -> {
        }

        is AnunciosMessageState.Success -> {
            mensage = getString(contexto, R.string.anuncios_delete_success)
            onShowSnackBar(mensage, true)
            anunciosVM.resetAnuncioMtoState()
            anunciosVM.resetInfoState()
            anunciosVM.getAll()
            refresh()
        }

        is AnunciosMessageState.Error -> {
            mensage = getString(
                contexto,
                R.string.anuncios_delete_failure
            ) + ": " + (anunciosVM.anunciosMessageState as AnunciosMessageState.Error).err
            onShowSnackBar(mensage, false)
            anunciosVM.resetInfoState()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = getString(contexto, R.string.fondo_desc),
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
                        modifier = modifier,
                        contexto = contexto
                    )
                }
            }
        )
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
                    refresh()
                },
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                containerColor = AppColors.Blue,
                modifier = modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudSync,
                    contentDescription = getString(contexto, R.string.refresh_desc),
                    tint = AppColors.White
                )
            }
            if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
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
                        contentDescription = getString(contexto, R.string.anadir_desc),
                        tint = AppColors.White
                    )
                }
            }
        }
        if (anunciosVM.anunciosBusState.showDlgBorrar) {
            DlgConfirmacion(
                mensaje = R.string.anuncios_delete_confirmation,
                onCancelarClick = {
                    anunciosVM.setShowDlgBorrar(false)
                },
                onAceptarClick = {
                    anunciosVM.setShowDlgBorrar(false)
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
    modifier: Modifier = Modifier,
    contexto: Context
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(180.dp, 280.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(AppColors.Posit)
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
                        text = stringResource(id = R.string.anuncio_lit) + " " + FormatVisibleDate.use(
                            anuncio.fechaPublicacion
                        ),
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 18.sp,
                        color = AppColors.Black
                    )
                    if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                        Row {
                            IconButton(
                                onClick = {
                                    anunciosVM.resetAnuncioMtoState()
                                    anunciosVM.cloneAnuncioMtoState(anuncio)
                                    anunciosVM.setShowDlgBorrar(true)
                                    refresh()
                                }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = getString(
                                        contexto,
                                        R.string.eliminar_desc
                                    ),
                                    tint = AppColors.Black
                                )
                            }
                            IconButton(
                                onClick = {
                                    anunciosVM.resetAnuncioMtoState()
                                    anunciosVM.cloneAnuncioMtoState(anuncio)
                                    anunciosVM.updateOriginalState()
                                    onNavUp()
                                }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = getString(
                                        contexto,
                                        R.string.eliminar_desc
                                    ),
                                    tint = AppColors.Black
                                )
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
                    OutlinedTextField(
                        value = anuncio.texto,
                        onValueChange = { anunciosVM.setTexto(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        readOnly = true,
                        textStyle = TextStyle(fontSize = 16.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Blue,
                            unfocusedLabelColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )
                }
            }
        }
    }
}
