package com.dam.proteccioncivil.pantallas.anuncios

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import com.dam.proteccioncivil.R
import com.dam.proteccioncivil.data.model.FormatVisibleDate
import com.dam.proteccioncivil.data.model.Guardia
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.screens.guardia.GuardiasMessageState
import com.dam.proteccioncivil.ui.screens.guardia.GuardiasVM
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GuardiasBus(
    guardias: List<Guardia>,
    guardiasVM: GuardiasVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    onNavDetail: () -> Unit,
    refresh: () -> Unit
) {
    var guardiasFiltered = guardias

    if(Token.rango == "Voluntario"){
        guardiasFiltered = guardias.filter { it.codUsuario1 == Token.codUsuario || it.codUsuario2 == Token.codUsuario }
    }

    val mensage: String
    val contexto = LocalContext.current

    when (guardiasVM.guardiasMessageState) {
        is GuardiasMessageState.Loading -> {
        }

        is GuardiasMessageState.Success -> {
            mensage = getString(
                contexto,
                R.string.guardia_delete_success
            )
            onShowSnackBar(mensage, true)
            guardiasVM.resetGuardiaMtoState()
            guardiasVM.resetInfoState()
            guardiasVM.getAll()
            refresh()
        }

        is GuardiasMessageState.Error -> {
            mensage = getString(
                contexto,
                R.string.guardia_delete_failure
            ) + ": " + (guardiasVM.guardiasMessageState as GuardiasMessageState.Error).err
            onShowSnackBar(mensage, false)
            guardiasVM.resetInfoState()
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
                items(guardiasFiltered) { it ->
                    GuardiaCard(
                        guardia = it,
                        onNavUp = { onNavUp() },
                        onNavDetail = { onNavDetail() },
                        guardiasVM = guardiasVM,
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
                        guardiasVM.resetGuardiaMtoState()
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
        if (guardiasVM.guardiasBusState.showDlgConfirmation) {
            DlgConfirmacion(
                mensaje = R.string.guardia_delete_confirmation,
                onCancelarClick = {
                    guardiasVM.setShowDlgBorrar(false)
                },
                onAceptarClick = {
                    guardiasVM.setShowDlgBorrar(false)
                    guardiasVM.deleteBy()
                }
            )
        }
    }
}

@Composable
fun GuardiaCard(
    guardia: Guardia,
    guardiasVM: GuardiasVM,
    onNavUp: () -> Unit,
    onNavDetail: () -> Unit,
    modifier: Modifier,
    contexto: Context
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(AppColors.Posit)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            Column {
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(
                        text =
                        stringResource(id = R.string.guardia_lit) + " " + FormatVisibleDate.use(
                            guardia.fechaGuardia)
                        ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = modifier
                            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                        color = Color.Black
                    )
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            guardiasVM.resetGuardiaMtoState()
                            guardiasVM.cloneGuardiaMtoState(guardia)
                            onNavDetail()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.RemoveRedEye,
                                contentDescription = getString(
                                    contexto,
                                    R.string.detalle_desc
                                ),
                                tint = Color.Black
                            )
                        }
                        if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                            IconButton(onClick = {
                                guardiasVM.resetGuardiaMtoState()
                                guardiasVM.cloneGuardiaMtoState(guardia)
                                guardiasVM.setShowDlgBorrar(true)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = getString(
                                        contexto,
                                        R.string.eliminar_desc
                                    ),
                                    tint = Color.Black
                                )
                            }
                            IconButton(onClick = {
                                guardiasVM.resetGuardiaMtoState()
                                guardiasVM.cloneGuardiaMtoState(guardia)
                                guardiasVM.updateOriginalState()
                                onNavUp()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = getString(contexto, R.string.editar_desc),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = guardia.descripcion,
                    modifier = modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = guardiasVM.users.find { it.codUsuario.toString() == guardia.codUsuario1.toString() }?.nombre
                        ?: "",
                    modifier = modifier
                        .padding(start = 8.dp),
                    color = Color.Black
                )
                Text(
                    text = guardiasVM.users.find { it.codUsuario.toString() == guardia.codUsuario2.toString() }?.nombre
                        ?: "",
                    modifier = modifier
                        .padding(start = 8.dp),
                    color = Color.Black
                )
            }
        }
    }
}

