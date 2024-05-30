package com.dam.proteccioncivil.ui.screens.infomur

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
import com.dam.proteccioncivil.data.model.Infomur
import com.dam.proteccioncivil.data.model.Token
import com.dam.proteccioncivil.ui.dialogs.DlgConfirmacion
import com.dam.proteccioncivil.ui.theme.AppColors

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InfomurBus(
    infomurs: List<Infomur>,
    infomursVM: InfomursVM,
    onShowSnackBar: (String, Boolean) -> Unit,
    modifier: Modifier,
    onNavUp: () -> Unit,
    onNavDetail: () -> Unit,
    refresh: () -> Unit
) {
    val mensage: String
    val contexto = LocalContext.current

    when (infomursVM.infomursMessageState) {
        is InfomursMessageState.Loading -> {
        }

        is InfomursMessageState.Success -> {
            mensage = getString(
                contexto,
                R.string.infomurs_delete_success
            )
            onShowSnackBar(mensage, true)
            infomursVM.resetInfomurMtoState()
            infomursVM.resetInfoState()
            infomursVM.getAll()
            refresh()
        }

        is InfomursMessageState.Error -> {
            mensage = getString(
                contexto,
                R.string.infomurs_delete_failure
            )
            onShowSnackBar(mensage, false)
            infomursVM.resetInfoState()
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
                items(infomurs) { it ->
                    InfomurCard(
                        infomur = it,
                        onNavUp = { onNavUp() },
                        infomursVM = infomursVM,
                        modifier = modifier,
                        refresh = refresh,
                        onNavDetail = { onNavDetail() },
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
                        infomursVM.resetInfomurMtoState()
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
        if (infomursVM.infomursBusState.showDlgConfirmation) {
            DlgConfirmacion(
                mensaje = R.string.infomurs_delete_confirmation,
                onCancelarClick = {
                    infomursVM.setShowDlgBorrar(false)
                },
                onAceptarClick = {
                    infomursVM.setShowDlgBorrar(false)
                    infomursVM.deleteBy()
                }
            )
        }
    }
}

@Composable
fun InfomurCard(
    infomur: Infomur,
    onNavUp: () -> Unit,
    modifier: Modifier,
    infomursVM: InfomursVM,
    refresh: () -> Unit,
    contexto: Context,
    onNavDetail: () -> Unit
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
                        text = stringResource(id = R.string.infomur_lit) + " " +
                                stringResource(id = R.string.dia_lit) + " " +
                                FormatVisibleDate.use(infomur.fechaInfomur),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = modifier
                            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                        color = AppColors.Black
                    )
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            infomursVM.resetInfomurMtoState()
                            infomursVM.cloneInfomurMtoState(infomur)
                            onNavDetail()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.RemoveRedEye,
                                contentDescription = getString(
                                    contexto,
                                    R.string.detalle_desc
                                ),
                                tint = AppColors.Black
                            )
                        }
                        if (Token.rango == "Admin" || Token.rango == "JefeServicio") {
                            IconButton(onClick = {
                                infomursVM.resetInfomurMtoState()
                                infomursVM.cloneInfomurMtoState(infomur)
                                infomursVM.setShowDlgBorrar(true)
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
                            IconButton(onClick = {
                                infomursVM.resetInfomurMtoState()
                                infomursVM.cloneInfomurMtoState(infomur)
                                onNavUp()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = getString(contexto, R.string.editar_desc),
                                    tint = AppColors.Black
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = infomur.descripcion,
                    modifier = modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppColors.Black
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = infomursVM.users.find { it.codUsuario.toString() == infomur.codUsuario1.toString() }?.nombre
                        ?: "",
                    modifier = modifier
                        .padding(start = 8.dp),
                    color = AppColors.Black
                )
                Text(
                    text = infomursVM.users.find { it.codUsuario.toString() == infomur.codUsuario2.toString() }?.nombre
                        ?: "",
                    modifier = modifier
                        .padding(start = 8.dp),
                    color = AppColors.Black
                )
            }
        }
    }
}
